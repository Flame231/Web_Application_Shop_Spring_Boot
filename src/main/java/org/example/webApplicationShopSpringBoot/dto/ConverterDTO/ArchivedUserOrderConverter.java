package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArchivedUserOrderConverter {

    ArchivedUserOrder toEntity(ArchivedUserOrderDTO archivedUserOrderDTO);
    ArchivedUserOrderDTO toDTO(ArchivedUserOrder archivedUserOrder);

}
