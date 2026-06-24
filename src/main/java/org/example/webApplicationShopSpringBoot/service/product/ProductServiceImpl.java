package org.example.webApplicationShopSpringBoot.service.product;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {
    private ConversionService conversionService;
    private ProductRepository productRepository;

    @Override
    public PageResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return new PageResponse<ProductDTO>(page.map(product -> conversionService.convert(product,ProductDTO.class)));
    }

    public ProductDTO findProduct(Long id) {

        return conversionService.convert(productRepository.findById(id).get(),ProductDTO.class);
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = conversionService.convert(productDTO,Product.class);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = conversionService.convert(productDTO,Product.class);
        productRepository.save(product);
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

}
