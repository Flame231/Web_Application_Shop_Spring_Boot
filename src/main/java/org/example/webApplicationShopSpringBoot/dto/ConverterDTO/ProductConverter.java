package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductConverter {
    @Mapping(target="status", source = "status", defaultExpression =
            "java(org.example.webApplicationShopSpringBoot.model.ItemStatus.ACTIVE)")
    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntity(ProductDTO productDTO, @MappingTarget Product product);

}
