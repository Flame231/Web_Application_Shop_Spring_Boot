package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerDTOConverter implements ConverterDTO<Seller, SellerDTO> {

    @Override
    public SellerDTO toDTO(Seller seller) {
        return SellerDTO.builder().id(seller.getId()).sellerName(seller.getSellerName())
                .sellerAddress(seller.getSellerAddress()).build();
    }

    @Override
    public Seller toEntity(SellerDTO seller) {
        return Seller.builder().sellerName(seller.getSellerName()).sellerAddress(seller.getSellerAddress())
                .id(seller.getId())
                .build();
    }
}
