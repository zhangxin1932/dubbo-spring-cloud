package com.zy.research.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
