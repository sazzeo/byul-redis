package com.byultudy.redisstudy;

import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.concert.ConcertRepository;
import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.redis.RedisRepository;
import com.byultudy.redisstudy.ticket.TicketRepository;
import com.byultudy.redisstudy.ticket.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RedisStudyApplicationTests {

    @Autowired
    ConcertRepository concertRepository;
    @Autowired
    ConcertService concertService;
    @Autowired
    RedisRepository redisRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService ticketService;

    Concert concert;

    @BeforeEach
    void setUp() {
        deleteAll();
        initConcert();

    }

    void deleteAll() {
        ticketRepository.deleteAll();
        concertRepository.deleteAll();
    }

    void initConcert() {
        concert = Concert.builder()
                .id(1L)
                .ticketQuantity(100L)
                .targetDateTime(LocalDateTime.now())
                .build();
        concertRepository.save(concert);
    }

    @DisplayName("캐시테스트")
    @Test
    void redisTest() {
        for (int i = 0; i <= 1000; i++) {
            long qty = concertService.getQty(concert.getId());
            System.out.println("qty = " + qty);
            if (i % 10 == 0) {
                concertService.reserve(concert.getId(), 10L);
            }
        }
    }

    @DisplayName("티켓예매")
    @Test
    void 티켓예매() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    ticketService.create(concert.getId(), userId);
                } catch (Exception e) {
                    System.out.println("user"+userId+"님 " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        assertThat(ticketService.count(concert.getId())).isEqualTo(concert.getTicketQuantity());
    }
    
    @Test
    void reserveTest() {
        Long reserve = concertService.reserve(1L, 50L);
    }

}
