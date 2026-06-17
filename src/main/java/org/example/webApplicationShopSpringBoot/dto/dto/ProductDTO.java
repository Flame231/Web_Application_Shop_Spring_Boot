package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String productName;

    private ProductCategoryDTO productCategory;

    private BigDecimal price;

    private SellerDTO seller;

    private Timestamp updateDateTime;

    @Override
    public String toString() {
        return "NewProductDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productCategory=" + productCategory +
                ", price=" + price +
                ", seller=" + seller +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}
