package org.example.webApplicationShopSpringBoot.dao.productCategory;

import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Page<ProductCategory> findAll(Pageable pageable);

    @Query("FROM ProductCategory  prodcat where prodcat.status=:status")
    List<ProductCategory> findAll(@Param(value = "status") ItemStatus itemStatus);
}
