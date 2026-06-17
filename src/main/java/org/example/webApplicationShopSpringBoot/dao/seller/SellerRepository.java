package org.example.webApplicationShopSpringBoot.dao.seller;

import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findAll();
}
