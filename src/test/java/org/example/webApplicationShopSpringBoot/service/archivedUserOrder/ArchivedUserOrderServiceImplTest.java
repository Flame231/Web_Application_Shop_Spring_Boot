package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductServiceImpl;
import org.example.webApplicationShopSpringBoot.service.discount.DiscountService;
import org.example.webApplicationShopSpringBoot.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchivedUserOrderServiceImplTest {

    @InjectMocks
    private ArchivedUserOrderServiceImpl archivedUserOrderService;

    @Mock
    private UserOrderRepository userOrderRepository;
    @Mock
    private ArchivedUserOrderRepository archivedUserOrderRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private DiscountService discountService;

    @Mock
    private ArchivedUserOrderProductServiceImpl archivedUserOrderProductService;

    @Test
    void createArchivedUserOrder() {
        Long id = 2L;
        Set<UserOrderProduct> userOrderProducts = new HashSet<>();
        userOrderProducts.add(new UserOrderProduct());
        userOrderProducts.add(new UserOrderProduct());
        userOrderProducts.add(new UserOrderProduct());
        userOrderProducts.add(new UserOrderProduct());
        Set<ArchivedUserOrderProduct> archivedUserOrderProducts = new HashSet<>();
        when(userOrderRepository.findById(id)).thenReturn(Optional.of(new UserOrder()));
        when(archivedUserOrderProductService.createUserOrderProduct(userOrderProducts, null)).thenReturn(archivedUserOrderProducts);
        when(archivedUserOrderRepository.save(any(ArchivedUserOrder.class))).thenReturn(new ArchivedUserOrder());
        doNothing().when(userService).increaseTotalSum(any(Long.class), any(BigDecimal.class));
        doNothing().when(userOrderRepository).deleteById(any(Long.class));
        doNothing().when(discountService).checkUserDiscount(any(User.class));
        verify(userOrderRepository, times(1)).findById(any(Long.class));
        verify(userOrderRepository, times(1)).deleteById(any(Long.class));
        verify(archivedUserOrderProductService, times(1)).createUserOrderProduct(userOrderProducts, any(ArchivedUserOrder.class));
        verify(archivedUserOrderRepository, times(1)).save(any(ArchivedUserOrder.class));
        verify(userService, times(1)).increaseTotalSum(any(Long.class), any(BigDecimal.class));
        verify(userOrderRepository).deleteById(any(Long.class));
        verify(discountService).checkUserDiscount(any(User.class));
    }

    @Test
    void refuseUserOrder() {
    }

    @Test
    void showArchivedUserOrders() {
    }
}