package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.stereotype.Component;

@Component
public class BagDTOConverter implements ConverterDTO<Bag, BagDTO> {
    UserRepository userRepository;
    private ConverterDTO<Product, ProductDTO> converter;

    public BagDTOConverter(UserRepository userRepository, ConverterDTO<Product, ProductDTO> converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public Bag toEntity(BagDTO bagDTO) {

        return Bag.builder().user(userRepository.findById(bagDTO.getUserId()).get()).product(converter.toEntity(bagDTO.getProduct()))
                .count(bagDTO.getCount())
                .build();
    }

    @Override
    public BagDTO toDTO(Bag bag) {
        return BagDTO.builder().userId(bag.getUser().getId())
                .product(converter.toDTO(bag.getProduct()))
                .count(bag.getCount()).build();
    }
}