package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserOrderConverter {


    @Mapping(target = "orderPoint", expression = "java(userOrder" +
            ".getOrderPoint().getOrderPointAddress())")
    UserOrderDTO toDTO(UserOrder userOrder);
}
