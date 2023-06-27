package com.byultudy.redisstudy.test.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class TestEventListener {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(Runnable event) {
        log.info("beforeCommit");
        event.run();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(Runnable event) {
        log.info("afterCommit");
        event.run();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(String str) {
        log.info("afterCommit : {} " , str);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit2(String str) {
        log.info("afterCommit2 : {} " , str);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(Runnable event) {
        log.info("afterRollback");
        event.run();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(String str) {
        log.info("afterRollback : {} " , str);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletion(Runnable event) {
        log.info("afterCompletion");
        event.run();
    }

}
