package com.byultudy.redisstudy.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class TestDto {

    private Long id;

    private Long userId;

    @Builder.Default
    private LocalDateTime at = LocalDateTime.now();

    public static Test toEntity(TestDto testDto) {
        return Test.builder()
                .userId(testDto.getUserId())
                .at(LocalDateTime.now())
                .build();
    }
}
