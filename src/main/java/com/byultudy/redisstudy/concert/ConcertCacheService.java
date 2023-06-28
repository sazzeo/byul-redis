package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.exception.BadRequestException;
import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConcertCacheService {

    private final RedisRepository redisRepository;
    private final RedissonClient redissonClient;
    private final String CONCERT_PREFIX = "concert:";

    private final ConcertRepository concertRepository;

    public Long getQuantity(Long concertId) {
        String key = CONCERT_PREFIX + "quantity:" + concertId;
        RLock lock = redissonClient.getLock(key);
        try {
            lock.tryLock(1, 3, TimeUnit.SECONDS);
            String ticketQuantity = redisRepository.get(key);
            if (ticketQuantity != null) {
                long lTicketQuantity = Long.parseLong(ticketQuantity);
                if (lTicketQuantity > 0) {
                    return lTicketQuantity;
                }
            }
            final Long lTicketQuantity = concertRepository
                    .findById(concertId)
                    .orElseThrow(() -> new BadRequestException("콘서트가 존재하지 않습니다."))
                    .getTicketQuantity();
            redisRepository.set(key, lTicketQuantity);
            return lTicketQuantity;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // TODO 콘서트 유효한지 판단?
    @Transactional
    public void setTicketQuantity(Long concertId, Long quantity) {
        String key = CONCERT_PREFIX + "quantity:" + concertId;
        RLock lock = redissonClient.getLock(key);
        try {
            lock.tryLock(1, 3, TimeUnit.SECONDS);
            String ticketQuantity = redisRepository.get(key);
            if (ticketQuantity == null) {
                throw new BadRequestException("유효하지 않은 요청입니다.");
            }
            Long lTicketQuantity = Long.parseLong(ticketQuantity);
            if (lTicketQuantity < 0) {
                Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new BadRequestException("콘서트가 존재하지 않습니다"));
            }
            redisRepository.set(key, lTicketQuantity - quantity);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
