package com.byultudy.redisstudy.ticket;

import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RedisRepository redisRepository;

    private String KEY_PREFIX = "reserve-";

    public void create(Ticket ticket) {
        Long count = redisRepository.getCount(KEY_PREFIX+ticket.getConcertId());

//        if(count)
    }

}
