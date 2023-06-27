package com.byultudy.redisstudy.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository  extends JpaRepository<Ticket , Long> {
    Long countByConcertId(Long concertId);
}
