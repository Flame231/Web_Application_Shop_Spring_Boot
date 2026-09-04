package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderProductServiceImplTest {

    @InjectMocks
    private UserOrderProductServiceImpl userOrderProductService;

    @Mock
    private UserOrderProductRepository userOrderProductRepository;

    @Mock
    UserOrderService userOrderService;

    @Mock
    UserService userService;


    @Test
    void addProductToOrderFailed() {
        UserOrderChangeCountDTO userOrderChangeCountDTO = new UserOrderChangeCountDTO();
        userOrderChangeCountDTO.setUserOrderId(2L);
        userOrderChangeCountDTO.setProductId(3L);
        userOrderChangeCountDTO.setCount(1L);
        UserOrderProduct userOrderProduct = new UserOrderProduct();
        userOrderProduct.setActualProductCount(3L);
        userOrderProduct.setProductCount(3L);
        when(userOrderProductRepository.findById(any(PrimaryKeyUserOrderProduct.class))).thenReturn(Optional.of(userOrderProduct));
        userOrderProductService.addProductToOrder(userOrderChangeCountDTO);
        assertEquals(3L, userOrderProduct.getActualProductCount());
        verify(userOrderProductRepository, times(1)).findById(any(PrimaryKeyUserOrderProduct.class));
    }

    @Test
    void addProductToOrderSuccess() {
        UserOrderChangeCountDTO userOrderChangeCountDTO = new UserOrderChangeCountDTO();
        userOrderChangeCountDTO.setUserOrderId(2L);
        userOrderChangeCountDTO.setProductId(3L);
        userOrderChangeCountDTO.setCount(1L);
        UserOrderProduct userOrderProduct = new UserOrderProduct();
        userOrderProduct.setActualProductCount(3L);
        userOrderProduct.setProductCount(4L);
        when(userOrderProductRepository.findById(any(PrimaryKeyUserOrderProduct.class))).thenReturn(Optional.of(userOrderProduct));
        userOrderProductService.addProductToOrder(userOrderChangeCountDTO);
        assertEquals(4L, userOrderProduct.getActualProductCount());
        verify(userOrderProductRepository, times(1)).findById(any(PrimaryKeyUserOrderProduct.class));
    }

    @Test
    void deleteProductFromOrderFailed() {
        UserOrderChangeCountDTO userOrderChangeCountDTO = new UserOrderChangeCountDTO();
        userOrderChangeCountDTO.setUserOrderId(2L);
        userOrderChangeCountDTO.setProductId(3L);
        userOrderChangeCountDTO.setCount(1L);
        UserOrderProduct userOrderProduct = new UserOrderProduct();
        userOrderProduct.setActualProductCount(0L);
        userOrderProduct.setProductCount(3L);
        when(userOrderProductRepository.findById(any(PrimaryKeyUserOrderProduct.class))).thenReturn(Optional.of(userOrderProduct));
        userOrderProductService.deleteProductFromOrder(userOrderChangeCountDTO);
        verify(userOrderProductRepository, times(1)).findById(any(PrimaryKeyUserOrderProduct.class));
        assertEquals(0L, userOrderProduct.getActualProductCount());
    }

    @Test
    void deleteProductFromOrderSuccess() {
        UserOrderChangeCountDTO userOrderChangeCountDTO = new UserOrderChangeCountDTO();
        userOrderChangeCountDTO.setUserOrderId(2L);
        userOrderChangeCountDTO.setProductId(3L);
        userOrderChangeCountDTO.setCount(1L);
        UserOrderProduct userOrderProduct = new UserOrderProduct();
        userOrderProduct.setActualProductCount(3L);
        userOrderProduct.setProductCount(3L);
        when(userOrderProductRepository.findById(any(PrimaryKeyUserOrderProduct.class))).thenReturn(Optional.of(userOrderProduct));
        userOrderProductService.deleteProductFromOrder(userOrderChangeCountDTO);
        verify(userOrderProductRepository, times(1)).findById(any(PrimaryKeyUserOrderProduct.class));
        assertEquals(2L, userOrderProduct.getActualProductCount());
    }

    @Test
    void showUserOrderProductSum() {
        Long userOrderId = 4L;
        Long userId = 2L;
        UserOrder userOrder = new UserOrder();
        User user = new User();
        user.setDiscount(Discount.builder().discount(3).totalSum(new BigDecimal(150000)).build());
        user.setId(userId);
        userOrder.setUser(user);
        UserOrderProduct userOrderProduct1 = mock(UserOrderProduct.class);
        UserOrderProduct userOrderProduct2 = mock(UserOrderProduct.class);
        UserOrderProduct userOrderProduct3 = mock(UserOrderProduct.class);
        when(userOrderProduct1.getPrice()).thenReturn(new BigDecimal("2500.25"));
        when(userOrderProduct2.getPrice()).thenReturn(new BigDecimal("3102.65"));
        when(userOrderProduct3.getPrice()).thenReturn(new BigDecimal("2210.7"));
        when(userOrderProduct1.getCount()).thenReturn(2L);
        when(userOrderProduct2.getCount()).thenReturn(3L);
        when(userOrderProduct3.getCount()).thenReturn(5L);
        List<UserOrderProduct> userOrderProductList = new ArrayList<>();
        userOrderProductList.add(userOrderProduct1);
        userOrderProductList.add(userOrderProduct2);
        userOrderProductList.add(userOrderProduct3);
        when(userOrderService.getUserOrder(userOrderId)).thenReturn(userOrder);
        when(userService.getUser(userOrder.getUser().getId())).thenReturn(user);
        when(userOrderProductRepository.findByUserOrderId(userOrderId)).thenReturn(userOrderProductList);
        assertEquals(new BigDecimal("24601.09"), userOrderProductService.showUserOrderProductSum(userOrderId));
        verify(userOrderService, times(1)).getUserOrder(userOrderId);
        verify(userService, times(1)).getUser(userOrder.getUser().getId());
        verify(userOrderProductRepository, times(1)).findByUserOrderId(userOrderId);
    }
}