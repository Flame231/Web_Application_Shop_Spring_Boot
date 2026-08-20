package org.example.webApplicationShopSpringBoot.service.product;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ProductConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductConverter productConverter;


    @Override
    public PageResponse<ProductDTO> getActiveProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable, ItemStatus.ACTIVE);
        return new PageResponse<>(page.map(product -> productConverter.toDTO(product)));
    }

    @Override
    public PageResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return new PageResponse<ProductDTO>(page.map(product -> productConverter.toDTO(product)));
    }

    public ProductDTO findProduct(Long id) {
        Product product = productRepository.findById(id).get();
        return productConverter.toDTO(product);
    }

    @Override
    public void addProduct(NewProductDTO productDTO) {
        Product product = productConverter.toEntity(productDTO);
        productRepository.save(product);
        log.info("Продукт {} успешно добавлен!", product.getProductName());
    }

    @Override
    public void updateProduct(NewProductDTO newProductDTO) {
        Product existingProduct = productRepository.findById(newProductDTO.getId()).get();
        Product product = productConverter.updateEntity(newProductDTO, existingProduct);
        productRepository.save(product);
        log.info("Продукт с id {} успешно обновлён!", existingProduct.getId());
    }

    @Transactional
    @Override
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id).get();
        product.setStatus(ItemStatus.DELETED);
        log.info("Статус продукта с id {} успешно изменён на {}!", product.getId(), product.getStatus().name());
    }

    @Transactional
    @Override
    public void recoverProduct(Long id) {
        Product product = productRepository.findById(id).get();
        product.setStatus(ItemStatus.ACTIVE);
        log.info("Статус продукта с id {} успешно изменён на {}!", product.getId(), product.getStatus().name());
    }

}
