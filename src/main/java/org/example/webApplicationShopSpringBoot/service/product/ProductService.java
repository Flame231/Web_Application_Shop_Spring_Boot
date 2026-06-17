package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;

import java.io.Serializable;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts(int currentPage);

    ProductDTO findProduct(Serializable id);

    void addProduct(ProductDTO productDTO);

    void updateProduct(ProductDTO productDTO);

    void removeProduct(Serializable id);

    Integer getProductCountResult();

    ProductPagesDivide getProductsAndList(Integer currentPage);
}
