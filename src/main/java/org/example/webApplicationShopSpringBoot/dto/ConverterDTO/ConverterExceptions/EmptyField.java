package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterExceptions;

public class EmptyField extends RuntimeException {
    public EmptyField(String message) {
        super(message);
    }
}
