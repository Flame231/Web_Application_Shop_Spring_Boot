package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderPointEntityConverter implements Converter<OrderPointDTO, OrderPoint> {
    @Override
    public OrderPoint convert(OrderPointDTO orderPointDTO) {
        return OrderPoint.builder().orderPointAddress(orderPointDTO.getOrderPointAddress())
                .openingTime(orderPointDTO.getOpeningTime())
                .closeTime(orderPointDTO.getCloseTime())
                .id(orderPointDTO.getId())
                .build();
    }
}
