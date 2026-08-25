package org.example.webApplicationShopSpringBoot.service.serviceExceptions;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
