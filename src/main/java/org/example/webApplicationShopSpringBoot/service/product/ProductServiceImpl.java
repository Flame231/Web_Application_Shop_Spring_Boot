package org.example.webApplicationShopSpringBoot.service.product;


import jakarta.transaction.Transactional;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ConverterDTO<Product, ProductDTO> converterDTO;
    private ProductRepository productRepository;

    public ProductServiceImpl(ConverterDTO<Product, ProductDTO> converterDTO, ProductRepository productRepository) {
        this.converterDTO = converterDTO;
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return page.map(converterDTO::toDTO);
    }

    public ProductDTO findProduct(Long id) {
        return converterDTO.toDTO(productRepository.findById(id).get());
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = converterDTO.toEntity(productDTO);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = converterDTO.toEntity(productDTO);
        productRepository.save(product);
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

}
