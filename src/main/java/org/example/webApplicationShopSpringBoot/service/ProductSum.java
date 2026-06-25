package org.example.webApplicationShopSpringBoot.service;


import java.math.BigDecimal;
import java.util.stream.Stream;

public interface ProductSum<T> {
    Long getCount();
    BigDecimal getPrice();
}
