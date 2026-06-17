package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterExceptions.AllEmptyFields;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterExceptions.EmptyField;

import java.util.Iterator;
import java.util.Set;

public class ValidatorDTO {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T dto) {
        if (dto == null) {
            throw new AllEmptyFields("Данные не переданы!");
        }
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuffer fullMessage = new StringBuffer();
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            while(iterator.hasNext()){
                String errorMessage = iterator.next().getMessage();
                fullMessage.append(errorMessage).append(", ");
            }
            throw new EmptyField(fullMessage.toString());
        }
    }
}
