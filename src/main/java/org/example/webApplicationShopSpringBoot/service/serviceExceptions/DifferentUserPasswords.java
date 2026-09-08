package org.example.webApplicationShopSpringBoot.service.serviceExceptions;

public class DifferentUserPasswords extends RuntimeException {
    public DifferentUserPasswords(String message) {
        super(message);
    }
}
