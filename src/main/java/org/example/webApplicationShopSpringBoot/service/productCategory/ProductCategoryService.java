package org.example.webApplicationShopSpringBoot.service.productCategory;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;

import java.util.List;

public interface ProductCategoryService {

    PageResponse<ProductCategoryDTO> getProductCategoryDTOList(int page, int pageSize);

    List<ProductCategoryDTO> getActiveProductCategoryDTOList();

    void addProductCategory(ProductCategoryDTO productCategoryDTO);

    void updateProductCategory(ProductCategoryDTO productCategoryDTO);

    ProductCategoryDTO findProductCategory(Long id);

    void deleteProductCategory(Long id);

    void recoverProductCategory(Long id);
}
