package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerDTO {

    private Long id;

    private String sellerName;

    private String sellerAddress;
}
