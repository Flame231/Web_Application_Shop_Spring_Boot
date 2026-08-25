package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
public class BagFormDTO {
    private Long orderPointId;
    private List<Long> productId;
    private List<BigDecimal> productPrice;
    private List<Long> count;


}

