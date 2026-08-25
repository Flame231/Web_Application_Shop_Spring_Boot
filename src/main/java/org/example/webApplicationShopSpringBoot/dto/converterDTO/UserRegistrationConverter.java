package org.example.webApplicationShopSpringBoot.dto.converterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.mapstruct.Mapper;

import java.math.BigDecimal;


@Mapper(componentModel = "spring", imports = {BigDecimal.class, Discount.class, Role.class})
public interface UserRegistrationConverter {

    User toEntity(UserRegistrationDTO userRegistrationDTO);
}
