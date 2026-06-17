package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements ConverterDTO<User, UserDTO> {
    @Override
    public UserDTO toDTO(User user) {
        if (user != null) {
            return UserDTO.builder().id(user.getId())
                    .name(user.getName())
                    .login(user.getLogin())
                    .birthday(user.getBirthday())
                    .paymentMethods(user.getPaymentMethods())
                    .sumOfPurchases(user.getSumOfPurchases())
                    .discount(user.getDiscount())
                    .role(user.getRole())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder().name(userDTO.getName())
                .id(userDTO.getId())
                .login((userDTO.getLogin()))
                .passwordHash((BcryptUtil.hashPassword(userDTO.getNewPassword())))
                .birthday((userDTO.getBirthday()))
                .paymentMethods((userDTO.getPaymentMethods()))
                .sumOfPurchases((userDTO.getSumOfPurchases()))
                .role(userDTO.getRole())
                .discount((userDTO.getDiscount())).build();
    }
}
