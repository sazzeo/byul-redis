package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConcertService {
    private final ConcertRepository concertRepository;

    @Cacheable(value = "concert", key = "#id")
    public long getQty(Long id) {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new RuntimeException("콘서트가 존재하지 않습니다."));
        return concert.getTicketQuantity();
    }

    @Transactional
    @CachePut(value = "concert", key = "#id")
    /** 예매 수량  */
    public Long reserve(Long id, Long qty) {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new RuntimeException("콘서트가 존재하지 않습니다."));
        concert.subtractTicketQuantity(qty);
        return concert.getTicketQuantity();
    }

}
