package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;

import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagConverter {

    Bag toEntity(BagDTORequest bagDTORequest);
    BagDTOResponse toDTO(Bag bag);

}
