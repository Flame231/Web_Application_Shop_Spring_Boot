package org.example.webApplicationShopSpringBoot.service;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class Calculate {

    public static < T extends ProductSum> BigDecimal calculateSum(Stream<T> stream) {
        return stream.map(e -> e.getPrice().multiply(new BigDecimal(e.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
