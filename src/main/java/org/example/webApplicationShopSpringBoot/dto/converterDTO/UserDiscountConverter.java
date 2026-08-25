package org.example.webApplicationShopSpringBoot.dto.converterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserDiscountConverter {

    @Mapping(target = "discount", source = "discount.discount")
    UserDiscountDTO toDTO(User user);
}
