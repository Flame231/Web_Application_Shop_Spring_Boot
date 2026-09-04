package org.example.webApplicationShopSpringBoot.service.product;

import ch.qos.logback.classic.spi.IThrowableProxy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.ProductConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.*;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.EditProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ProductsAndBagsDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.exceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductConverter productConverter;
    private ProductCategoryService productCategoryService;
    private SellerService sellerService;
    private BagService bagService;

    @Override
    public PageResponse<ProductDTO> getActiveProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<Product> productsPage = productRepository.findAll(pageable, ItemStatus.ACTIVE);
        return new PageResponse<>(productsPage.map(product -> productConverter.toDTO(product)));
    }

    @Override
    public PageResponse<ProductDTO> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<Product> productsPage = productRepository.findAll(pageable);
        return new PageResponse<>(productsPage.map(product -> productConverter.toDTO(product)));
    }

    public ProductDTO findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Продукт не найден!"));
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
        Product existingProduct = productRepository.findById(newProductDTO.getId()).orElseThrow(() -> new ResourceNotFound("Продукт не найден!"));
        Product product = productConverter.updateEntity(newProductDTO, existingProduct);
        productRepository.save(product);
        log.info("Продукт с id {} успешно обновлён!", existingProduct.getId());
    }

    @Transactional
    @Override
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Продукт не найден!"));
        product.setStatus(ItemStatus.DELETED);
        log.info("Статус продукта с id {} успешно изменён на {}!", product.getId(), product.getStatus().name());
    }

    @Transactional
    @Override
    public void recoverProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Продукт не найден!"));
        product.setStatus(ItemStatus.ACTIVE);
        log.info("Статус продукта с id {} успешно изменён на {}!", product.getId(), product.getStatus().name());
    }

    @Override
    public EditProductDTO returnLists(Long productId) {
        ProductDTO productDTO = findProduct(productId);
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getActiveSellerDTOList();
        return EditProductDTO.builder().productDTO(productDTO)
                .productCategoryDTOList(productCategoryDTOList).sellerDTOList(sellerDTOList).build();
    }

    @Override
    public EditProductDTO returnLists() {
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getActiveSellerDTOList();
        return EditProductDTO.builder().productDTO(null)
                .productCategoryDTOList(productCategoryDTOList).sellerDTOList(sellerDTOList).build();
    }

    @Override
    public ProductsAndBagsDTO getProductsAndBags(int page, int pageSize, User user) {
        PageResponse<ProductDTO> productDTOList = getActiveProducts(page, pageSize);
        List<BagDTOResponse> bagDTOResponseList = bagService.getAllBags(user);
        BagSumWithDiscountDTO bagSum = bagService.calculateBagSumWithDiscount(user);
        return ProductsAndBagsDTO.builder().productDTOList(productDTOList).bagDTOResponseList(bagDTOResponseList).bagSum(bagSum).build();
    }

    public Product getProduct(Long productId){
       return productRepository.findById(productId).orElseThrow(()->new ResourceNotFound("Продукт не найден!"));
    }
}
