package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderPointDTOConverter implements Converter<OrderPoint, OrderPointDTO> {
    @Override
    public OrderPointDTO convert(OrderPoint orderPoint) {
        return OrderPointDTO.builder().id(orderPoint.getId()).
                orderPointAddress(orderPoint.getOrderPointAddress())
                .openingTime(orderPoint.getOpeningTime()).closeTime(orderPoint.getCloseTime())
                .build();
    }
}
