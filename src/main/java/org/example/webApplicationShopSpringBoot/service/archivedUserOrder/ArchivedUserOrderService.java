package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArchivedUserOrderService {

    void createArchivedUserOrder(Long userOrderId);

    void refuseUserOrder(Long userOrderId);

    PageResponse<ArchivedUserOrderDTO> showArchivedUserOrders(Pageable pageable);

}
