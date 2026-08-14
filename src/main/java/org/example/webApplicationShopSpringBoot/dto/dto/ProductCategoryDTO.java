package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategoryDTO {

    private Long id;

    private String category;

    private ItemStatus status;
}
