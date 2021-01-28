package com.zy.research.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConnectionStateMachineHandler {

    @Autowired
    private StateMachine<ConnectionStateEnum, ConnectionEventEnum> stateMachine;

    public void handle(ConnectionEventEnum connectionEvent) {
        stateMachine.sendEvent(connectionEvent);
    }

    @PostConstruct
    public void init() {
        stateMachine.start();
        handle(ConnectionEventEnum.CONNECT);
    }

}
