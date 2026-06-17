package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.model.Seller;

public class ProductDTOConverter implements ConverterDTO<Product, ProductDTO> {
    private ConverterDTO<ProductCategory, ProductCategoryDTO> productCategoryConverterDTO = new ProductCategoryDTOConverter();
    private ConverterDTO<Seller, SellerDTO> sellerConverterDTO = new SellerDTOConverter();

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productCategory(productCategoryConverterDTO.toEntity(productDTO.getProductCategory()))
                .price(productDTO.getPrice())
                .seller(sellerConverterDTO.toEntity(productDTO.getSeller()))
                .id(productDTO.getId())
                .updateDateTime(productDTO.getUpdateDateTime())
                .build();
    }

    @Override
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productCategory(productCategoryConverterDTO.toDTO(product.getProductCategory()))
                .price(product.getPrice())
                .seller(sellerConverterDTO.toDTO(product.getSeller()))
                .updateDateTime(product.getUpdateDateTime())
                .build();
    }
}
