package com.byultudy.redisstudy.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {
    private final TestService testService;
    @PostMapping
    public String jmeterTest(@RequestBody TestDto testDto) {
        return testService.create(testDto);
    }
}
