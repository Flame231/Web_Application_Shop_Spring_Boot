package org.example.webApplicationShopSpringBoot.service.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.service.PageResponse;

import java.math.BigDecimal;
import java.util.Set;

public interface ArchivedUserOrderService {

    ArchivedUserOrder createArchivedUserOrder(UserOrder userOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct, BigDecimal finalOrderSum);

    ArchivedUserOrder refuseUserOrder(UserOrder userOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct);

    PageResponse<ArchivedUserOrderDTO> showArchivedUserOrders(int page, int pageSize, User user);

}
