package com.hobbing.reservation_pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/reservation/health")

    public ResponseEntity<Void> signup() {
        return ResponseEntity.ok(null);
    }

}
