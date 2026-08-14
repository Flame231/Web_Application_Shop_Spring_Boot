package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String productName;

    private ProductCategoryDTO productCategory;

    private BigDecimal price;

    private SellerDTO seller;

    private ItemStatus status;

    private Timestamp updateDateTime;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productCategory=" + productCategory +
                ", price=" + price +
                ", seller=" + seller +
                ", status=" + status +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}
