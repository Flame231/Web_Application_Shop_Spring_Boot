package org.example.webApplicationShopSpringBoot.service.user;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ValidatorDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.example.webApplicationShopSpringBoot.service.exceptions.*;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);


    private UserRepository userRepository;
    private ConverterDTO<User, UserDTO> converterDTO;


    public UserServiceImpl(UserRepository userRepository, ConverterDTO<User, UserDTO> converterDTO) {
        this.userRepository = userRepository;
        this.converterDTO = converterDTO;
    }

    @Override
    public void saveOrUpdateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            try {
                ValidatorDTO.validate(userDTO);
                passwordValidation(userDTO);
                userDTO.setRole(Role.CLIENT);
                userRepository.save(converterDTO.toEntity(userDTO));
                logger.info("Пользователь {} успешно зарегистрирован!", userDTO.getLogin());
            } catch (PersistenceException e) {
                logger.error("Ошибка регистрации пользователя {}", userDTO.getLogin(), e);
                throw new UserRegistrationException("Ошибка регистрации пользователя: пользователь с таким логином уже зарегистрирован");
            } catch (Exception e) {
                logger.error("Ошибка регистрации пользователя", e);
                throw new UserRegistrationException("Ошибка регистрации пользователя: " + e.getMessage());
            }
        } else updateUser(userDTO);
    }

/*    @Override
    public UserDTO authorizeUser(LoginDTO loginDTO) {
        User user = null;
        try {
            user = userDAO.findUser(loginDTO.getLogin());
        } catch (NoResultException e) {
            logger.info("Ошибка авторизации пользователя: логин не найден", e);
            throw new WrongLoginOrPassword("Неверный логин или пароль!");
        }
        if (!BcryptUtil.checkPassword(loginDTO.getPassword(), user.getPasswordHash())) {
            String userName = user.getName();
            logger.info("Ошибка авторизации пользователя {}: введён неверный логин или пароль", userName);
            throw new WrongLoginOrPassword("Неверный логин или пароль!");
        }
        logger.info("Успешная авторизация пользователя {}", loginDTO.getLogin());
        return converterDTO.toDTO(user);
    }*/

    public UserDTO getUserDTO(Long id) {
        return converterDTO.toDTO(userRepository.findById(id).get());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        passwordValidation(userDTO);
        User user = userRepository.findById(userDTO.getId()).get();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setPasswordHash(BcryptUtil.hashPassword(userDTO.getNewPassword()));
        user.setBirthday(userDTO.getBirthday());
        user.setPaymentMethods(userDTO.getPaymentMethods());
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
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }
}
