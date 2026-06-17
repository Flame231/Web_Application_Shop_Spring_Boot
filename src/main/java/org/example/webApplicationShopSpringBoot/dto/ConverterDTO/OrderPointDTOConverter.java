package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;

public class OrderPointDTOConverter implements ConverterDTO<OrderPoint, OrderPointDTO> {
    @Override
    public OrderPointDTO toDTO(OrderPoint orderPoint) {
        return OrderPointDTO.builder().id(orderPoint.getId()).
                orderPointAddress(orderPoint.getOrderPointAddress())
                .openingTime(orderPoint.getOpeningTime()).closeTime(orderPoint.getCloseTime())
                .build();
    }

    @Override
    public OrderPoint toEntity(OrderPointDTO orderPointDTO) {
        return OrderPoint.builder().orderPointAddress(orderPointDTO.getOrderPointAddress())
                .openingTime(orderPointDTO.getOpeningTime())
                .closeTime(orderPointDTO.getCloseTime())
                .id(orderPointDTO.getId())
                .build();
    }
}
