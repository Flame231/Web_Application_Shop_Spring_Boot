package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;

import java.util.List;

public record ProductPagesDivide(List<ProductDTO> productDTOList, Integer productCountResult, Integer currentPage) {
}
