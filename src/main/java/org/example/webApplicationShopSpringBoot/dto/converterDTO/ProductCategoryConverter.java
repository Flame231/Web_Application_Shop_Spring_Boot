package org.example.webApplicationShopSpringBoot.dto.converterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductCategoryConverter {
    @Mapping(target = "status", source = "status", defaultExpression =
            "java(org.example.webApplicationShopSpringBoot.model.ItemStatus.ACTIVE)")
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO);

    ProductCategoryDTO toDTO(ProductCategory productCategory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductCategory updateEntity(ProductCategoryDTO productCategoryDTO, @MappingTarget ProductCategory productCategory);
}
