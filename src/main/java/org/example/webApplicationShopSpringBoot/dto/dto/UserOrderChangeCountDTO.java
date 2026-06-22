package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderChangeCountDTO {
    private Long userOrderId;
    private Long productId;
    private Long count;
}
