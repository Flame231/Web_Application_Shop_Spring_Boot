package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.ArchivedUserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchivedUserOrderServiceImplTest {

    private final String ORDER_HISTORY_EMPTY = "История заказов пуста";
    @InjectMocks
    private ArchivedUserOrderServiceImpl archivedUserOrderService;

    @Mock
    private ArchivedUserOrderRepository archivedUserOrderRepository;

    @Mock
    private ArchivedUserOrderConverter archivedUserOrderConverter;

    @Test
    void createArchivedUserOrder() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(21L);
        userOrder.setOrderPoint(OrderPoint.builder().orderPointAddress("Адрес пункта выдачи").build());
        userOrder.setUser(User.builder().id(21L).build());
        Set<ArchivedUserOrderProduct> archivedUserOrderProducts = new HashSet<>();
        BigDecimal finalOrderSum = new BigDecimal("2556.67");
        ArchivedUserOrder archivedUserOrder = archivedUserOrderService.createArchivedUserOrder(userOrder, archivedUserOrderProducts, finalOrderSum);
        assertNotNull(archivedUserOrder);
        assertEquals(userOrder.getId(), archivedUserOrder.getUserOrderId());
        assertEquals(archivedUserOrderProducts, archivedUserOrder.getArchivedUserOrderProducts());
        assertEquals(finalOrderSum, archivedUserOrder.getFinalOrderSum());
        assertEquals(OrderStatus.CLOSED, archivedUserOrder.getOrderStatus());
    }

    @Test
    void refuseUserOrder() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(21L);
        userOrder.setOrderPoint(OrderPoint.builder().orderPointAddress("Адрес пункта выдачи").build());
        userOrder.setUser(User.builder().id(21L).build());
        Set<ArchivedUserOrderProduct> archivedUserOrderProducts = new HashSet<>();
        ArchivedUserOrder archivedUserOrder = archivedUserOrderService.refuseUserOrder(userOrder, archivedUserOrderProducts);
        assertNotNull(archivedUserOrder);
        assertEquals(userOrder.getId(), archivedUserOrder.getUserOrderId());
        assertEquals(archivedUserOrderProducts, archivedUserOrder.getArchivedUserOrderProducts());
        assertEquals(OrderStatus.REFUSED, archivedUserOrder.getOrderStatus());
        assertEquals(BigDecimal.ZERO, archivedUserOrder.getFinalOrderSum());
    }

    @Test
    void showArchivedUserOrders() {
        int page = 1;
        int pageSize = 3;
        User user = User.builder().id(5L).build();
        List<ArchivedUserOrder> list = new ArrayList<>();
        list.add(new ArchivedUserOrder());
        list.add(new ArchivedUserOrder());
        list.add(new ArchivedUserOrder());
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<ArchivedUserOrder> archivedUserOrderPage = new PageImpl<>(list, pageable, 20);
        when(archivedUserOrderRepository.findByUserId(user.getId(), pageable)).thenReturn(archivedUserOrderPage);
        when(archivedUserOrderConverter.toDTO(any(ArchivedUserOrder.class))).thenReturn(new ArchivedUserOrderDTO());
        PageResponse<ArchivedUserOrderDTO> pageResponse = archivedUserOrderService.showArchivedUserOrders(page, pageSize, user);
        assertNotNull(pageResponse);
        assertEquals(pageSize, pageResponse.content().size());
    }

    @Test
    void showArchivedUserOrdersThrowsEmptyList() {
        int page = 1;
        int pageSize = 3;
        User user = User.builder().id(5L).build();
        List<ArchivedUserOrder> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<ArchivedUserOrder> archivedUserOrderPage = new PageImpl<>(list, pageable, 0);
        when(archivedUserOrderRepository.findByUserId(user.getId(), pageable)).thenReturn(archivedUserOrderPage);
        EmptyList emptyList = assertThrows(EmptyList.class, () -> archivedUserOrderService.showArchivedUserOrders(page, pageSize, user));
        assertEquals(ORDER_HISTORY_EMPTY, emptyList.getMessage());
    }

}