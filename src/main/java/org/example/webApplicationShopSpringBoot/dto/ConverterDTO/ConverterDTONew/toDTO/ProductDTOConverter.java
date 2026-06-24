package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductDTOConverter implements Converter<Product, ProductDTO> {
    @Lazy
    private ConversionService conversionService;

    @Override
    public ProductDTO convert(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productCategory(conversionService.convert(product.getProductCategory(), ProductCategoryDTO.class))
                .price(product.getPrice())
                .seller(conversionService.convert(product.getSeller(), SellerDTO.class))
                .updateDateTime(product.getUpdateDateTime())
                .build();
    }
}
