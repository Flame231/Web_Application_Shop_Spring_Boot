package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.NewProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductConverter {
    @Mapping(target = "productCategory.id", source = "productCategory")
    @Mapping(target = "seller.id", source = "seller")
    @Mapping(target = "status", defaultExpression =
            "java(org.example.webApplicationShopSpringBoot.model.ItemStatus.ACTIVE)")
    Product toEntity(NewProductDTO newProductDTO);

    ProductDTO toDTO(Product product);

   /* @Mapping(target = "productCategory", source = "productCategory")
    @Mapping(target = "seller", source = "seller")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntity(NewProductDTO newProductDTO, @MappingTarget Product product);*/

}
