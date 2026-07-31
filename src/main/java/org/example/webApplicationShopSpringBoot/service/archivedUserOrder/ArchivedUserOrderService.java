package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;

import java.util.List;

public interface ArchivedUserOrderService {

    void createArchivedUserOrder(Long userOrderId);

    void refuseUserOrder(Long userOrderId);

    List<ArchivedUserOrderDTO> showArchivedUserOrders();

}
