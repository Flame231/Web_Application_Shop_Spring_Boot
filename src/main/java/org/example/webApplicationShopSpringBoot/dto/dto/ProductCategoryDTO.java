package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategoryDTO {

    private Long id;

    private String category;
}
