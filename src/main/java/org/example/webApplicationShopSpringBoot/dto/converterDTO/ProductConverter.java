package org.example.webApplicationShopSpringBoot.dto.converterDTO;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.example.webApplicationShopSpringBoot.repository.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.repository.seller.SellerRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductConverter {

    @Autowired
    protected SellerRepository sellerRepository;

    @Autowired
    protected ProductCategoryRepository productCategoryRepository;

    @Mapping(target = "productCategory", source = "productCategory")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "status", defaultExpression =
            "java(org.example.webApplicationShopSpringBoot.model.ItemStatus.ACTIVE)")
    public abstract Product toEntity(NewProductDTO newProductDTO);

    public abstract ProductDTO toDTO(Product product);

    @Mapping(target = "productCategory", source = "productCategory")
    @Mapping(target = "seller", source = "seller")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Product updateEntity(NewProductDTO newProductDTO, @MappingTarget Product product);

    protected Seller mapLongToSeller(Long sellerId) {
        if (sellerId == null) {
            return null;
        }
        return sellerRepository.getReferenceById(sellerId);
    }

    protected ProductCategory mapLongToCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return productCategoryRepository.getReferenceById(categoryId);
    }
}
