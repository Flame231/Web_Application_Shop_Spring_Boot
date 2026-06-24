package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BagRequestDTOConverter implements Converter<BagDTORequest, Bag> {
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Override
    public Bag convert(BagDTORequest bagDTORequest) {
        return Bag.builder()
                .user(userRepository.findById(bagDTORequest.getUserId()).get())
                .product(productRepository.findById(bagDTORequest.getProductId()).get())
                .count(bagDTORequest.getCount())
                .build();
    }
}
