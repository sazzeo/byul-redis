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
        redisRepository.set(CONCERT_PREFIX + ":count:" + concert.getId(), 0L); //티켓 예매된 수량
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
    //read 락 걸어야 됨??

    public Long getReserveTicketCount(Long concertId) {
        return redisRepository.increment(CONCERT_PREFIX + ":count:" + concertId);
    }

}
