package org.example.webApplicationShopSpringBoot.service.product;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.ProductConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.*;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.EditProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ProductsAndBagsDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.bag.BagServiceImpl;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryServiceImpl;
import org.example.webApplicationShopSpringBoot.service.seller.SellerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @Mock
    private ProductCategoryServiceImpl productCategoryService;

    @Mock
    private SellerServiceImpl sellerService;

    @Mock
    private BagServiceImpl bagService;

    @Test
    void getActiveProducts() {
        int page = 1;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        List<Product> list = new ArrayList<>();
        list.add(new Product());
        list.add(new Product());
        list.add(new Product());
        List<ProductDTO> listDTO = new ArrayList<>();
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        Page<Product> productsPage = new PageImpl<>(list, pageable, list.size());
        when(productRepository.findAll(pageable, ItemStatus.ACTIVE)).thenReturn(productsPage);
        when(productConverter.toDTO(any(Product.class))).thenReturn(listDTO.get(0), listDTO.get(1), listDTO.get(2));
        PageResponse<ProductDTO> pageResponse = productService.getActiveProducts(page, pageSize);
        verify(productRepository, times(1)).findAll(pageable, ItemStatus.ACTIVE);
        verify(productConverter, times(list.size())).toDTO(any(Product.class));
        assertNotNull(pageResponse);
        assertEquals(PageResponse.class, pageResponse.getClass());
        assertEquals(pageSize, pageResponse.size());
        assertEquals(listDTO.get(0), pageResponse.content().get(0));
        assertEquals(listDTO.get(1), pageResponse.content().get(1));
        assertEquals(listDTO.get(2), pageResponse.content().get(2));
    }

    @Test
    void getAllProducts() {
        int page = 1;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        List<Product> list = new ArrayList<>();
        list.add(new Product());
        list.add(new Product());
        list.add(new Product());
        List<ProductDTO> listDTO = new ArrayList<>();
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        Page<Product> productsPage = new PageImpl<>(list, pageable, list.size());
        when(productRepository.findAll(pageable)).thenReturn(productsPage);
        when(productConverter.toDTO(any(Product.class))).thenReturn(listDTO.get(0), listDTO.get(1), listDTO.get(2));
        PageResponse<ProductDTO> pageResponse = productService.getAllProducts(page, pageSize);
        verify(productRepository, times(1)).findAll(pageable);
        verify(productConverter, times(list.size())).toDTO(any(Product.class));
        assertNotNull(pageResponse);
        assertEquals(PageResponse.class, pageResponse.getClass());
        assertEquals(pageSize, pageResponse.size());
        assertEquals(listDTO.get(0), pageResponse.content().get(0));
        assertEquals(listDTO.get(1), pageResponse.content().get(1));
        assertEquals(listDTO.get(2), pageResponse.content().get(2));
    }

    @Test
    void findProduct() {
        Long id = 5L;
        Product product = new Product();
        ProductDTO productDTOConverted = new ProductDTO();
        productDTOConverted.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productConverter.toDTO(product)).thenReturn(productDTOConverted);
        ProductDTO productDTO = productService.findProduct(id);
        verify(productRepository, times(1)).findById(id);
        verify(productConverter, times(1)).toDTO(product);
        assertNotNull(productDTO);
        assertEquals(productDTOConverted.getClass(), productDTO.getClass());
        assertEquals(id, productDTO.getId());
    }

    @Test
    void findProductThrow() {
        Long id = 5L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> productService.findProduct(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void addProduct() {
        Product product = new Product();
        NewProductDTO productDTO = new NewProductDTO();
        when(productRepository.save(product)).thenReturn(product);
        when(productConverter.toEntity(productDTO)).thenReturn(product);
        productService.addProduct(productDTO);
        verify(productRepository, times(1)).save(product);
        verify(productConverter, times(1)).toEntity(productDTO);
    }

    @Test
    void updateProduct() {
        Long id = 5L;
        NewProductDTO newProductDTO = new NewProductDTO();
        newProductDTO.setProductName("Продукт 2");
        newProductDTO.setId(5L);
        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setProductName("Продукт 1");
        Product updatedProduct = new Product();
        updatedProduct.setId(5L);
        updatedProduct.setProductName("Продукт 2");
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productConverter.updateEntity(newProductDTO, existingProduct)).thenReturn(updatedProduct);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        productService.updateProduct(newProductDTO);
        verify(productRepository, times(1)).findById(id);
        verify(productConverter, times(1)).updateEntity(newProductDTO, existingProduct);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void updateProductThrow() {
        Long id = 5L;
        NewProductDTO newProductDTO = new NewProductDTO();
        newProductDTO.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> productService.updateProduct(newProductDTO));
        verify(productRepository, times(1)).findById(id);
        verifyNoMoreInteractions(productConverter, productRepository);
    }

    @Test
    void removeProduct() {
        Long id = 5L;
        Product product = new Product();
        product.setStatus(ItemStatus.ACTIVE);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.removeProduct(id);
        assertEquals(ItemStatus.DELETED, product.getStatus());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void removeProductThrow() {
        Long id = 5L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> productService.removeProduct(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void recoverProduct() {
        Long id = 5L;
        Product product = new Product();
        product.setStatus(ItemStatus.DELETED);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.recoverProduct(id);
        assertEquals(ItemStatus.ACTIVE, product.getStatus());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void returnLists() {
        Long productId = 5L;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        List<ProductCategoryDTO> productCategoryDTOList = new ArrayList<>();
        List<SellerDTO> sellerDTOList = new ArrayList<>();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productConverter.toDTO(product)).thenReturn(productDTO);
        when(productCategoryService.getActiveProductCategoryDTOList()).thenReturn(productCategoryDTOList);
        when(sellerService.getActiveSellerDTOList()).thenReturn(sellerDTOList);
        EditProductDTO editProductDTO = productService.returnLists(productId);
        assertNotNull(editProductDTO);
        verify(productRepository, times(1)).findById(productId);
        verify(productConverter, times(1)).toDTO(product);
        verify(productCategoryService, times(1)).getActiveProductCategoryDTOList();
        verify(sellerService, times(1)).getActiveSellerDTOList();
        assertSame(productDTO, editProductDTO.getProductDTO());
        assertSame(productCategoryDTOList, editProductDTO.getProductCategoryDTOList());
        assertSame(sellerDTOList, editProductDTO.getSellerDTOList());
    }

    @Test
    void testReturnLists() {
        List<ProductCategoryDTO> productCategoryDTOList = new ArrayList<>();
        List<SellerDTO> sellerDTOList = new ArrayList<>();
        when(productCategoryService.getActiveProductCategoryDTOList()).thenReturn(productCategoryDTOList);
        when(sellerService.getActiveSellerDTOList()).thenReturn(sellerDTOList);
        EditProductDTO editProductDTO = productService.returnLists();
        assertNotNull(editProductDTO);
        verify(productCategoryService, times(1)).getActiveProductCategoryDTOList();
        verify(sellerService, times(1)).getActiveSellerDTOList();
        assertNull(editProductDTO.getProductDTO());
        assertSame(productCategoryDTOList, editProductDTO.getProductCategoryDTOList());
        assertSame(sellerDTOList, editProductDTO.getSellerDTOList());
    }

    @Test
    void getProductsAndBags() {
        int page = 1;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        List<Product> list = new ArrayList<>();
        list.add(new Product());
        list.add(new Product());
        list.add(new Product());
        List<ProductDTO> listDTO = new ArrayList<>();
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        listDTO.add(new ProductDTO());
        Page<Product> productsPage = new PageImpl<>(list, pageable, list.size());
        User user = new User();
        when(productRepository.findAll(pageable, ItemStatus.ACTIVE)).thenReturn(productsPage);
        when(productConverter.toDTO(any(Product.class))).thenReturn(listDTO.get(0), listDTO.get(1), listDTO.get(2));
        when(bagService.getAllBags(user)).thenReturn(new ArrayList<BagDTOResponse>());
        when(bagService.calculateBagSumWithDiscount(user)).thenReturn(new BagSumWithDiscountDTO());
        ProductsAndBagsDTO productsAndBagsDTO = productService.getProductsAndBags(page, pageSize, user);
        assertNotNull(productsAndBagsDTO);
        assertNotNull(productsAndBagsDTO);
        assertNotNull(productsAndBagsDTO.getProductDTOList());
        assertEquals(pageSize, productsAndBagsDTO.getProductDTOList().size()); // проверяем размер пагинации
        assertSame(listDTO.getFirst(), productsAndBagsDTO.getProductDTOList().content().get(0));
        verify(productRepository, times(1)).findAll(pageable, ItemStatus.ACTIVE);
        verify(productConverter, times(list.size())).toDTO(any(Product.class));
        verify(bagService, times(1)).getAllBags(user);
        verify(bagService, times(1)).calculateBagSumWithDiscount(user);
    }
}