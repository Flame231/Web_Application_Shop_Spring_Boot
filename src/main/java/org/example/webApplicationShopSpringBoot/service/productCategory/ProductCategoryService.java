package org.example.webApplicationShopSpringBoot.service.productCategory;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;

import java.io.Serializable;
import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryDTO> getProductCategoryDTOList();

    ProductCategory findProductCategory(Long id);

    void addProductCategory(ProductCategoryDTO productCategoryDTO);

    void updateProductCategory(ProductCategoryDTO productCategoryDTO);

    void deleteProductCategory(Long id);
}
