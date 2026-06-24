package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;


import org.example.webApplicationShopSpringBoot.dao.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.dao.archivedUserOrderProduct.ArchivedUserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ArchivedUserOrderDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.UserOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductService;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class ArchivedUserOrderServiceImpl implements ArchivedUserOrderService {
    private ArchivedUserOrderRepository archivedUserOrderDAO;
    private ArchivedUserOrderProductRepository archivedUserOrderProductDAO;
    private UserOrderRepository userOrderDAO;
    private ArchivedUserOrderProductService archivedUserOrderProductService;

    public ArchivedUserOrderServiceImpl(ArchivedUserOrderRepository archivedUserOrderDAO, ArchivedUserOrderProductRepository archivedUserOrderProductDAO, UserOrderRepository userOrderDAO, ArchivedUserOrderProductService archivedUserOrderProductService) {
        this.archivedUserOrderDAO = archivedUserOrderDAO;
        this.archivedUserOrderProductDAO = archivedUserOrderProductDAO;
        this.userOrderDAO = userOrderDAO;
        this.archivedUserOrderProductService = archivedUserOrderProductService;
    }

    @Override
    public void createArchivedUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderDAO.findById(userOrderId).get();
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
                .OrderPoint(userOrder.getOrderPoint().getOrderPointAddress())
                .orderSum(userOrder.getOrderSum())
                .finalOrderSum(finalOrderSum)
                .userOrderCreateDateTime(userOrder.getCreateDateTime())
                .archivedUserOrderProducts(archivedUserOrderProduct)
                .build();
        archivedUserOrderProduct.forEach(e -> e.setArchivedUserOrder(archivedUserOrder));
        archivedUserOrderDAO.save(archivedUserOrder);
        userOrderDAO.deleteById(userOrderId);
    }

    @Override
    public void refuseUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderDAO.findById(userOrderId).get();
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = archivedUserOrderProductService.createUserOrderProduct(
                userOrder.getUserOrderProduct(), null);
        ArchivedUserOrder archivedUserOrder = ArchivedUserOrder
                .builder()
                .userOrderId(userOrder.getId())
                .orderStatus(OrderStatus.REFUSED)
                .userId(userOrder.getUser().getId())
                .OrderPoint(userOrder.getOrderPoint().getOrderPointAddress())
                .orderSum(userOrder.getOrderSum())
                .finalOrderSum(BigDecimal.ZERO)
                .userOrderCreateDateTime(userOrder.getCreateDateTime())
                .archivedUserOrderProducts(archivedUserOrderProduct)
                .build();
        archivedUserOrderProduct.forEach(e -> e.setArchivedUserOrder(archivedUserOrder));
        archivedUserOrderDAO.save(archivedUserOrder);
        userOrderDAO.deleteById(userOrderId);
    }


    @Override
    public List<ArchivedUserOrderDTO> showArchivedUserOrders(Long userId) {
        ConverterDTO<ArchivedUserOrder, ArchivedUserOrderDTO> converterDTO = new ArchivedUserOrderDTOConverter();
        return archivedUserOrderDAO
                .getArchivedUserOrders(userId)
                .stream()
                .map(converterDTO::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }
}
