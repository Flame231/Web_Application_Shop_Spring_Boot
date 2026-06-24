package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryEntityConverter implements Converter<ProductCategoryDTO, ProductCategory> {
    @Override
    public ProductCategory convert(ProductCategoryDTO productCategoryDTO) {
        if (productCategoryDTO.getId() != null) {
            return ProductCategory.builder()
                    .products(null).category(productCategoryDTO.getCategory())
                    .id(productCategoryDTO.getId()).build();
        }
        return ProductCategory.builder()
                .products(null).category(productCategoryDTO.getCategory())
                .build();
    }
}
