package com.byultudy.redisstudy.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TestService {

    private final TestRepository testRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final RedissonClient redissonClient;
    private static int count = 0;

    @Transactional
    public String create(TestDto testDto) {
        RLock lock = redissonClient.getLock("lock");
        try {
            lock.tryLock(1, 3, TimeUnit.SECONDS);
            if (count < 1000) {
                Test test = TestDto.toEntity(testDto);
                testRepository.save(test);
                count++;
                lock.unlock();
                return "성공";
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return "실패";
    }
}
