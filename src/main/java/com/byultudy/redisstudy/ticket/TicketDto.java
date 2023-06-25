package com.byultudy.redisstudy.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class TicketDto {

    private Long id;

    private Long customerId;

    private Long concertId;

    private boolean vip;

    @Builder.Default
    private LocalDateTime issueDate = LocalDateTime.now();

    public static Ticket toEntity(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.getId())
                .customerId(ticketDto.getCustomerId())
                .concertId(ticketDto.getConcertId())
                .vip(ticketDto.isVip())
                .issueDate(ticketDto.getIssueDate())
                .build();
    }

    public static TicketDto toDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .customerId(ticket.getCustomerId())
                .concertId(ticket.getConcertId())
                .vip(ticket.isVip())
                .issueDate(ticket.getIssueDate())
                .build();
    }
}
