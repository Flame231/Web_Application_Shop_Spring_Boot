package org.example.webApplicationShopSpringBoot.service.serviceExceptions;

public class WrongPassword extends RuntimeException {
    public WrongPassword(String message) {
        super(message);
    }
}
