package org.example.webApplicationShopSpringBoot.service.user;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.math.BigDecimal;

public interface UserService {

    void saveNewUser(UserRegistrationDTO userRegistrationDTO);

    void updateUser(UserProfileDTO userProfileDTO, User user);

    UserProfileDTO getUserProfileDTO(User user);

    void increaseTotalSum(Long userId, BigDecimal finalOrderSum);

    OrderPointDTO getOrderPoint();

    UserDiscountDTO getUserDiscount();

}
