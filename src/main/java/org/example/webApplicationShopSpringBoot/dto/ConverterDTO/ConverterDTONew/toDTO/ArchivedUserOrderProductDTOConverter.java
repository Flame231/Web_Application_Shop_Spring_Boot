package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ArchivedUserOrderProductDTOConverter {

    ArchivedUserOrderProductDTO toDTO(ArchivedUserOrderProduct archivedUserOrderProduct);
    ArchivedUserOrderProduct toEntity(ArchivedUserOrderProductDTO archivedUserOrderProductDTO);

}
