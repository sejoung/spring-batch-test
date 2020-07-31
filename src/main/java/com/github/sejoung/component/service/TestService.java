package com.github.sejoung.component.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    public void test() {
        log.info("test service");
    }
}
