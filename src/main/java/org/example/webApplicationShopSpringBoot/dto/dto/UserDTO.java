package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank(message = "имя не может быть пустым")
    private String name;

    @NotBlank(message = "логин не может быть пустым")
    @Pattern(regexp = "^(?=.*[a-zA-Zа-яА-Я])(?=.*[0-9])[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "логин должен содержать только буквы латинского алфавита,цифры/спец. символы")
    private String login;

    @NotBlank(message = "пароль не может быть пустым")
    private String oldPassword;

    @NotBlank(message = "новый пароль не может быть пустым")
  /*  @Pattern(regexp = "^(?=.*[!@#$%^&*<>|/~])[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "новый пароль должен содержать только буквы латинского алфавита/цифры/спец. символы")*/
    private String newPassword;

    @NotBlank(message = "новый пароль не может быть пустым")
/*    @Pattern(regexp = "^(?=.*[!@#$%^&*<>|/~])[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "новый пароль должен содержать только буквы латинского алфавита/цифры/спец. символы")*/
    private String newPasswordRepeat;

    @NotNull(message = "не указана дата рождения")
    private LocalDate birthday;

    @NotBlank(message = "не указан способ оплаты")
    private String paymentMethods;

    private BigDecimal sumOfPurchases;

    private Discount discount;

    private Role role;

}
