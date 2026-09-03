package org.example.webApplicationShopSpringBoot.service.bag;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.BagConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.BagInfoDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BagServiceImplTest {
    @Spy
    @InjectMocks
    BagServiceImpl bagService;

    @Mock
    ProductRepository productRepository;

    @Mock
    BagRepository bagRepository;

    @Mock
    BagConverter bagConverter;

    @Mock
    OrderPointServiceImpl orderPointService;


    @Test
    void addProductToBag() {
        Long productId = 5L;
        Long userId = 2L;
        BagDTORequest bagDTORequest = new BagDTORequest();
        bagDTORequest.setProductId(productId);
        User user = new User();
        user.setId(2L);
        Product product = new Product();
        product.setId(productId);
        Bag bag = new Bag();
        bag.setUser(user);
        bag.setProduct(product);
        bag.setCount(0L);
        when(productRepository.getReferenceById(productId)).thenReturn(product);
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        when(bagRepository.findById(primaryKeyBag)).thenReturn(Optional.of(bag));
        when(bagRepository.save(bag)).thenReturn(bag);
        bagService.addProductToBag(bagDTORequest, user);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(bagRepository, times(1)).findById(primaryKeyBag);
        verify(bagRepository, times(1)).save(bag);
    }

    @Test
    void addProductToBagNotFound() {
        Long productId = 5L;
        Long userId = 2L;
        BagDTORequest bagDTORequest = new BagDTORequest();
        bagDTORequest.setProductId(productId);
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        when(productRepository.getReferenceById(productId)).thenReturn(product);
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        when(bagRepository.findById(primaryKeyBag)).thenReturn(Optional.empty());
        when(bagRepository.save(any(Bag.class))).thenReturn(new Bag());
        bagService.addProductToBag(bagDTORequest, user);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(bagRepository, times(1)).findById(primaryKeyBag);
        verify(bagRepository, times(1)).save(any(Bag.class));
    }

    @Test
    void deleteProductFromBagAndDeleteBag() {
        Long productId = 5L;
        Long userId = 2L;
        BagDTORequest bagDTORequest = new BagDTORequest();
        bagDTORequest.setProductId(productId);
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        Bag bag = new Bag();
        bag.setUser(user);
        bag.setProduct(product);
        bag.setCount(0L);
        when(productRepository.getReferenceById(productId)).thenReturn(product);
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        when(bagRepository.findById(primaryKeyBag)).thenReturn(Optional.of(bag));
        bagService.deleteProductFromBag(bagDTORequest, user);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(bagRepository, times(1)).delete(bag);
    }

    @Test
    void deleteProductFromBag() {
        Long productId = 5L;
        Long userId = 2L;
        BagDTORequest bagDTORequest = new BagDTORequest();
        bagDTORequest.setProductId(productId);
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        Bag bag = new Bag();
        bag.setUser(user);
        bag.setProduct(product);
        bag.setCount(2L);
        when(productRepository.getReferenceById(productId)).thenReturn(product);
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        when(bagRepository.findById(primaryKeyBag)).thenReturn(Optional.of(bag));
        bagService.deleteProductFromBag(bagDTORequest, user);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(bagRepository, times(1)).save(bag);
    }

    @Test
    void getAllBags() {
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        List<Bag> list = new ArrayList<>();
        list.add(new Bag());
        list.add(new Bag());
        list.add(new Bag());
        when(bagRepository.getBagList(userId)).thenReturn(list);
        when(bagConverter.toDTO(any(Bag.class))).thenReturn(new BagDTOResponse());
        List<BagDTOResponse> bagDTOResponseList = bagService.getAllBags(user);
        verify(bagRepository, times(1)).getBagList(userId);
        verify(bagConverter, times(3)).toDTO(any(Bag.class));
        assertNotNull(bagDTOResponseList);
        assertFalse(bagDTOResponseList.isEmpty());

    }

    @Test
    void showBagSum() {
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        List<Bag> list = new ArrayList<>();
        Bag bag1 = Mockito.mock(Bag.class);
        Bag bag2 = Mockito.mock(Bag.class);
        list.add(bag1);
        list.add(bag2);
        when(bag1.getCount()).thenReturn(2L);
        when(bag2.getCount()).thenReturn(3L);
        when(bag1.getPrice()).thenReturn(new BigDecimal("3500.83"));
        when(bag2.getPrice()).thenReturn(new BigDecimal("2350"));
        when(bagRepository.getBagList(userId)).thenReturn(list);
        BigDecimal bagSum = bagService.showBagSum(user);
        assertNotNull(bagSum);
        assertEquals(new BigDecimal("14051.66"), bagSum);
    }

    @Test
    void showCalculatedDiscount() {
        BigDecimal fullPrice = new BigDecimal("7543.56");
        Integer discount = 5;
        BigDecimal calculatedDiscount = bagService.showCalculatedDiscount(fullPrice, discount);
        assertEquals(new BigDecimal("377.18"), calculatedDiscount);
    }

    @Test
    void calculateBagSumWithDiscount() {
        Long userId = 7L;
        User user = new User();
        user.setId(userId);
        BigDecimal fullPrice = new BigDecimal("2315.17");
        user.setDiscount(Discount.builder().discount(3).build());
        Integer discountValue = user.getDiscount().getDiscount();
        doReturn(fullPrice).when(bagService).showBagSum(user);
        doReturn(new BigDecimal("69.46")).when(bagService).showCalculatedDiscount(fullPrice, discountValue);
        BagSumWithDiscountDTO bagSumWithDiscountDTO = bagService.calculateBagSumWithDiscount(user);
        assertEquals(new BigDecimal("2245.71"), bagSumWithDiscountDTO.getPriceWithDiscount());
    }

    @Test
    void clearAllBags() {
        User user = new User();
        user.setId(5L);
        bagService.clearAllBags(user);
        verify(bagRepository, times(1)).deleteAllByUserId(user.getId());
    }

    @Test
    void openBag() {
        Long userId = 7L;
        User user = new User();
        user.setId(userId);
        List<Bag> list = new ArrayList<>();
        list.add(new Bag());
        list.add(new Bag());
        list.add(new Bag());
        list.add(new Bag());
        when(bagRepository.getBagList(userId)).thenReturn(list);
        when(bagConverter.toDTO(any(Bag.class))).thenReturn(new BagDTOResponse());
        List<BagDTOResponse> bagDTOResponseList = bagService.openBag(user);
        assertNotNull(bagDTOResponseList);
        assertFalse(bagDTOResponseList.isEmpty());
    }

    @Test
    void openEmptyBag() {
        Long userId = 7L;
        User user = new User();
        user.setId(userId);
        List<Bag> list = new ArrayList<>();
        when(bagRepository.getBagList(userId)).thenReturn(list);
        assertThrows(EmptyList.class, () -> bagService.openBag(user), "не выброшено исключение!");
    }

    @Test
    void returnBagInfo() {
        Long userId = 7L;
        User user = new User();
        user.setId(userId);
        doReturn(new ArrayList<BagDTOResponse>()).when(bagService).openBag(user);
        doReturn(BagSumWithDiscountDTO.builder().build()).when(bagService).calculateBagSumWithDiscount(user);
        when(orderPointService.getAllOrderPoints()).thenReturn(new ArrayList<OrderPointDTO>());
        BagInfoDTO bagInfoDTO = bagService.returnBagInfo(user);
        assertNotNull(bagInfoDTO);
    }
}