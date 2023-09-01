package com.lone.demo.completableFuture;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureMallDemo {
    private static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    private static List<String> getPrice(String productName) {
        return list.stream()
                .map(netMall -> String.format("《%s》 in %s price is %.2f", productName, netMall.getNetMallName(), netMall.calculatePrice(productName)))
                .collect(Collectors.toList());
    }

    private static List<String> getPriceByCompletableFuture(String productName) {
        return list.stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format("《%s》 in %s price is %.2f", productName, netMall.getNetMallName(), netMall.calculatePrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        getPrice("mysql").forEach(log::info);
        log.info("cost time:{}", (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        getPriceByCompletableFuture("mysql").forEach(log::info);
        log.info("cost time:{}", (System.currentTimeMillis() - start));
    }
}

class NetMall{
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calculatePrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}