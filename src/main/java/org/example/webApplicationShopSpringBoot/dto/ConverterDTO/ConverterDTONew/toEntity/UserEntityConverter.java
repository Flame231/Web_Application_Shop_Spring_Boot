package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConverter implements Converter<UserDTO,User> {

    @Override
    public User convert(UserDTO userDTO) {
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
