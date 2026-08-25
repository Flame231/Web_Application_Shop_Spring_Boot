package org.example.webApplicationShopSpringBoot.dto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
