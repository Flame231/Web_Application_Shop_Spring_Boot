package org.example.webApplicationShopSpringBoot.service.exceptions;

public class DifferentUserPasswords extends RuntimeException {
    public DifferentUserPasswords(String message) {
        super(message);
    }
}
