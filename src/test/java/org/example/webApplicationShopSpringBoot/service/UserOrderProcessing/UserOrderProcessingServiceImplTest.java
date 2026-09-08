package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.*;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductService;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.discount.DiscountService;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderProcessingServiceImplTest {

    private final String EMPTY_BAG = "Корзина товаров пуста!";
    @InjectMocks
    private UserOrderProcessingServiceImpl userOrderProcessingService;

    @Mock
    private UserOrderService userOrderService;

    @Mock
    private ArchivedUserOrderProductService archivedUserOrderProductService;

    @Mock
    private ArchivedUserOrderService archivedUserOrderService;

    @Mock
    private ArchivedUserOrderRepository archivedUserOrderRepository;

    @Mock
    private UserService userService;

    @Mock
    private DiscountService discountService;

    @Mock
    private BagService bagService;

    @Mock
    OrderPointService orderPointService;

    @Mock
    UserOrderRepository userOrderRepository;

    @Mock
    UserOrderProductService userOrderProductService;

    @Mock
    UserOrderConverter userOrderConverter;

    @Test
    void closeUserOrder() {
        Long userOrderId = 12L;
        UserOrder userOrder = new UserOrder();
        User user = new User();
        Discount discount = Discount.builder().discount(4).totalSum(new BigDecimal("2500.33")).build();
        user.setDiscount(discount);
        userOrder.setUser(user);
        ArchivedUserOrderProduct p1 = mock(ArchivedUserOrderProduct.class);
        ArchivedUserOrderProduct p2 = mock(ArchivedUserOrderProduct.class);
        when(p1.getPrice()).thenReturn(new BigDecimal("500.00"));
        when(p1.getCount()).thenReturn(1L); // 500 * 1 = 500
        when(p2.getPrice()).thenReturn(new BigDecimal("250.00"));
        when(p2.getCount()).thenReturn(2L);
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = Set.of(p1, p2);
        Set<UserOrderProduct> userOrderProducts = new HashSet<>();
        userOrderProducts.add(new UserOrderProduct());
        userOrderProducts.add(new UserOrderProduct());
        userOrderProducts.add(new UserOrderProduct());
        userOrder.setUserOrderProduct(userOrderProducts);
        ArchivedUserOrder archivedUserOrder = new ArchivedUserOrder();
        when(userOrderService.getUserOrder(userOrderId)).thenReturn(userOrder);
        when(archivedUserOrderProductService.createUserOrderProduct(userOrder.getUserOrderProduct(), null)).thenReturn(archivedUserOrderProduct);
        when(archivedUserOrderService.createArchivedUserOrder(any(UserOrder.class), anySet(), any(BigDecimal.class))).thenReturn(archivedUserOrder);
        userOrderProcessingService.closeUserOrder(userOrderId);
        verify(userOrderService, times(1)).getUserOrder(userOrderId);
        verify(archivedUserOrderProductService, times(1)).createUserOrderProduct(userOrder.getUserOrderProduct(), null);
        verify(archivedUserOrderService, times(1)).createArchivedUserOrder(any(UserOrder.class), anySet(), any(BigDecimal.class));
        verify(archivedUserOrderProductService, times(1)).setArchivedUserOrder(archivedUserOrder, archivedUserOrderProduct);
        verify(archivedUserOrderRepository, times(1)).save(archivedUserOrder);
        verify(userService, times(1)).increaseTotalSum(userOrder.getUser().getId(), archivedUserOrder.getFinalOrderSum());
        verify(userOrderService, times(1)).deleteUserOrder(userOrderId);
        verify(discountService, times(1)).checkUserDiscount(userOrder.getUser());
    }

    @Test
    void refuseUserOrder() {
        Long userOrderId = 12L;
        UserOrder userOrder = new UserOrder();
        userOrder.setId(userOrderId);
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = new HashSet<>();
        archivedUserOrderProduct.add(new ArchivedUserOrderProduct());
        archivedUserOrderProduct.add(new ArchivedUserOrderProduct());
        archivedUserOrderProduct.add(new ArchivedUserOrderProduct());
        userOrder.setUserOrderProduct(new HashSet<UserOrderProduct>());
        ArchivedUserOrder archivedUserOrder = new ArchivedUserOrder();
        when(userOrderService.getUserOrder(userOrderId)).thenReturn(userOrder);
        when(archivedUserOrderProductService.createUserOrderProduct(userOrder.getUserOrderProduct(), null)).thenReturn(archivedUserOrderProduct);
        when(archivedUserOrderService.refuseUserOrder(userOrder, archivedUserOrderProduct)).thenReturn(archivedUserOrder);
        userOrderProcessingService.refuseUserOrder(userOrderId);
        verify(userOrderService, times(1)).getUserOrder(userOrderId);
        verify(archivedUserOrderProductService, times(1)).createUserOrderProduct(userOrder.getUserOrderProduct(), null);
        verify(archivedUserOrderRepository, times(1)).save(archivedUserOrder);
        verify(userOrderService, times(1)).deleteUserOrder(userOrderId);
    }

    @Test
    void createUserOrder() {
        User user = new User();
        Discount discount = Discount.builder().discount(4).totalSum(new BigDecimal("2500.33")).build();
        user.setDiscount(discount);
        List<Bag> list = new ArrayList<Bag>();
        Product product1 = Product.builder().price(new BigDecimal("250.45")).build();
        Product product2 = Product.builder().price(new BigDecimal("37.63")).build();
        Product product3 = Product.builder().price(new BigDecimal("56.01")).build();
        list.add(Bag.builder().product(product1).count(3L).build());
        list.add(Bag.builder().product(product2).count(5L).build());
        list.add(Bag.builder().product(product3).count(1L).build());
        Long orderPointId = 25L;
        OrderPoint orderPoint = new OrderPoint();
        orderPoint.setId(orderPointId);
        UserOrder userOrder = new UserOrder();
        when(bagService.getAllBagsForOrder(user)).thenReturn(list);
        when(orderPointService.getOrderPoint(orderPointId)).thenReturn(orderPoint);
        when(userOrderService.createUserOrder(user, orderPoint)).thenReturn(userOrder);
        userOrderProcessingService.createUserOrder(user, orderPointId);
        verify(bagService, times(1)).getAllBagsForOrder(user);
        verify(orderPointService, times(1)).getOrderPoint(orderPointId);
        verify(userOrderService, times(1)).createUserOrder(user, orderPoint);
        verify(userOrderRepository, times(1)).save(userOrder);
        verify(userOrderProductService, times(list.size())).addBagToUserOrderProduct(any(Bag.class), any(UserOrder.class), any(User.class));
        assertEquals(new BigDecimal("955.69"), userOrder.getOrderSum());
    }

    @Test
    void createUserOrderThrowsEmptyList() {
        User user = new User();
        Long orderPointId = 25L;
        List<Bag> list = new ArrayList<Bag>();
        when(bagService.getAllBagsForOrder(user)).thenReturn(list);
        EmptyList emptyList = assertThrows(EmptyList.class, () -> userOrderProcessingService.createUserOrder(user, orderPointId));
        assertEquals(EMPTY_BAG, emptyList.getMessage());
        verify(bagService, times(1)).getAllBagsForOrder(user);
    }

    @Test
    void returnOrderInfo() {
        Long userOrderId = 7L;
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        BigDecimal UserOrderProductSum = new BigDecimal("5400.43");
        when(userOrderService.getUserOrder(userOrderId)).thenReturn(new UserOrder());
        when(userOrderConverter.toDTO(userOrderService.getUserOrder(userOrderId))).thenReturn(userOrderDTO);
        when(userOrderProductService.showUserOrderProductSum(userOrderId)).thenReturn(UserOrderProductSum);
        assertNotNull(userOrderProcessingService.returnOrderInfo(userOrderId));
        verify(userOrderConverter, times(1)).toDTO(userOrderService.getUserOrder(userOrderId));
        verify(userOrderProductService, times(1)).showUserOrderProductSum(userOrderId);
    }
}