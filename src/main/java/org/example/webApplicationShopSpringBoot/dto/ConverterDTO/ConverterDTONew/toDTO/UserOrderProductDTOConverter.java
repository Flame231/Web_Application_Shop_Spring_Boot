package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserOrderProductDTOConverter implements Converter<UserOrderProduct, UserOrderProductDTO> {
    @Lazy
    private ConversionService conversionService;

    @Override
    public UserOrderProductDTO convert(UserOrderProduct userOrderProduct) {
        return UserOrderProductDTO.builder()
                .userOrderId(userOrderProduct.getUserOrder().getId())
                .productDTO(conversionService.convert(userOrderProduct.getProduct(), ProductDTO.class))
                .productCount(userOrderProduct.getProductCount())
                .actualProductCount(userOrderProduct.getActualProductCount())
                .build();
    }
}
