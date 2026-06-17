package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.springframework.stereotype.Component;

@Component
public class UserOrderProductDTOConverter implements ConverterDTO<UserOrderProduct, UserOrderProductDTO> {
    private ConverterDTO<Product, ProductDTO> converterDTO;

    public UserOrderProductDTOConverter(ConverterDTO<Product, ProductDTO> converterDTO) {
        this.converterDTO = converterDTO;
    }

    @Override
    public UserOrderProduct toEntity(UserOrderProductDTO userOrderProductDTO) {
        return null;
    }

    @Override
    public UserOrderProductDTO toDTO(UserOrderProduct userOrderProduct) {
        return UserOrderProductDTO.builder()
                .userOrderId(userOrderProduct.getUserOrder().getId())
                .productDTO(converterDTO.toDTO(userOrderProduct.getProduct()))
                .productCount(userOrderProduct.getProductCount())
                .actualProductCount(userOrderProduct.getActualProductCount())
                .build();
    }
}
