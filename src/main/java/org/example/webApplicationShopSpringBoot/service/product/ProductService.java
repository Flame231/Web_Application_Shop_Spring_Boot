package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    PageResponse<ProductDTO> getActiveProducts(Pageable pageable);

    PageResponse<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO findProduct(Long id);

    void addProduct(NewProductDTO productDTO);

    void updateProduct(NewProductDTO newProductDTO);

    void removeProduct(Long id);

    void recoverProduct(Long id);

}
