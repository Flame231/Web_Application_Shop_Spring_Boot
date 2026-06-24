package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SellerEntityConverter implements Converter<SellerDTO, Seller> {

    @Override
    public Seller convert(SellerDTO seller) {
        return Seller.builder().sellerName(seller.getSellerName()).sellerAddress(seller.getSellerAddress())
                .id(seller.getId())
                .build();
    }
}
