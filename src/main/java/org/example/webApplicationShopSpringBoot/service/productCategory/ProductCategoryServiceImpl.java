package org.example.webApplicationShopSpringBoot.service.productCategory;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ProductCategoryConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.exceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    final private ProductCategoryRepository productCategoryRepository;
    final private ProductCategoryConverter productCategoryConverter;

    public PageResponse<ProductCategoryDTO> getProductCategoryDTOList(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return new PageResponse<ProductCategoryDTO>(productCategoryPage.map(productCategoryConverter::toDTO));
    }

    public List<ProductCategoryDTO> getProductCategoryDTOList() {
        return productCategoryRepository.findAll().stream()
                .map(productCategoryConverter::toDTO)
                .toList();
    }

    public List<ProductCategoryDTO> getActiveProductCategoryDTOList() {
        return productCategoryRepository.findAll(ItemStatus.ACTIVE).stream()
                .map(productCategoryConverter::toDTO)
                .toList();
    }

    public ProductCategoryDTO findProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        return productCategoryConverter.toDTO(productCategory);
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryConverter.toEntity(productCategoryDTO);
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory existingProductCategory = getCategoryById(productCategoryDTO.getId());
        ProductCategory productCategory = productCategoryConverter.updateEntity(productCategoryDTO, existingProductCategory);
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        productCategory.setStatus(ItemStatus.DELETED);
    }

    @Override
    public void recoverProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        productCategory.setStatus(ItemStatus.ACTIVE);
    }

    private ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Категория не найдена"));
    }

}
