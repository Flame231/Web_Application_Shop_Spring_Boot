package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderDTO {

    private Long userId;

    private Integer userOrderId;

    private Long orderPointId;

    private Long productId;

    private Long Count;

    private BigDecimal productPrice;

}
