package org.example.webApplicationShopSpringBoot.service.productCategory;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity.ProductCategoryConverter;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity.ProductConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;
    private ProductCategoryConverter productCategoryConverter;


    public PageResponse<ProductCategoryDTO> getProductCategoryDTOList(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return new PageResponse<ProductCategoryDTO>(productCategoryPage.map(productCategory -> productCategoryConverter
                .toDTO(productCategory)));
    }

    public List<ProductCategoryDTO> getProductCategoryDTOList() {
        return productCategoryRepository.findAll().stream()
                .map(productCategory -> productCategoryConverter
                        .toDTO(productCategory))
                .toList();
    }

    public List<ProductCategoryDTO> getActiveProductCategoryDTOList() {
        return productCategoryRepository.findAll(ItemStatus.ACTIVE).stream()
                .map(productCategory -> productCategoryConverter
                        .toDTO(productCategory))
                .toList();
    }

    public ProductCategoryDTO findProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).get();
        return productCategoryConverter.toDTO(productCategory);
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryConverter.toEntity(productCategoryDTO);
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory existingProductCategory = productCategoryRepository.findById(productCategoryDTO.getId()).get();
        ProductCategory productCategory = productCategoryConverter.updateEntity(productCategoryDTO, existingProductCategory);
        productCategoryRepository.save(productCategory);
    }

    @Transactional
    @Override
    public void deleteProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).get();
        productCategory.setStatus(ItemStatus.DELETED);
    }

    @Transactional
    @Override
    public void recoverProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).get();
        productCategory.setStatus(ItemStatus.ACTIVE);
    }

}
