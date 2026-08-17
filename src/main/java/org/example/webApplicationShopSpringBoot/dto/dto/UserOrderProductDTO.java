package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserOrderProductDTO {

    private Long userOrderId;

    private ProductDTO productDTO;

    private Long productCount;

    private Long actualProductCount;
}
