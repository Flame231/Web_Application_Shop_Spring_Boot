package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryDTOConverter implements ConverterDTO<ProductCategory, ProductCategoryDTO> {

    @Override
    public ProductCategoryDTO toDTO(ProductCategory productCategory) {
        return ProductCategoryDTO.builder().id(productCategory.getId())
                .category(productCategory.getCategory()).build();
    }

    @Override
    public ProductCategory toEntity(ProductCategoryDTO productCategoryDTO) {
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
