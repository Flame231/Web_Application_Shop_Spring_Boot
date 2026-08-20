package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDiscountDTO {

    private Integer discount;

    private BigDecimal sumOfPurchases;

    @Override
    public String toString() {
        return "UserDiscountDTO{" +
                "discount=" + discount +
                ", sumOfPurchases=" + sumOfPurchases +
                '}';
    }
}
