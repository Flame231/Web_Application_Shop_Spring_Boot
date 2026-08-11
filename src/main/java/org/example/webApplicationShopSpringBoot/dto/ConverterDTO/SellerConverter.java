package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SellerConverter {

    @Mapping(target="status", source = "status", defaultExpression =
            "java(org.example.webApplicationShopSpringBoot.model.ItemStatus.ACTIVE)")
    Seller toEntity(SellerDTO sellerDTO);

    SellerDTO toDTO(Seller seller);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Seller updateSeller(SellerDTO sellerDTO, @MappingTarget Seller seller);

}
