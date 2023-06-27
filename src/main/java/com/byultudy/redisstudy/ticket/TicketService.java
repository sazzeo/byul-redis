package com.byultudy.redisstudy.ticket;

import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RedisRepository redisRepository;
    private final ConcertService concertService;

    private final String KEY_PREFIX = "count:ticket:";

    private final RedissonClient redissonClient;

    public Long count(Long concertId) {
        return ticketRepository.countByConcertId(concertId);
    }

    @Transactional
    public TicketDto create(TicketDto ticketDto) {
        Long qty = concertService.getReserveTicketCount(ticketDto.getConcertId());
        if (qty > concertService.getTicketQuantity(ticketDto.getConcertId())) {
            throw new RuntimeException("티켓이 모두 소진 되었습니다.");
        }
        Ticket ticket = TicketDto.toEntity(ticketDto);
        return TicketDto.toDto(ticketRepository.save(ticket));
    }

}
