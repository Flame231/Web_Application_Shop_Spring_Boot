package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductBagDTO {

    private Integer productId;

    private String productName;

    private String category;

    private BigDecimal price;

    private String sellerName;

    private Integer count;

}
