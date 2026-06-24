package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
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
}
