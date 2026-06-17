package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductCategoryDTO {

    private Long id;

    private String category;
}
