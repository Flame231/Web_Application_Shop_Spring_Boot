package org.example.webApplicationShopSpringBoot.service.productCategory;


import jakarta.transaction.Transactional;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ProductCategoryDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryRepository productCategoryDAO;
    private ConverterDTO<ProductCategory, ProductCategoryDTO> converterDTO;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryDAO, ConverterDTO<ProductCategory, ProductCategoryDTO> converterDTO) {
        this.productCategoryDAO = productCategoryDAO;
        this.converterDTO = converterDTO;
    }

    public Page<ProductCategoryDTO> getProductCategoryDTOList(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryDAO.findAll(pageable);
        return productCategoryPage.map(converterDTO::toDTO);
    }

    public List<ProductCategoryDTO> getProductCategoryDTOList() {
        return productCategoryDAO.findAll().stream()
                .map(converterDTO::toDTO)
                .toList();
    }

    public ProductCategoryDTO findProductCategory(Long id) {
        return converterDTO.toDTO(productCategoryDAO.findById(id).get());
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = converterDTO.toEntity(productCategoryDTO);
        productCategoryDAO.save(productCategory);
    }

    @Override
    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = converterDTO.toEntity(productCategoryDTO);
        productCategoryDAO.save(productCategory);
    }


    @Override
    public void deleteProductCategory(Long id) {
        productCategoryDAO.deleteById(id);
    }
}
