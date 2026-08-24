package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;

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

    @Override
    public String toString() {
        return "NewProductDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", productCategory=" + productCategory +
                ", seller=" + seller +
                ", status=" + status +
                '}';
    }
}
