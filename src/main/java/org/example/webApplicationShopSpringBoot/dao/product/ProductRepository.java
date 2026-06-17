package org.example.webApplicationShopSpringBoot.dao.product;

import org.example.webApplicationShopSpringBoot.dao.DAO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends DAO<Product> {

    void addSeller(Product product, Seller seller);

    void addProductCategory(Product product, ProductCategory productCategory);


    List<Product> getProductList(int currentPage);

    Integer getProductsCount();
}
