package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ArchivedUserOrderDTOConverter implements Converter<ArchivedUserOrder, ArchivedUserOrderDTO> {
    @Lazy
    private final ConversionService conversionService;

    @Override
    public ArchivedUserOrderDTO convert(ArchivedUserOrder archivedUserOrder) {
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
                        .stream().map(archivedUserOrderProduct -> conversionService.convert(archivedUserOrderProduct, ArchivedUserOrderProductDTO.class)).collect(Collectors.toSet()))
                .createDateTime(archivedUserOrder.getCreateDateTime())
                .build();
    }
}
