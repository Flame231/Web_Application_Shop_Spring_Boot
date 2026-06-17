package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterExceptions;

public class AllEmptyFields extends RuntimeException {
    public AllEmptyFields(String message) {
        super(message);
    }
}
