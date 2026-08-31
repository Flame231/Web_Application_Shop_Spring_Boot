package org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchivedUserOrderProductServiceImplTest {

    private ArchivedUserOrderProductServiceImpl archivedUserOrderProductService = new ArchivedUserOrderProductServiceImpl();


    @Test
    void createUserOrderProduct() {
        Product product = Product.builder().id(1L).price(new BigDecimal(1200L)).build();
        Product product2 = Product.builder().id(2L).price(new BigDecimal(1500L)).build();
        UserOrderProduct userOrderProduct = UserOrderProduct.builder()
                .product(product)
                .productCount(2L)
                .actualProductCount(1L)
                .productPrice(product.getPrice()).build();
        UserOrderProduct userOrderProduct2 = UserOrderProduct.builder()
                .product(product2)
                .productCount(2L)
                .actualProductCount(1L)
                .productPrice(product2.getPrice()).build();
        Set<UserOrderProduct> userOrderProducts = Set.of(userOrderProduct, userOrderProduct2);
        ArchivedUserOrder archivedUserOrder = new ArchivedUserOrder();
        Set<ArchivedUserOrderProduct> archivedUserOrderProducts = archivedUserOrderProductService.createUserOrderProduct(userOrderProducts, archivedUserOrder);
        assertEquals(userOrderProducts.size(), archivedUserOrderProducts.size());
        assertThat(archivedUserOrderProducts)
                .hasSize(2)
                .extracting("productId", "price", "productCount", "finalProductCount", "archivedUserOrder")
                .containsExactlyInAnyOrder(
                        tuple(1L, new BigDecimal(1200L), 2L, 1L, archivedUserOrder),
                        tuple(2L, new BigDecimal(1500L), 2L, 1L, archivedUserOrder)
                );
    }
}