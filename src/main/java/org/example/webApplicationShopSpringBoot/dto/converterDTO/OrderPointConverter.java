package org.example.webApplicationShopSpringBoot.dto.converterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderPointConverter {

    OrderPoint toEntity(OrderPointDTO orderPointDTO);

    OrderPointDTO toDTO(OrderPoint orderPoint);
}
