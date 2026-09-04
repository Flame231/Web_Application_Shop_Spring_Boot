package org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArchivedUserOrderProductServiceImpl implements ArchivedUserOrderProductService {

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

    @Override
    public void setArchivedUserOrder(ArchivedUserOrder archivedUserOrder, Set<ArchivedUserOrderProduct> archivedUserOrderProduct) {
        archivedUserOrderProduct.forEach(e -> e.setArchivedUserOrder(archivedUserOrder));
    }
}
