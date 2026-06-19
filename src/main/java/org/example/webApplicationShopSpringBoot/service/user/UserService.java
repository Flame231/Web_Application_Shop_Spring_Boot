package org.example.webApplicationShopSpringBoot.service.user;


import org.example.webApplicationShopSpringBoot.dto.dto.LoginDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.io.Serializable;

public interface UserService {

    void saveOrUpdateUser(UserDTO userDTO);

    /*UserDTO authorizeUser(LoginDTO loginDTO);*/

    UserDTO getUserDTO(Long id);

    void updateUser(UserDTO userDTO);

    void passwordValidation(UserDTO userDTO);

    User getUser(Long id);


}
