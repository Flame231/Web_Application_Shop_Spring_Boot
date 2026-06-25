package org.example.webApplicationShopSpringBoot.service.productCategory;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface ProductCategoryService {

    PageResponse<ProductCategoryDTO> getProductCategoryDTOList(Pageable pageable);

    List<ProductCategoryDTO> getProductCategoryDTOList();

    void addProductCategory(ProductCategoryDTO productCategoryDTO);

    void updateProductCategory(ProductCategoryDTO productCategoryDTO);

    ProductCategoryDTO findProductCategory(Long id);

    void deleteProductCategory(Long id);
}
