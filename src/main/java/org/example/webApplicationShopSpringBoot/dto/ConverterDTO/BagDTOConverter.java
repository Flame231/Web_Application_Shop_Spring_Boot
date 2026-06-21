package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.stereotype.Component;

@Component
public class BagDTOConverter implements ConverterDTO<Bag, BagDTOResponse> {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ConverterDTO<Product, ProductDTO> converter;

    public BagDTOConverter(UserRepository userRepository, ProductRepository productRepository, ConverterDTO<Product, ProductDTO> converter) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.converter = converter;
    }

    @Override
    public Bag toEntity(BagDTOResponse bagDTOResponse) {

        return Bag.builder().user(userRepository.findById(bagDTOResponse.getUserId()).get()).product(productRepository.findById(bagDTOResponse.getProductDTO().getId()).get())
                .count(bagDTOResponse.getCount())
                .build();
    }

    @Override
    public BagDTOResponse toDTO(Bag bag) {
        return BagDTOResponse.builder().userId(bag.getUser().getId())
                .productDTO(converter.toDTO(bag.getProduct()))
                .count(bag.getCount()).build();
    }
}