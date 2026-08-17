package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ArchivedUserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.UserOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductService;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@AllArgsConstructor
public class ArchivedUserOrderServiceImpl implements ArchivedUserOrderService {
    private ArchivedUserOrderRepository archivedUserOrderRepository;
    private UserOrderRepository userOrderRepository;
    private ArchivedUserOrderProductService archivedUserOrderProductService;
    private ArchivedUserOrderConverter archivedUserOrderConverter;

    @Override
    public void createArchivedUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderRepository.findById(userOrderId).get();
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = archivedUserOrderProductService.createUserOrderProduct(
                userOrder.getUserOrderProduct(), null);
        BigDecimal finalOrderSum = archivedUserOrderProduct.stream().map(e -> e.getPrice()
                .multiply(new BigDecimal(e.getFinalProductCount()))).reduce(
                BigDecimal.ZERO, BigDecimal::add);

        ArchivedUserOrder archivedUserOrder = ArchivedUserOrder
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
        archivedUserOrderProduct.forEach(e -> e.setArchivedUserOrder(archivedUserOrder));
        archivedUserOrderRepository.save(archivedUserOrder);
        userOrderRepository.deleteById(userOrderId);
    }

    @Override
    public void refuseUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderRepository.findById(userOrderId).get();
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = archivedUserOrderProductService.createUserOrderProduct(
                userOrder.getUserOrderProduct(), null);
        ArchivedUserOrder archivedUserOrder = ArchivedUserOrder
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
        archivedUserOrderProduct.forEach(e -> e.setArchivedUserOrder(archivedUserOrder));
        archivedUserOrderRepository.save(archivedUserOrder);
        userOrderRepository.deleteById(userOrderId);
    }

    @Override
    public PageResponse<ArchivedUserOrderDTO> showArchivedUserOrders(Pageable pageable) {
        User user = PrincipalProvider.getUserFromSecurityContext();
        Page<ArchivedUserOrder> page = archivedUserOrderRepository.findByUserId(user.getId(), pageable);
        if (page.getTotalElements() == 0) {
            throw new EmptyList("История заказов пуста");
        }
        return
                new PageResponse<ArchivedUserOrderDTO>(page
                        .map(archivedUserOrder -> archivedUserOrderConverter.toDTO(archivedUserOrder)));
    }
}
