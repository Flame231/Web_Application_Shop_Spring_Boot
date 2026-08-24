package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class BagSumWithDiscountDTO {

    private BigDecimal fullPrice;

    private Integer discountValue;

    private BigDecimal calculatedDiscount;

    private BigDecimal priceWithDiscount;
}
