package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.ArchivedUserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArchivedUserOrderServiceImpl implements ArchivedUserOrderService {
    private final ArchivedUserOrderRepository archivedUserOrderRepository;
    private final ArchivedUserOrderConverter archivedUserOrderConverter;

    @Override
    public ArchivedUserOrder createArchivedUserOrder(UserOrder userOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct, BigDecimal finalOrderSum) {
        return ArchivedUserOrder
                .builder()
                .userOrderId(userOrder.getId())
                .orderStatus(OrderStatus.CLOSED)
                .userId(userOrder.getUser().getId())
                .orderPoint(userOrder.getOrderPoint().getOrderPointAddress())
                .orderSum(userOrder.getOrderSum())
                .finalOrderSum(finalOrderSum)
                .userOrderCreateDateTime(userOrder.getCreateDateTime())
                .archivedUserOrderProducts(archivedUserOrderProduct)
                .build();
    }

    @Override
    public ArchivedUserOrder refuseUserOrder(UserOrder userOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct) {
        return ArchivedUserOrder
                .builder()
                .userOrderId(userOrder.getId())
                .orderStatus(OrderStatus.REFUSED)
                .userId(userOrder.getUser().getId())
                .orderPoint(userOrder.getOrderPoint().getOrderPointAddress())
                .orderSum(userOrder.getOrderSum())
                .finalOrderSum(BigDecimal.ZERO)
                .userOrderCreateDateTime(userOrder.getCreateDateTime())
                .archivedUserOrderProducts(archivedUserOrderProduct)
                .build();
    }

    @Override
    public PageResponse<ArchivedUserOrderDTO> showArchivedUserOrders(int page, int pageSize, User user) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<ArchivedUserOrder> archivedUserOrderPage = archivedUserOrderRepository.findByUserId(user.getId(), pageable);
        if (archivedUserOrderPage.getTotalElements() == 0) {
            throw new EmptyList("История заказов пуста");
        }
        return new PageResponse<>(archivedUserOrderPage.map(archivedUserOrderConverter::toDTO));
    }
}
