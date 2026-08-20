package org.example.webApplicationShopSpringBoot.service.exceptions;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message, String variable) {
        super(String.format(message, variable));
    }

    public UserRegistrationException(String message) {
        super(message);
    }

    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
