package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BagEntityConverter implements Converter<BagDTOResponse, Bag> {
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Override
    public Bag convert(BagDTOResponse bagDTOResponse) {
        return Bag.builder().user(userRepository.findById(bagDTOResponse.getUserId()).get()).product(productRepository.findById(bagDTOResponse.getProductDTO().getId()).get())
                .count(bagDTOResponse.getCount())
                .build();
    }
}