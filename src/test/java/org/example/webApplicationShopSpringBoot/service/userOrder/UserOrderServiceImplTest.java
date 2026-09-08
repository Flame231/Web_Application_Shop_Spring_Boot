package org.example.webApplicationShopSpringBoot.service.userOrder;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceImplTest {

    private final String ORDERS_LIST_EMPTY = "Активные заказы отсутствуют!";
    @InjectMocks
    private UserOrderServiceImpl userOrderService;

    @Mock
    private UserOrderRepository userOrderRepository;

    @Mock
    private UserOrderConverter userOrderConverter;

    @Test
    void showAllUserOrders() {
        User user = new User();
        user.setId(6L);
        List<UserOrder> list = new ArrayList<>();
        list.add(new UserOrder());
        list.add(new UserOrder());
        list.add(new UserOrder());
        when(userOrderRepository.findAllByUserId(user.getId())).thenReturn(list);
        when(userOrderConverter.toDTO(any(UserOrder.class))).thenReturn(new UserOrderDTO());
        List<UserOrderDTO> orderDTOList = userOrderService.showAllUserOrders(user);
        verify(userOrderRepository, times(1)).findAllByUserId(user.getId());
        verify(userOrderConverter, times(3)).toDTO(any(UserOrder.class));
        assertNotNull(orderDTOList);
    }

    @Test
    void showAllUserOrdersThrowsEmptyList() {
        User user = new User();
        user.setId(6L);
        List<UserOrder> list = new ArrayList<>();
        when(userOrderRepository.findAllByUserId(user.getId())).thenReturn(list);
        EmptyList emptyList = assertThrows(EmptyList.class, () -> userOrderService.showAllUserOrders(user));
        assertEquals(ORDERS_LIST_EMPTY, emptyList.getMessage());
    }

    @Test
    void showReadyUserOrdersByOrderPoint() {
        User user = new User();
        user.setId(6L);
        Long orderPointId = 7L;
        OrderPoint orderPoint = new OrderPoint();
        orderPoint.setId(orderPointId);
        user.setOrderPoint(orderPoint);
        List<UserOrder> list = new ArrayList<>();
        list.add(new UserOrder());
        list.add(new UserOrder());
        list.add(new UserOrder());
        when(userOrderRepository.getReadyUserOrderByOrderPoint(orderPointId)).thenReturn(list);
        when(userOrderConverter.toDTO(any(UserOrder.class))).thenReturn(new UserOrderDTO());
        List<UserOrderDTO> orderDTOList = userOrderService.showReadyUserOrdersByOrderPoint(user);
        verify(userOrderRepository, times(1)).getReadyUserOrderByOrderPoint(orderPointId);
        verify(userOrderConverter, times(3)).toDTO(any(UserOrder.class));
        assertNotNull(orderDTOList);
    }

    @Test
    void readyUserOrder() {
        Long userOrderId = 24L;
        UserOrder userOrder = new UserOrder();
        userOrder.setId(userOrderId);
        when(userOrderRepository.findById(userOrderId)).thenReturn(Optional.of(userOrder));
        userOrderService.readyUserOrder(userOrderId);
        verify(userOrderRepository, times(1)).findById(userOrderId);
        verify(userOrderRepository, times(1)).save(userOrder);
        assertEquals(OrderStatus.READY, userOrder.getOrderStatus());
    }

    @Test
    void showCreatedUserOrders() {
        User user = new User();
        OrderPoint orderPoint = new OrderPoint();
        user.setOrderPoint(orderPoint);
        orderPoint.setId(23L);
        List<UserOrder> userOrderList = new ArrayList<>();
        userOrderList.add(new UserOrder());
        userOrderList.add(new UserOrder());
        userOrderList.add(new UserOrder());
        userOrderList.add(new UserOrder());
        when(userOrderRepository.findAllCreatedUserOrder(user.getOrderPoint().getId())).thenReturn(userOrderList);
        when(userOrderConverter.toDTO(any(UserOrder.class)));
        List<UserOrderDTO> list = userOrderService.showCreatedUserOrders(user);
        assertNotNull(list);
    }

    @Test
    void getUserOrder() {
    }

    @Test
    void deleteUserOrder() {
    }

    @Test
    void createUserOrder() {
    }
}