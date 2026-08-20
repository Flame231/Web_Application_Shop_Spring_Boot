package org.example.webApplicationShopSpringBoot.repository.orderPoint;

import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPointRepository extends JpaRepository<OrderPoint, Long> {

    List<OrderPoint> findAll();
}
