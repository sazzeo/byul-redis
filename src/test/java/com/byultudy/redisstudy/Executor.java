package com.byultudy.redisstudy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

    private final int threadCount;

    private int count;
    private final ExecutorService executorService;
    private final CountDownLatch latch;

    public Executor(final int threadPool, final int threadCount) {
        this.threadCount = threadCount;
        this.executorService = Executors.newFixedThreadPool(threadPool);
        this.latch = new CountDownLatch(threadCount);
    }

    public void execute(Runnable runnable) throws InterruptedException {
        for (int i = 0; i < this.threadCount; i++) {
            executorService.submit(() -> {
                try {
                    runnable.run();
                } finally {
                    count++;
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    public int getCount() {
        return this.count;
    }
}
