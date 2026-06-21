package org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;


import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ConverterDTONew;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.stereotype.Component;

@Component
public class BagDTOConverterNew implements ConverterDTONew<Bag, BagDTORequest, BagDTOResponse> {

    private ConverterDTO<Product, ProductDTO> converterDTO;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public BagDTOConverterNew(ConverterDTO<Product, ProductDTO> converterDTO, UserRepository userRepository, ProductRepository productRepository) {
        this.converterDTO = converterDTO;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Bag toEntity(BagDTORequest bagDTORequest) {
        return Bag.builder()
                .user(userRepository.findById(bagDTORequest.getUserId()).get())
                .product(productRepository.findById(bagDTORequest.getProductId()).get())
                .count(bagDTORequest.getCount())
                .build();
    }

    @Override
    public BagDTOResponse toDTO(Bag bag) {
        return BagDTOResponse.builder()
                .userId(bag.getUser().getId())
                .productDTO(converterDTO.toDTO(bag.getProduct()))
                .count(bag.getCount()).build();
    }
}
