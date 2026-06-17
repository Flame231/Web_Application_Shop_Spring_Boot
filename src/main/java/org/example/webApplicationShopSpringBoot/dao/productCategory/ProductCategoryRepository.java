package org.example.webApplicationShopSpringBoot.dao.productCategory;


import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAll();
}
