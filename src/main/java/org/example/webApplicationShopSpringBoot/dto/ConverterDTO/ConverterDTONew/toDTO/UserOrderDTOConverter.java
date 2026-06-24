package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
@Component
@AllArgsConstructor
public class UserOrderDTOConverter implements Converter<UserOrder, UserOrderDTO> {
    @Lazy
    private ConversionService conversionService;

    @Override
    public UserOrderDTO convert(UserOrder userOrder) {
        return UserOrderDTO.builder().orderId(userOrder.getId()).orderStatus(userOrder.getOrderStatus()).orderPoint(userOrder.getOrderPoint().getOrderPointAddress()).orderSum(userOrder.getOrderSum())
                .OrderCreateDateTime(userOrder.getCreateDateTime())
                .userOrderProducts(userOrder.getUserOrderProduct()
                        .stream().map(userOrderProduct -> conversionService.convert(userOrderProduct, UserOrderProductDTO.class))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }
}
