package org.example.webApplicationShopSpringBoot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public class Calculate {

    public static <T extends ProductSum> BigDecimal calculateSum(Stream<T> stream) {
        return stream.map(e -> e.getPrice().multiply(new BigDecimal(e.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T extends ProductSum> BigDecimal calculateSum(Stream<T> stream, Integer discount) {
        BigDecimal fullPrice = stream.map(e -> e.getPrice().multiply(new BigDecimal(e.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal calculatedDiscount = fullPrice.multiply(new BigDecimal(discount))
                .divide(new BigDecimal(100), 2, RoundingMode.UP);
        return fullPrice.subtract(calculatedDiscount);
    }
}
