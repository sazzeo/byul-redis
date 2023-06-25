package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final RedisRepository redisRepository;

    private final String CONCERT_PREFIX = "concert";

    @Transactional
    public void create(ConcertDto concertDto) {
        Concert concert = ConcertDto.toEntity(concertDto);
        concertRepository.save(concert);
        redisRepository.set(CONCERT_PREFIX + ":ticketQuantity:" + concert.getId() ,  concert.getTicketQuantity());
        redisRepository.set(CONCERT_PREFIX + ":count:" + concert.getId(), 0L);
    }

    public Long getTicketQuantity(Long concertId) {
        String quantity = redisRepository.get(CONCERT_PREFIX + ":ticketQuantity:" + concertId);
        if(quantity == null) {
            Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new RuntimeException("콘서트가 존재하지 않습니다."));
            Long qty =  concert.getTicketQuantity();
            redisRepository.set(CONCERT_PREFIX + ":ticketQuantity:" + concertId , qty);
            return qty;
        }
        return Long.parseLong(quantity);
    }

    public Long getReserveTicketCount(Long concertId) {
        return redisRepository.increment(CONCERT_PREFIX + ":count:" + concertId);
    }

    @Transactional
    public void updateTicketQuantity(Long concertId,Long qty) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new RuntimeException("콘서트가 존재하지 않습니다."));
        concert.subtractTicketQuantity(qty);
    }

    @Transactional
    public Concert reserve(Long id) {
        Concert concert = redisRepository.getHash(CONCERT_PREFIX, id, Concert.class).orElseGet(() -> {
            Concert c = concertRepository.findById(id).orElseThrow(() -> new RuntimeException("콘서트가 존재하지 않습니다."));
            redisRepository.setHash(CONCERT_PREFIX, id, c);
            return c;
        });

        concert.subtractTicketQuantity(1L);
        redisRepository.setHash(CONCERT_PREFIX, id, concert);
        return concert;
    }
}
