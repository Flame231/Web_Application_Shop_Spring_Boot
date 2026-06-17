package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class BagDTO {

    private Long userId;

    private ProductDTO product;

    private Integer count;
}