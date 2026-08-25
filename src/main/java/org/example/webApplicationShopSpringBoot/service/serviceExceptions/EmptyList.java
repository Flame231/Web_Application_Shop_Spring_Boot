package org.example.webApplicationShopSpringBoot.service.serviceExceptions;

public class EmptyList extends RuntimeException {
    public EmptyList(String message) {
        super(message);
    }
}
