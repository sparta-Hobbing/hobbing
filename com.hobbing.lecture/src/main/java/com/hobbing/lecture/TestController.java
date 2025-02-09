package com.hobbing.lecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/lecture/health")
    public String signup() {
        return "Test 확인";
    }

}
