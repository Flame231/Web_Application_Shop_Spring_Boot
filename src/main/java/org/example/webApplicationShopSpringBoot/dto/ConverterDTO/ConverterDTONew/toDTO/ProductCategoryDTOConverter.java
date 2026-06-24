package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryDTOConverter implements Converter<ProductCategory, ProductCategoryDTO> {
    @Override
    public ProductCategoryDTO convert(ProductCategory productCategory) {
        return ProductCategoryDTO.builder().id(productCategory.getId())
                .category(productCategory.getCategory()).build();
    }
}
