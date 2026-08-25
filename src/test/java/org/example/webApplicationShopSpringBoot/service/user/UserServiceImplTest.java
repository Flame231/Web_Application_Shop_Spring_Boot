package org.example.webApplicationShopSpringBoot.service.user;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserDiscountConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserProfileConverter;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserRegistrationConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.discount.DiscountRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.service.BcryptUtil;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.DifferentUserPasswords;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.UserRegistrationException;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.WrongPassword;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final String USER_IS_NOT_FOUND = "Пользователь не найден!";
    private final String NEW_PASSWORDS_ARE_EMPTY = "Не заполнены формы нового пароля";
    private final String WRONG_PASSWORD = "Неверный пароль!";
    private final String PASSWORD_IS_EMPTY = "Пароль не заполнен!";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserProfileConverter userProfileConverter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderPointConverter orderPointConverter;

    @Mock
    private UserDiscountConverter userDiscountConverter;

    @Mock
    private UserRegistrationConverter userRegistrationConverter;

    @Mock
    private DiscountRepository discountRepository;

    @Test
    void saveNewUser() {
        Long discountId = 1L;
        Discount discount = Discount.builder().discount(3).totalSum(new BigDecimal("2200000.00")).id(discountId).build();
        User user = new User();
        user.setName("Alex123");
        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder().name("Alex")
                .login("Alex123").newPassword("123q!").newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        when(userRepository.findByLogin(userRegistrationDTO.getLogin())).thenReturn(Optional.empty());
        when(userRegistrationConverter.toEntity(userRegistrationDTO)).thenReturn(user);
        when(discountRepository.findById(discountId)).thenReturn(Optional.of(discount));
        userService.saveNewUser(userRegistrationDTO);
        verify(userRepository, times(1)).findByLogin(userRegistrationDTO.getLogin());
        verify(userRegistrationConverter, times(1)).toEntity(userRegistrationDTO);
        verify(discountRepository, times(1)).findById(discountId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveNewUserThrowUserRegEx() {
        Long discountId = 1L;
        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder().name("Alex")
                .login("Alex123").newPassword("123q!").newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        when(userRepository.findByLogin(userRegistrationDTO.getLogin())).thenReturn(Optional.empty());
        when(userRegistrationConverter.toEntity(userRegistrationDTO)).thenReturn(new User());
        when(discountRepository.findById(discountId)).thenReturn(Optional.empty());
        assertThrows(UserRegistrationException.class, () -> userService.saveNewUser(userRegistrationDTO));
        verify(userRepository, times(1)).findByLogin(userRegistrationDTO.getLogin());
        verify(userRegistrationConverter, times(1)).toEntity(userRegistrationDTO);
        verify(discountRepository, times(1)).findById(discountId);
    }

    @Test
    void saveNewUserThrowUserExists() {
        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder().name("Alex")
                .login("Alex123").newPassword("123q!").newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        when(userRepository.findByLogin(userRegistrationDTO.getLogin())).thenReturn(Optional.of(new User()));
        assertThrows(UserRegistrationException.class, () -> userService.saveNewUser(userRegistrationDTO));
        verify(userRepository, times(1)).findByLogin(userRegistrationDTO.getLogin());
    }

    @Test
    void updateUserThrowsResourceNotFound() {
        Long id = 5L;
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        User user = new User();
        user.setId(id);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        ResourceNotFound resourceNotFoundException = assertThrows(ResourceNotFound.class, () -> userService.updateUser(userProfileDTO, user));
        assertEquals(USER_IS_NOT_FOUND, resourceNotFoundException.getMessage());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void updateUserThrowsDifferentPasswords() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder().name("Alex").login("Alex123").oldPassword("321q!").newPassword("")
                .newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        Long id = 5L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        DifferentUserPasswords differentUserPasswordsException = assertThrows(DifferentUserPasswords.class, () -> userService.updateUser(userProfileDTO, user));
        assertEquals(NEW_PASSWORDS_ARE_EMPTY, differentUserPasswordsException.getMessage());
        verify(userProfileConverter, times(1)).updateUser(userProfileDTO, user);
    }

    @Test
    void updateUserThrowsWrongPassword() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder().name("Alex").login("Alex123").oldPassword("321q!").newPassword("123q!")
                .newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        Long id = 5L;
        User user = new User();
        user.setId(id);
        user.setPasswordHash("$2a$12$.mnFPs7pc6C7AisJgr6JWu2hUsl1xDQKNSeLQ8cuRVblNOzt8n4cm");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        WrongPassword wrongPasswordException = assertThrows(WrongPassword.class, () -> userService.updateUser(userProfileDTO, user));
        assertEquals(WRONG_PASSWORD, wrongPasswordException.getMessage());
        verify(userProfileConverter, times(1)).updateUser(userProfileDTO, user);
    }

    @Test
    void updateUserThrowsEmptyPassword() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder().name("Alex").login("Alex123").oldPassword("").newPassword("123q!")
                .newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        Long id = 5L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        WrongPassword wrongPasswordException = assertThrows(WrongPassword.class, () -> userService.updateUser(userProfileDTO, user));
        assertEquals(PASSWORD_IS_EMPTY, wrongPasswordException.getMessage());
        verify(userProfileConverter, times(1)).updateUser(userProfileDTO, user);
    }

    @Test
    void updateUser() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder().name("Alex").login("Alex123").oldPassword("321q!").newPassword("123q!")
                .newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        Long id = 5L;
        User user = new User();
        user.setId(id);
        User userManaged = new User();
        userManaged.setPasswordHash("$2a$12$oN2wPiHh4a9SnQ.GiEZz9O/TcCSGVRcYTuDR9jvYQFypHdXw87D1K");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(userManaged));
        userService.updateUser(userProfileDTO, user);
        assertTrue(BcryptUtil.checkPassword(userProfileDTO.getNewPassword(), userManaged.getPasswordHash()));
        verify(userProfileConverter, times(1)).updateUser(userProfileDTO, userManaged);
    }


    @Test
    void getUserProfileDTO() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder().id(2L).name("Alex")
                .login("Alex123").newPassword("123q!").newPasswordRepeat("123q!").birthday(LocalDate.of(2026, 7, 13)).paymentMethods(null).build();
        User user = new User();
        user.setId(2L);
        when(userProfileConverter.toDTO(user)).thenReturn(userProfileDTO);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserProfileDTO returnedUserProfileDTO = userService.getUserProfileDTO(user);
        verify(userProfileConverter, times(1)).toDTO(user);
        verify(userRepository, times(1)).findById(user.getId());
        assertNotNull(returnedUserProfileDTO);
    }

    @Test
    void getUserProfileDTOThrow() {
        User user = new User();
        user.setId(2L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        ResourceNotFound resourceNotFoundException = assertThrows(ResourceNotFound.class, () -> userService.getUserProfileDTO(user));
        assertEquals(USER_IS_NOT_FOUND, resourceNotFoundException.getMessage());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void increaseTotalSum() {
        BigDecimal userTotalSum = new BigDecimal("22353.23");
        BigDecimal finalOrderSum = new BigDecimal("2473.41");
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        user.setSumOfPurchases(userTotalSum);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.increaseTotalSum(userId, finalOrderSum);
        verify(userRepository, times(1)).findById(user.getId());
        assertEquals(new BigDecimal("24826.64"), user.getSumOfPurchases());
    }

    @Test
    void getOrderPoint() {
        OrderPoint orderPoint = new OrderPoint();
        User user = new User();
        user.setOrderPoint(orderPoint);
        when(orderPointConverter.toDTO(orderPoint)).thenReturn(new OrderPointDTO());
        OrderPointDTO returnedOrderPoint = userService.getOrderPoint(user);
        verify(orderPointConverter, times(1)).toDTO(orderPoint);
        assertNotNull(returnedOrderPoint);
    }

    @Test
    void getUserDiscount() {
        User user = new User();
        when(userDiscountConverter.toDTO(user)).thenReturn(new UserDiscountDTO());
        UserDiscountDTO userDiscountDTO = userService.getUserDiscount(user);
        verify(userDiscountConverter, times(1)).toDTO(user);
        assertNotNull(userDiscountDTO);
    }
}