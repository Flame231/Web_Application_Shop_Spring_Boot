package org.example.webApplicationShopSpringBoot.dao.discount;

import org.example.webApplicationShopSpringBoot.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
