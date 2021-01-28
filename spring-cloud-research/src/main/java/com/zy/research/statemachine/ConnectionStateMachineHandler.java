package com.zy.research.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * http://www.uml.org.cn/zjjs/201911151.asp
 * https://segmentfault.com/a/1190000009906317
 *
 * spring 状态机使用步骤
 * 1.定义状态枚举。 {@link ConnectionStateEnum}
 * 2.定义事件枚举。 {@link ConnectionEventEnum}
 * 3.定义状态机配置，设置初始状态，以及状态与事件之间的关系。 {@link ConnectionStateMachineConfig}
 * 4.定义状态监听器，当状态变更时，触发方法。 {@link ConnectionStateMachineEventConfig}
 *
 */
@Component
public class ConnectionStateMachineHandler {

    @Autowired
    private StateMachine<ConnectionStateEnum, ConnectionEventEnum> connectionStateMachine;

    public void handle(ConnectionEventEnum connectionEvent) {
        connectionStateMachine.sendEvent(connectionEvent);
    }

    @PostConstruct
    public void init() {
        connectionStateMachine.start();
        handle(ConnectionEventEnum.CONNECT);
    }

}
