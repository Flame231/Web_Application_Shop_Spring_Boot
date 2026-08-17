package org.example.webApplicationShopSpringBoot.service.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.UserConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.service.exceptions.DifferentPasswordsRegistration;
import org.example.webApplicationShopSpringBoot.service.exceptions.DifferentPasswordsUpdate;
import org.example.webApplicationShopSpringBoot.service.exceptions.UserRegistrationException;
import org.example.webApplicationShopSpringBoot.service.exceptions.WrongPassword;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;
    private UserConverter userConverter;

    @Override
    public void saveOrUpdateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            try {
                passwordValidation(userDTO);
                userDTO.setRole(Role.CLIENT);
                userRepository.saveAndFlush(userConverter.toEntity(userDTO));
                logger.info("Пользователь {} успешно зарегистрирован!", userDTO.getLogin());
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                logger.error("Ошибка регистрации пользователя {}", userDTO.getLogin(), e);
                throw new UserRegistrationException("Ошибка регистрации пользователя: пользователь с таким логином уже зарегистрирован");
            } catch (Exception e) {
                logger.error("Ошибка регистрации пользователя", e);
                throw new UserRegistrationException("Ошибка регистрации пользователя: " + e.getMessage());
            }
        } else updateUser(userDTO);
    }

    public UserDTO getUserDTO(Long id) {
        return userConverter.toDTO(userRepository.findById(id).get());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = PrincipalProvider.getUserFromSecurityContext();
        passwordValidation(userDTO);
        User userManaged = userRepository.findById(user.getId()).get();
        userManaged.setName(userDTO.getName());
        userManaged.setLogin(userDTO.getLogin());
        userManaged.setPasswordHash(BcryptUtil.hashPassword(userDTO.getNewPassword()));
        userManaged.setBirthday(userDTO.getBirthday());
        userManaged.setPaymentMethods(userDTO.getPaymentMethods());
    }

    @Override
    public void passwordValidation(UserDTO userDTO) {
        if (userDTO.getId() != null) {
            User user = userRepository.findById(userDTO.getId()).get();
            if (BcryptUtil.checkPassword(userDTO.getOldPassword(), user.getPasswordHash())) {
                if (!userDTO.getNewPassword().equals(userDTO.getNewPasswordRepeat())) {
                    throw new DifferentPasswordsUpdate("Введенные пароли не совпадают!");
                }
            } else {
                throw new WrongPassword("Неверный пароль!");
            }
        } else {
            if (!userDTO.getNewPassword().equals(userDTO.getNewPasswordRepeat())) {
                throw new DifferentPasswordsRegistration("Введенные пароли не совпадают!");
            }
        }
    }

    @Override
    public UserDTO getUser() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        return userConverter.toDTO(userRepository.findById(user.getId()).get());
    }
}
