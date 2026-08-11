package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerDTO {

    private Long id;

    private String sellerName;

    private String sellerAddress;

    private ItemStatus status;
}
