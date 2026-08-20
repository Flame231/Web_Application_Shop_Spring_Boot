package org.example.webApplicationShopSpringBoot.repository.seller;

import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Page<Seller> findAll(Pageable pageable);

    @Query("FROM Seller seller WHERE seller.status=:status")
    List<Seller> findAll(@Param("status") ItemStatus itemStatus);
}
