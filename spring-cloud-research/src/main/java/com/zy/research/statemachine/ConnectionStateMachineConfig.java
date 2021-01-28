package com.zy.research.statemachine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import java.util.EnumSet;

/**
 * https://gitee.com/wphmoon/statemachine
 * https://segmentfault.com/a/1190000019466502?utm_source=tag-newest
 * https://www.jianshu.com/p/1a4947a099e9?from=singlemessage
 *
 * Transition: 节点，是组成状态机引擎的核心
 * source：节点的当前状态
 * target：节点的目标状态
 * event：触发节点从当前状态到目标状态的动作
 * guard：起校验功能，一般用于校验是否可以执行后续action
 * action：用于实现当前节点对应的业务逻辑处理
 *
 */
@Configuration
@EnableStateMachine
public class ConnectionStateMachineConfig extends EnumStateMachineConfigurerAdapter<ConnectionStateEnum, ConnectionEventEnum> {

    /**
     * 初始化状态机状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<ConnectionStateEnum, ConnectionEventEnum> states) throws Exception {
        states.withStates()
                // 定义初始状态
                .initial(ConnectionStateEnum.UNCONNECTED)
                // 定义状态机状态集合
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
                // 状态来源
                .source(ConnectionStateEnum.UNCONNECTED)
                // 状态目标
                .target(ConnectionStateEnum.CONNECTED)
                // 触发事件: 导致当前变化的动作/事件
                .event(ConnectionEventEnum.CONNECT)
                // 起校验功能，一般用于校验是否可以执行后续action
                // .guard()
                // 执行当前状态变更导致的业务逻辑处理，以及出异常时的处理
                // .action()

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
