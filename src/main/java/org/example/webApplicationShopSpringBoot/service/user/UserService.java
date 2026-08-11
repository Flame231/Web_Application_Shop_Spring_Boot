package org.example.webApplicationShopSpringBoot.service.user;


import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;

public interface UserService {

    void saveNewUser(UserRegistrationDTO userRegistrationDTO);

    void updateUser(UserProfileDTO userProfileDTO);

    UserProfileDTO getUser();

}
