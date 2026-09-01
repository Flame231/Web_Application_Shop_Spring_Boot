package org.example.webApplicationShopSpringBoot.service.productCategory;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.ProductCategoryConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.repository.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @InjectMocks
    ProductCategoryServiceImpl productCategoryService;

    @Mock
    ProductCategoryRepository productCategoryRepository;

    @Mock
    ProductCategoryConverter productCategoryConverter;

    @Test
    void getProductCategoryDTOList() {
        int page = 1;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        List<ProductCategory> list = new ArrayList<>();
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        List<ProductCategoryDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        Page<ProductCategory> pageList = new PageImpl<>(list, pageable, list.size());
        when(productCategoryRepository.findAll(pageable)).thenReturn(pageList);
        when(productCategoryConverter.toDTO(any(ProductCategory.class))).thenReturn(productDTOList.get(0), productDTOList.get(1), productDTOList.get(2), productDTOList.get(3));
        PageResponse<ProductCategoryDTO> pageResponse = productCategoryService.getProductCategoryDTOList(page, pageSize);
        verify(productCategoryRepository, times(1)).findAll(pageable);
        verify(productCategoryConverter, times(4)).toDTO(any(ProductCategory.class));
        assertEquals(PageResponse.class, pageResponse.getClass());
        assertEquals(list.size(), pageResponse.content().size());
    }

    @Test
    void getActiveProductCategoryDTOList() {
        List<ProductCategory> list = new ArrayList<>();
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        list.add(new ProductCategory());
        List<ProductCategoryDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        productDTOList.add(new ProductCategoryDTO());
        when(productCategoryRepository.findAll(ItemStatus.ACTIVE)).thenReturn(list);
        when(productCategoryConverter.toDTO(any(ProductCategory.class))).thenReturn(productDTOList.get(0), productDTOList.get(1), productDTOList.get(2), productDTOList.get(3));
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        verify(productCategoryRepository.findAll(ItemStatus.ACTIVE), times(1));
        verify(productCategoryConverter.toDTO(any(ProductCategory.class)), times(4));
        assertNotNull(productCategoryDTOList);
    }

    @Test
    void findProductCategory() {
        Long id = 5L;
        ProductCategory productCategory = new ProductCategory();
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        when(productCategoryConverter.toDTO(productCategory)).thenReturn(productCategoryDTO);
        when(productCategoryRepository.findById(id)).thenReturn(Optional.of(productCategory));
        ProductCategoryDTO returnedProductCategoryDTO = productCategoryService.findProductCategory(id);
        verify(productCategoryRepository, times(1)).findById(id);
        verify(productCategoryConverter, times(1)).toDTO(productCategory);
        assertNotNull(returnedProductCategoryDTO);
    }

    @Test
    void addProductCategory() {
    }

    @Test
    void updateProductCategory() {
    }

    @Test
    void deleteProductCategory() {
    }

    @Test
    void recoverProductCategory() {
    }
}