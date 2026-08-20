package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;


@Mapper(componentModel = "spring")
public interface UserDiscountConverter {

    @Mapping(target = "discount", source = "discount.discount")
    UserDiscountDTO toDTO(User user);
}
