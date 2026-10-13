package org.example.webApplicationShopSpringBoot.service.userOrder;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderOperatorAccessTest {

    @InjectMocks
    private UserOrderServiceImpl userOrderService;

    @Mock
    private UserOrderRepository userOrderRepository;

    @Mock
    private UserOrderConverter userOrderConverter;

    private UserOrder userOrder;
    private User operator;

    @BeforeEach
    void setUp() {
        OrderPoint orderPoint = new OrderPoint();
        orderPoint.setId(25L);
        userOrder = new UserOrder();
        userOrder.setId(7L);
        userOrder.setOrderPoint(orderPoint);
        userOrder.setOrderStatus(OrderStatus.CREATED);

        OrderPoint operatorOrderPoint = new OrderPoint();
        operatorOrderPoint.setId(25L);
        operator = new User();
        operator.setOrderPoint(operatorOrderPoint);
        when(userOrderRepository.findById(7L)).thenReturn(Optional.of(userOrder));
    }

    @Test
    void returnsOrderForMatchingPointIds() {
        assertSame(userOrder, userOrderService.getUserOrderForOperator(7L, operator));
    }

    @Test
    void deniesOtherOrderPoint() {
        operator.getOrderPoint().setId(26L);
        assertAccessDenied(operator);
    }

    @Test
    void deniesMissingOperator() {
        assertAccessDenied(null);
    }

    @Test
    void deniesOperatorWithoutOrderPoint() {
        operator.setOrderPoint(null);
        assertAccessDenied(operator);
    }

    @Test
    void deniesOperatorWithoutOrderPointId() {
        operator.getOrderPoint().setId(null);
        assertAccessDenied(operator);
    }

    @Test
    void deniesOrderWithoutOrderPoint() {
        userOrder.setOrderPoint(null);
        assertAccessDenied(operator);
    }

    @Test
    void deniesOrderWithoutOrderPointId() {
        userOrder.getOrderPoint().setId(null);
        assertAccessDenied(operator);
    }

    @Test
    void deniesOrderPointsWithBothIdsNull() {
        userOrder.getOrderPoint().setId(null);
        operator.getOrderPoint().setId(null);
        assertAccessDenied(operator);
    }

    @Test
    void readyUserOrderDoesNotChangeOtherOrderPointOrder() {
        operator.getOrderPoint().setId(26L);
        assertThrows(AccessDeniedException.class,
                () -> userOrderService.readyUserOrder(7L, operator));
        assertEquals(OrderStatus.CREATED, userOrder.getOrderStatus());
        verify(userOrderRepository, never()).save(any(UserOrder.class));
    }

    private void assertAccessDenied(User currentOperator) {
        assertThrows(AccessDeniedException.class,
                () -> userOrderService.getUserOrderForOperator(7L, currentOperator));
        verify(userOrderRepository, never()).save(any(UserOrder.class));
        verifyNoInteractions(userOrderConverter);
    }
}
