package com.byultudy.redisstudy;

import com.byultudy.redisstudy.customer.Customer;
import com.byultudy.redisstudy.redis.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisTest {

    RedisRepository redisRepository;

    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        redisConnectionFactory.afterPropertiesSet();

        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        redisRepository = new RedisRepository(redisTemplate);
    }

    int rsvCount = 0;

    @Test
    void redisTest() throws InterruptedException {
        int threadCount = 300;

        redisRepository.setCount("a", 100L);
        //ExecutorService : 각기 다른 Thread를 생성해서 작업을 처리하고 완료되면 Thread를 제거하는 작업을 자동으로 해줌
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 사용하는 작업을 기다려주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    Long a = redisRepository.getCount("a");
                    rsvCount++;
                } catch (Exception e) {
                    System.out.println("티켓이 모두 소진되었습니다.");
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); //메인스레드 멈춤 (위 코드가 다 실행되고 아래 코드를 실행시켜준다)


        System.out.println("예약수: " + rsvCount);
    }
}
