package org.example.webApplicationShopSpringBoot.service.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dao.discount.DiscountRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.UserProfileConverter;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.UserRegistrationConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.service.exceptions.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;
    private UserProfileConverter userProfileConverter;
    private UserRegistrationConverter userRegistrationConverter;
    private DiscountRepository discountRepository;


    @Override
    public void saveNewUser(UserRegistrationDTO userRegistrationDTO) {
        passwordCheck(userRegistrationDTO.getNewPassword(), userRegistrationDTO.getNewPasswordRepeat());
        if (!userRepository.findByLogin(userRegistrationDTO.getLogin()).isEmpty()) {
            throw new UserRegistrationException("Пользователь с таким логином уже зарегистрирован!");
        }
        User user = userRegistrationConverter.toEntity(userRegistrationDTO);
        String passwordHash = BcryptUtil.hashPassword(userRegistrationDTO.getNewPassword());
        user.setPasswordHash(passwordHash);
        user.setSumOfPurchases(BigDecimal.ZERO);
        user.setRole(Role.CLIENT);
        user.setDiscount(discountRepository.findById(1L).orElseThrow(() -> new UserRegistrationException("Ошибка регистрации!")));
        userRepository.save(user);
    }

    private void passwordCheck(String newPassword, String newPasswordRepeat) {
        if (!newPassword.equals(newPasswordRepeat)) {
            {
                throw new DifferentUserPasswords("Введённые пароли не совпадают!");
            }
        }
    }

    @Override
    public void updateUser(UserProfileDTO userProfileDTO) {
        User user = PrincipalProvider.getUserFromSecurityContext();
        User userManaged = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFound("Пользователь не найден!"));

        String newPass = userProfileDTO.getNewPassword();
        String repeatPass = userProfileDTO.getNewPasswordRepeat();

        String oldPass = userProfileDTO.getOldPassword();

        boolean hasNewPass = newPass != null && !newPass.isBlank();
        boolean hasRepeatPass = repeatPass != null && !repeatPass.isBlank();
        boolean hasOldPass = oldPass != null && !oldPass.isBlank();
        userProfileConverter.updateUser(userProfileDTO, userManaged);

        if (hasNewPass && hasRepeatPass) { //если новые пароли заполнены

            if (hasOldPass) {

                if (!BcryptUtil.checkPassword(userProfileDTO.getOldPassword(), userManaged.getPasswordHash())) {
                    throw new WrongPassword("Неверный пароль!");
                }
                passwordCheck(userProfileDTO.getNewPassword(), userProfileDTO.getNewPasswordRepeat());
                userManaged.setPasswordHash(BcryptUtil.hashPassword(userProfileDTO.getNewPassword()));
            } else {
                throw new WrongPassword("Пароль не заполнен!");
            }


        } else if (hasNewPass || hasRepeatPass) {
            throw new DifferentUserPasswords("Не заполнены формы нового пароля");
        }
    }

    @Override
    public UserProfileDTO getUser() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        return userProfileConverter.toDTO(userRepository.findById(user.getId()).get());
    }
}
