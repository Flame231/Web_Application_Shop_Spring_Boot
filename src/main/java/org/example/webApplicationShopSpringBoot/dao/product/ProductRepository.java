package org.example.webApplicationShopSpringBoot.dao.product;

import org.example.webApplicationShopSpringBoot.model.Product;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(@NonNull Pageable pageable);
}
