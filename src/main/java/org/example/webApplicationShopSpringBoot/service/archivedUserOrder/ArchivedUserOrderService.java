package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArchivedUserOrderService {

    void createArchivedUserOrder(Long userOrderId);

    void refuseUserOrder(Long userOrderId);

    PageResponse<ArchivedUserOrderDTO> showArchivedUserOrders(int page, int pageSize, User user);

}
