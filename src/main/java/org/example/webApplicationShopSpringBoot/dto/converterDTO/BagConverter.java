package org.example.webApplicationShopSpringBoot.dto.converterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductConverter.class})
public interface BagConverter {


    Bag toEntity(BagDTORequest bagDTORequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product", target = "productDTO")
    BagDTOResponse toDTO(Bag bag);

}
