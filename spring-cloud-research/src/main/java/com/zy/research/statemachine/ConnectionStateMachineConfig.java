package com.zy.research.statemachine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class ConnectionStateMachineConfig extends EnumStateMachineConfigurerAdapter<ConnectionStateEnum, ConnectionEventEnum> {

    /**
     * https://github.com/lianggzone/springboot-action/blob/master/springboot-action-statemachine/src/main/java/com/lianggzone/springboot/statemachine/reg/config/StateMachineConfig.java
     * 初始化状态机状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<ConnectionStateEnum, ConnectionEventEnum> states) throws Exception {
        states.withStates()
                // 定义初始状态
                .initial(ConnectionStateEnum.UNCONNECTED)
                // 定义状态机状态
                .states(EnumSet.allOf(ConnectionStateEnum.class));
    }

    /**
     * 初始化状态迁移事件
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<ConnectionStateEnum, ConnectionEventEnum> transitions) throws Exception {
        transitions
                // 1.连接事件: 未连接 -> 已连接
                .withExternal()
                .source(ConnectionStateEnum.UNCONNECTED)
                .target(ConnectionStateEnum.CONNECTED)
                .event(ConnectionEventEnum.CONNECT)
                // 2.注册事件: 已连接 -> 注册中
                .and()
                .withExternal()
                .source(ConnectionStateEnum.CONNECTED)
                .target(ConnectionStateEnum.REGISTERING)
                .event(ConnectionEventEnum.REGISTER)
                // 3.注册成功事件: 注册中 -> 已注册
                .and()
                .withExternal()
                .source(ConnectionStateEnum.REGISTERING)
                .target(ConnectionStateEnum.REGISTERED)
                .event(ConnectionEventEnum.REGISTER_SUCCESS)
                // 4.注销事件: 已连接 -> 未连接
                .and()
                .withExternal()
                .source(ConnectionStateEnum.CONNECTED)
                .target(ConnectionStateEnum.UNCONNECTED)
                .event(ConnectionEventEnum.UN_REGISTER)
                // 5.注册中 -> 未连接
                .and()
                .withExternal()
                .source(ConnectionStateEnum.REGISTERING)
                .target(ConnectionStateEnum.UNCONNECTED)
                .event(ConnectionEventEnum.UN_REGISTER)
                // 6.已注册 -> 未连接
                .and()
                .withExternal()
                .source(ConnectionStateEnum.REGISTERED)
                .target(ConnectionStateEnum.UNCONNECTED)
                .event(ConnectionEventEnum.UN_REGISTER);
    }
}
