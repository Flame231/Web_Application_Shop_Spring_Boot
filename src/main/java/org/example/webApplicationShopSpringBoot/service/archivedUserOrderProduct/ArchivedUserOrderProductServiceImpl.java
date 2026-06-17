package org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct;

import org.example.webApplicationShopSpringBoot.dao.archivedUserOrderProduct.ArchivedUserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;

import java.util.Set;
import java.util.stream.Collectors;

public class ArchivedUserOrderProductServiceImpl implements ArchivedUserOrderProductService {

    private ArchivedUserOrderProductRepository archivedUserOrderProductDAO;

    @Override
    public Set<ArchivedUserOrderProduct> createUserOrderProduct(Set<UserOrderProduct> userOrderProducts, ArchivedUserOrder archivedUserOrder) {

        return userOrderProducts.stream().map(e -> ArchivedUserOrderProduct
                .builder()
                .archivedUserOrder(archivedUserOrder)
                .productId(e.getProduct().getId()).productName(e.getProduct().getProductName())
                .productCount(e.getProductCount())
                .finalProductCount(e.getActualProductCount())
                .price(e.getProductPrice())
                .build()).collect(Collectors.toSet());
    }
}
