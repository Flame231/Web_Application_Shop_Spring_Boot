package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO findProduct(Long id);

    void addProduct(ProductDTO productDTO);

    void updateProduct(ProductDTO productDTO);

    void removeProduct(Long id);

}
