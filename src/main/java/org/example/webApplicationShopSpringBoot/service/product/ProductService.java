package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.EditProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ProductsAndBagsDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PageResponse;

public interface ProductService {

    PageResponse<ProductDTO> getActiveProducts(int page, int pageSize);

    PageResponse<ProductDTO> getAllProducts(int page, int pageSize);

    ProductDTO findProduct(Long id);

    void addProduct(NewProductDTO productDTO);

    void updateProduct(NewProductDTO newProductDTO);

    void removeProduct(Long id);

    void recoverProduct(Long id);

    EditProductDTO returnLists(Long productId);

    EditProductDTO returnLists();

    ProductsAndBagsDTO getProductsAndBags(int page, int pageSize, User user);

    Product getProduct(Long productId);

}
