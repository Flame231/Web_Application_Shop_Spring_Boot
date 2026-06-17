package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
public class ArchivedUserOrderProductDTO {

    private ArchivedUserOrder archivedUserOrder;

    private Long productId;

    private String productName;

    private Integer productCount;

    private Integer finalProductCount;

    private BigDecimal price;

    private Timestamp createDateTime;

}
