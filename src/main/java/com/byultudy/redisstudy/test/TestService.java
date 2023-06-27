package com.byultudy.redisstudy.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TestService {

    private final TestRepository testRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String create(TestDto testDto) {
        Test test = TestDto.toEntity(testDto);
        Runnable runnable = ()-> log.info("실행");
        eventPublisher.publishEvent("runnable");
        testRepository.save(test);
        return "성공";
    }
}
