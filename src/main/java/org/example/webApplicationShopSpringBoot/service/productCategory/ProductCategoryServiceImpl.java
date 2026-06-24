package org.example.webApplicationShopSpringBoot.service.productCategory;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;
    private ConversionService conversionService;


    public Page<ProductCategoryDTO> getProductCategoryDTOList(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return productCategoryPage.map(product -> conversionService
                .convert(product, ProductCategoryDTO.class));
    }

    public List<ProductCategoryDTO> getProductCategoryDTOList() {
        return productCategoryRepository.findAll().stream()
                .map(product -> conversionService
                        .convert(product, ProductCategoryDTO.class))
                .toList();
    }

    public ProductCategoryDTO findProductCategory(Long id) {
        return conversionService.convert(productCategoryRepository.findById(id).get(), ProductCategoryDTO.class);
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = conversionService.convert(productCategoryDTO, ProductCategory.class);
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = conversionService.convert(productCategoryDTO, ProductCategory.class);
        productCategoryRepository.save(productCategory);
    }


    @Override
    public void deleteProductCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
