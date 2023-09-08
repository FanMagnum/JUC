package com.lone.demo.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(5);

        log.info("{}\t{}", integer.compareAndSet(5, 2023), integer.get());
        log.info("{}\t{}", integer.compareAndSet(5, 2023), integer.get());
    }
}
