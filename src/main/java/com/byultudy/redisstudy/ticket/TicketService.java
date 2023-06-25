package com.byultudy.redisstudy.ticket;

import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void create(Long concertId, Long customerId) {
//        Long qty = concertService.getQty(concertId);
////        Long count = redisRepository.getCount(KEY_PREFIX + concertId);
//        if (qty.equals(0L)) {
//            throw new RuntimeException("티켓이 모두 소진되었습니다.");
//        }
//        if (count > qty) {
//            concertService.reserve(concertId, qty);
//            throw new RuntimeException("티켓이 모두 소진되었습니다.");
//        }
//        //티켓 발행
//        ticketRepository.save(Ticket.builder()
//                .concertId(concertId)
//                .customerId(customerId)
//                .build());
    }

    public Long count(Long concertId) {
        return ticketRepository.countByConcertId(concertId);
    }

    @Transactional
    public TicketDto create(TicketDto ticketDto) {
        Concert concert = concertService.reserve(ticketDto.getConcertId());
        if (concert.getTicketQuantity().equals(0L)) {
            throw new RuntimeException("티켓이 모두 소진 되었습니다.");
        }
        Ticket ticket = TicketDto.toEntity(ticketDto);
        return TicketDto.toDto(ticketRepository.save(ticket));
    }


}
