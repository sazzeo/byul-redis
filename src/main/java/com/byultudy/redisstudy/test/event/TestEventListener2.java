package com.byultudy.redisstudy.test.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Order(1)
@Component
public class TestEventListener2 {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit3(String str) {
        log.info("afterCommit3 : {} " , str);
    }
}
