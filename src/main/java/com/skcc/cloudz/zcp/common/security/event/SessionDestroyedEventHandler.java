package com.skcc.cloudz.zcp.common.security.event;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionDestroyedEventHandler implements ApplicationListener<SessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        if (log.isDebugEnabled()) {
            log.debug(" ----------------------------------------------------  ");
            log.debug(" SessionDestroyedEvent getId : {}", event.getId());
            log.debug(" SessionDestroyedEvent getTimestamp : {}", event.getTimestamp());
            log.debug(" ----------------------------------------------------  ");
        }
    }
}
