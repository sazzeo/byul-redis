package com.byultudy.redisstudy.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class ConcertDto {

    private Long id;

    private Long ticketQuantity;

    public static Concert toEntity(ConcertDto concertDto) {
        return Concert.builder()
                .id(concertDto.getId())
                .ticketQuantity(concertDto.getTicketQuantity())
                .build();
    }
}