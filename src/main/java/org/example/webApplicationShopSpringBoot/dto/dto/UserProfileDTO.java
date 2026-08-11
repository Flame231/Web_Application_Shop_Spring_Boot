package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class UserProfileDTO {

    private Long id;

    @NotBlank(message = "имя не может быть пустым")
    private String name;

    @NotBlank(message = "логин не может быть пустым")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "логин может содержать только буквы, цифры и спец. символы")
    private String login;

    private String oldPassword;

    @Pattern(regexp = "^$|^(?=.*[!@#$%^&*<>|/~])[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "новый пароль должен содержать минимум один спец. символ (!@#$%^&*<>|/~)!")
    private String newPassword;

    @Pattern(regexp = "^$|^(?=.*[!@#$%^&*<>|/~])[a-zA-Z0-9а-яА-Я!@#$%^&*<>|/~]+$",
            message = "новый пароль должен содержать минимум один спец. символ (!@#$%^&*<>|/~)!")
    private String newPasswordRepeat;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    private String paymentMethods;
}
