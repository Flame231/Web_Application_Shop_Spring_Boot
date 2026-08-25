package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BagSumWithDiscountDTO {

    private BigDecimal fullPrice;

    private Integer discountValue;

    private BigDecimal calculatedDiscount;

    private BigDecimal priceWithDiscount;
}
