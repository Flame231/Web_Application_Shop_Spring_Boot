package org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct;


import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;

import java.util.Set;

public interface ArchivedUserOrderProductService {

    Set<ArchivedUserOrderProduct> createUserOrderProduct(Set<UserOrderProduct> userOrderProducts, ArchivedUserOrder archivedUserOrder);



    void setArchivedUserOrder(ArchivedUserOrder archivedUserOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct);
}
