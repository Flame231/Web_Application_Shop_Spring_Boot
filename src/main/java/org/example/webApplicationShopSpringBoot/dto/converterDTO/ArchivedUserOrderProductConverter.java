package org.example.webApplicationShopSpringBoot.dto.converterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArchivedUserOrderProductConverter {

    ArchivedUserOrderProductDTO toDTO(ArchivedUserOrderProduct archivedUserOrderProduct);
    ArchivedUserOrderProduct toEntity(ArchivedUserOrderProductDTO archivedUserOrderProductDTO);

}
