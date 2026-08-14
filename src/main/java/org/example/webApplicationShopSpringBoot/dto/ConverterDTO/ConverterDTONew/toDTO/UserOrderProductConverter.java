package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserOrderProductConverter {



    UserOrderProductDTO toDTO(UserOrderProduct userOrderProduct);


}
