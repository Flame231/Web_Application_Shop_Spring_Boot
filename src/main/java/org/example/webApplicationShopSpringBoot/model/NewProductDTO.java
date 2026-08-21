package org.example.webApplicationShopSpringBoot.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class NewProductDTO {

    private Long id;

    private String productName;

    private BigDecimal price;

    private Long productCategory;

    private Long seller;

    private ItemStatus status;

}
