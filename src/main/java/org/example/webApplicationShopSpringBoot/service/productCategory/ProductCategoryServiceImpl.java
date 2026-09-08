package org.example.webApplicationShopSpringBoot.service.productCategory;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.repository.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.ProductCategoryConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryConverter productCategoryConverter;

    @Override
    public PageResponse<ProductCategoryDTO> getProductCategoryDTOList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return new PageResponse<>(productCategoryPage.map(productCategoryConverter::toDTO));
    }

    @Override
    public List<ProductCategoryDTO> getActiveProductCategoryDTOList() {
        return productCategoryRepository.findAll(ItemStatus.ACTIVE).stream()
                .map(productCategoryConverter::toDTO)
                .toList();
    }

    @Override
    public ProductCategoryDTO findProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        return productCategoryConverter.toDTO(productCategory);
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryConverter.toEntity(productCategoryDTO);
        productCategoryRepository.save(productCategory);
        log.info("Категория продукта {} успешно добавлена!", productCategory.getCategory());
    }

    @Override
    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory existingProductCategory = getCategoryById(productCategoryDTO.getId());
        ProductCategory productCategory = productCategoryConverter.updateEntity(productCategoryDTO, existingProductCategory);
        productCategoryRepository.save(productCategory);
        log.info("Категория продукта с id {} успешно обновлена!", productCategory.getId());
    }

    @Override
    public void deleteProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        productCategory.setStatus(ItemStatus.DELETED);
        log.info("Статус категории продукта с id {} успешно изменён на {}!", productCategory.getId(), productCategory.getStatus().name());
    }

    @Override
    public void recoverProductCategory(Long id) {
        ProductCategory productCategory = getCategoryById(id);
        productCategory.setStatus(ItemStatus.ACTIVE);
        log.info("Статус категории продукта с id {} успешно изменён на {}!", productCategory.getId(), productCategory.getStatus().name());
    }

    @Override
    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Категория не найдена"));
    }

}
