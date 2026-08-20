package org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct;


import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;

import java.util.Set;

public interface ArchivedUserOrderProductService {

    Set<ArchivedUserOrderProduct> createUserOrderProduct(Set<UserOrderProduct> userOrderProducts, ArchivedUserOrder archivedUserOrder);
}
