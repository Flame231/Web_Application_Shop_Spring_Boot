package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SellerDTO {

    private Long id;

    private String sellerName;

    private String sellerAddress;
}
