package org.example.webApplicationShopSpringBoot.dao.orderPoint;

import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPointRepository extends JpaRepository<OrderPoint, Long> {

    List<OrderPoint> findAll();
}
