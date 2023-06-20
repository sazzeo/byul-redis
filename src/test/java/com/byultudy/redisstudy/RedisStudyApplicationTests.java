package com.byultudy.redisstudy;

import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.concert.ConcertRepository;
import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.customer.Customer;
import com.byultudy.redisstudy.customer.CustomerRepository;
import com.byultudy.redisstudy.redis.RedisRepository;
import com.byultudy.redisstudy.ticket.Ticket;
import com.byultudy.redisstudy.ticket.TicketRepository;
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
    CustomerRepository customerRepository;
    @Autowired
    TicketRepository ticketRepository;

    Concert concert;

    @BeforeEach
    void setUp() {
        initConcert();

    }

    void initConcert() {
        concertRepository.deleteAll();
        concert = Concert.builder()
                .id(1L)
                .ticketQuantity(1000L)
                .targetDateTime(LocalDateTime.now())
                .build();
        concertRepository.save(concert);
    }

    @Test
    void 티켓예매() throws InterruptedException {
        int threadCount = 100;

        //ExecutorService : 각기 다른 Thread를 생성해서 작업을 처리하고 완료되면 Thread를 제거하는 작업을 자동으로 해줌
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 사용하는 작업을 기다려주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    customerRepository.save(Customer.builder().id(userId).name(userId + "").build());
//                    ticketRepository.save(Ticket.builder().)
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); //메인스레드 멈춤 (위 코드가 다 실행되고 아래 코드를 실행시켜준다)

//        assertThat(count).isEqualTo(100);

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

    //캐시 config 관련설정 해야함
}
