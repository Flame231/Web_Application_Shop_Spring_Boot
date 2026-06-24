package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dao.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductEntityConverter implements Converter<ProductDTO, Product> {
    private ProductCategoryRepository productCategoryRepository;
    private SellerRepository sellerRepository;

    @Override
    public Product convert(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productCategory(productCategoryRepository.getReferenceById(productDTO.getProductCategory().getId()))
                .price(productDTO.getPrice())
                .seller(sellerRepository.getReferenceById(productDTO.getSeller().getId()))
                .id(productDTO.getId())
                .updateDateTime(productDTO.getUpdateDateTime())
                .build();
    }
}
