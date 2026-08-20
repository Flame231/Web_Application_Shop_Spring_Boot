package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductConverter.class})
public interface UserOrderProductConverter {


@Mapping(source = "userOrder.id",target = "userOrderId")
@Mapping(source = "product",target = "productDTO")
    UserOrderProductDTO toDTO(UserOrderProduct userOrderProduct);
}
