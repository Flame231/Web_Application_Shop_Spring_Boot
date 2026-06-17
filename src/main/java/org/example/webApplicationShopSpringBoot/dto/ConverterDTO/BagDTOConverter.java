package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dao.user.UserDAOImpl;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;

public class BagDTOConverter implements ConverterDTO<Bag, BagDTO> {
    private ConverterDTO<Product, ProductDTO> converter = new ProductDTOConverter();

    @Override
    public Bag toEntity(BagDTO bagDTO) {
        UserRepository userDAO = new UserDAOImpl();
        return Bag.builder().user(userDAO.get(bagDTO.getUserId())).product(converter.toEntity(bagDTO.getProduct()))
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