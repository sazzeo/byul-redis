package com.byultudy.redisstudy;

import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.concert.ConcertDto;
import com.byultudy.redisstudy.concert.ConcertRepository;
import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.redis.RedisRepository;
import com.byultudy.redisstudy.ticket.TicketDto;
import com.byultudy.redisstudy.ticket.TicketRepository;
import com.byultudy.redisstudy.ticket.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    ConcertDto concertDto;

    @BeforeEach
    void setUp() {
        initConcert();
    }

    void initConcert() {
        concertDto = ConcertDto.builder()
                .ticketQuantity(100L)
                .build();
        concertService.create(concertDto);
    }

    @Test
    void reserveTest() {
        redisRepository.setHash("concert", 1L, concert);
        ConcertDto concert1 = redisRepository.getHash("concert", 1L, ConcertDto.class).orElseThrow(() -> new RuntimeException("null에러"));
        System.out.println("concert1 = " + concert1);
    }


    @Test
    void ticketServiceTest() throws InterruptedException {
        Executor executor = new Executor(32, 1000);

        executor.execute(() -> {
            Long userId = (long) executor.getCount();
            try {
                TicketDto ticketDto = TicketDto.builder()
                        .concertId(1L)
                        .customerId(userId)
                        .build();
                ticketService.create(ticketDto);
            } catch (Exception e) {
                System.out.println("user" + userId + "님 " + e.getMessage());
            }
        });
    }

    @Test
    void concertTest() {
        for (long i = 0; i < 200; i++) {
            TicketDto ticketDto = TicketDto.builder().customerId(i).concertId(1L).build();
            ticketService.create(ticketDto);
        }
    }
}