package org.example.webApplicationShopSpringBoot.dto.converterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserOrderProductConverter.class})
public interface UserOrderConverter {


    @Mapping(target = "orderPoint", expression = "java(userOrder" +
            ".getOrderPoint().getOrderPointAddress())")
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "createDateTime", target = "OrderCreateDateTime")
    @Mapping(source = "userOrderProduct", target = "userOrderProducts")
    UserOrderDTO toDTO(UserOrder userOrder);
}
