package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SellerDTOConverter implements Converter<Seller, SellerDTO> {
    @Override
    public SellerDTO convert(Seller seller) {
        return SellerDTO.builder().id(seller.getId()).sellerName(seller.getSellerName())
                .sellerAddress(seller.getSellerAddress()).build();
    }
}
