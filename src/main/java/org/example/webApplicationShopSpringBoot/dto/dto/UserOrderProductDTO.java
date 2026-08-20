package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class UserOrderProductDTO {

    private Long userOrderId;

    private ProductDTO productDTO;

    private Long productCount;

    private Long actualProductCount;

    private BigDecimal productPrice;

    @Override
    public String toString() {
        return "UserOrderProductDTO{" +
                "productCount=" + productCount +
                ", actualProductCount=" + actualProductCount +
                ", productPrice=" + productPrice +
                ", userOrderId=" + userOrderId +
                '}';
    }
}
