package org.example.webApplicationShopSpringBoot.service.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserDiscountConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserProfileConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserRegistrationConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.discount.DiscountRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.DifferentUserPasswords;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.UserRegistrationException;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.WrongPassword;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final UserProfileConverter userProfileConverter;
    private final UserRegistrationConverter userRegistrationConverter;
    private final OrderPointConverter orderPointConverter;
    private final UserDiscountConverter userDiscountConverter;

    @Override
    public void saveNewUser(UserRegistrationDTO userRegistrationDTO) {
        passwordCheck(userRegistrationDTO.getNewPassword(), userRegistrationDTO.getNewPasswordRepeat());
        String userLogin = userRegistrationDTO.getLogin();
        if (userRepository.findByLogin(userLogin).isPresent()) {
            throw new UserRegistrationException("Пользователь с логином \"%s\" уже зарегистрирован!", userLogin);
        }
        User user = userRegistrationConverter.toEntity(userRegistrationDTO);
        String passwordHash = BcryptUtil.hashPassword(userRegistrationDTO.getNewPassword());
        user.setPasswordHash(passwordHash);
        user.setSumOfPurchases(BigDecimal.ZERO);
        user.setRole(Role.CLIENT);
        Long defaultDiscountId = 1L;
        user.setDiscount(discountRepository.findById(defaultDiscountId).orElseThrow(() -> new UserRegistrationException("Ошибка регистрации!")));
        userRepository.save(user);
        log.info("Пользователь с логином {} успешно зарегистрирован!", user.getLogin());
    }

    private void passwordCheck(String newPassword, String newPasswordRepeat) {
        if (!newPassword.equals(newPasswordRepeat)) {
            {
                throw new DifferentUserPasswords("Введённые пароли не совпадают!");
            }
        }
    }

    @Override
    public void updateUser(UserProfileDTO userProfileDTO, User user) {
        User userManaged = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFound("Пользователь не найден!"));
        String newPass = userProfileDTO.getNewPassword();
        String repeatPass = userProfileDTO.getNewPasswordRepeat();
        String oldPass = userProfileDTO.getOldPassword();
        boolean hasNewPass = newPass != null && !newPass.isBlank();
        boolean hasRepeatPass = repeatPass != null && !repeatPass.isBlank();
        boolean hasOldPass = oldPass != null && !oldPass.isBlank();
        userProfileConverter.updateUser(userProfileDTO, userManaged);
        if (hasNewPass && hasRepeatPass) {
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
        log.info("Пользователь с id {} успешно обновлён!", user.getId());
    }

    @Override
    public UserProfileDTO getUserProfileDTO(User user) {
        return userProfileConverter.toDTO(getUser(user.getId()));
    }

    @Override
    public void increaseTotalSum(Long userId, BigDecimal finalOrderSum) {
        User user = getUser(userId);
        BigDecimal userTotalSum = user.getSumOfPurchases();
        BigDecimal newUserTotalSum = userTotalSum.add(finalOrderSum);
        user.setSumOfPurchases(newUserTotalSum);
        log.info("Сумма пользователя с id {} успешно увеличена до {}", user.getId(), newUserTotalSum);
    }

    @Override
    public OrderPointDTO getOrderPoint(User user) {
        return orderPointConverter.toDTO(user.getOrderPoint());
    }

    @Override
    public UserDiscountDTO getUserDiscount(User user) {
        return userDiscountConverter.toDTO(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("Пользователь не найден!"));
    }
}
