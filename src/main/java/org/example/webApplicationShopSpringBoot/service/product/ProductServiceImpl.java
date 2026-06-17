package org.example.webApplicationShopSpringBoot.service.product;


import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ProductDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productDAO;

    public ProductServiceImpl(ProductRepository productDAO) {
        this.productDAO = productDAO;
    }

    private ConverterDTO<Product, ProductDTO> converterDTO = new ProductDTOConverter();

    @Override
    public List<ProductDTO> getAllProducts(int currentPage) {
        return productDAO.getProductList(currentPage).stream()
                .map(converterDTO::toDTO)
                .toList();
    }

    public ProductDTO findProduct(Serializable id) {
        return converterDTO.toDTO(productDAO.get(id));
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = converterDTO.toEntity(productDTO);
        productDAO.save(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = converterDTO.toEntity(productDTO);
        productDAO.save(product);
    }

    @Override
    public void removeProduct(Serializable id) {
        productDAO.delete(id);
    }

    @Override
    public Integer getProductCountResult() {
        return productDAO.getProductsCount();
    }

    @Override
    public ProductPagesDivide getProductsAndList(Integer currentPage) {
        List<ProductDTO> productList = null;
        if (currentPage != null) {
        } else {
            currentPage = 1;
        }
        productList = this.getAllProducts(currentPage);
        int productCountResult = this.getProductCountResult();
        return new ProductPagesDivide(productList, productCountResult, currentPage);
    }
}
