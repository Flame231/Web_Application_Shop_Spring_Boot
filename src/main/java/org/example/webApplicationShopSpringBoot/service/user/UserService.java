package org.example.webApplicationShopSpringBoot.service.user;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;

import java.math.BigDecimal;

public interface UserService {

    void saveNewUser(UserRegistrationDTO userRegistrationDTO);

    void updateUser(UserProfileDTO userProfileDTO);

    UserProfileDTO getUserProfileDTO();

    void increaseTotalSum(Long userId, BigDecimal finalOrderSum);

    OrderPointDTO getOrderPoint();

    UserDiscountDTO getUserDiscount();

}
