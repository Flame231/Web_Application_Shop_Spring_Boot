package org.example.webApplicationShopSpringBoot.repository.product;

import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("FROM Product prod WHERE prod.status=:status")
    Page<Product> findAll(@NonNull Pageable pageable, @Param(value = "status") ItemStatus itemStatus);

}
