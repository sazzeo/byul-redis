package com.byultudy.redisstudy.ticket;

import com.byultudy.redisstudy.concert.ConcertService;
import com.byultudy.redisstudy.exception.SoldOutException;
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


    public Long count(Long concertId) {
        return ticketRepository.countByConcertId(concertId);
    }
    //최초 티켓 예매가 들어올때 : 콘서트 남은 수량을 => 레디스에 저장한다.
    //티켓 예매수량
    //티켓 남은 수량 확인 하기
    @Transactional
    public TicketDto create(TicketDto ticketDto) {
        Long qty = concertService.getReserveTicketCount(ticketDto.getConcertId());
        if (qty > concertService.getTicketQuantity(ticketDto.getConcertId())) {
            throw new SoldOutException("티켓이 모두 소진 되었습니다.");
        }
        Ticket ticket = TicketDto.toEntity(ticketDto);
        return TicketDto.toDto(ticketRepository.save(ticket));
    }

}
