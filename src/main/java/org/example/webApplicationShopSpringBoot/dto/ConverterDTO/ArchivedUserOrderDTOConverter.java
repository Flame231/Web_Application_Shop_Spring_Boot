package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;

import java.util.stream.Collectors;

public class ArchivedUserOrderDTOConverter implements ConverterDTO<ArchivedUserOrder, ArchivedUserOrderDTO> {
    @Override
    public ArchivedUserOrder toEntity(ArchivedUserOrderDTO archivedUserOrderDTO) {
        return null;
    }

    @Override
    public ArchivedUserOrderDTO toDTO(ArchivedUserOrder archivedUserOrder) {
        ConverterDTO<ArchivedUserOrderProduct, ArchivedUserOrderProductDTO> converterDTO = new ArchivedUserOrderProductDTOConverter();
        return ArchivedUserOrderDTO
                .builder()
                .UserOrderId(archivedUserOrder.getUserOrderId())
                .orderStatus(archivedUserOrder.getOrderStatus())
                .userId(archivedUserOrder.getUserId())
                .OrderPoint(archivedUserOrder.getOrderPoint())
                .orderSum(archivedUserOrder.getOrderSum())
                .finalOrderSum(archivedUserOrder.getFinalOrderSum())
                .userOrderCreateDateTime(archivedUserOrder.getUserOrderCreateDateTime())
                .archivedUserOrderProducts(archivedUserOrder
                        .getArchivedUserOrderProducts()
                        .stream().map(converterDTO::toDTO).collect(Collectors.toSet()))
                .createDateTime(archivedUserOrder.getCreateDateTime())
                .build();
    }
}
