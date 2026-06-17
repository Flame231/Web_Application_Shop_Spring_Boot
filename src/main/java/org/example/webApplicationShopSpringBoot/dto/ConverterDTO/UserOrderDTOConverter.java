package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Qualifier("converterDTO")
@Component
public class UserOrderDTOConverter implements ConverterDTO<UserOrder, UserOrderDTO> {
    private ConverterDTO<UserOrderProduct, UserOrderProductDTO> converterDTO;

    public UserOrderDTOConverter(ConverterDTO<UserOrderProduct, UserOrderProductDTO> converterDTO) {
        this.converterDTO = converterDTO;
    }

    @Override
    public UserOrder toEntity(UserOrderDTO userOrderDTO) {
        return null;
    }

    @Override
    public UserOrderDTO toDTO(UserOrder userOrder) {
        return UserOrderDTO.builder().orderId(userOrder.getId()).orderStatus(userOrder.getOrderStatus()).orderPoint(userOrder.getOrderPoint().getOrderPointAddress()).orderSum(userOrder.getOrderSum())
                .OrderCreateDateTime(userOrder.getCreateDateTime())
                .userOrderProducts(userOrder.getUserOrderProduct()
                        .stream().map(converterDTO::toDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }
}
