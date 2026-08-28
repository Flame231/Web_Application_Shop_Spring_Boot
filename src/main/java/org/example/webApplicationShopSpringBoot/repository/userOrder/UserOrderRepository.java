package org.example.webApplicationShopSpringBoot.repository.userOrder;


import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    List<UserOrder> findAllByUserId(Long userId);

    @Query("select distinct uo from UserOrder uo left join fetch uo.userOrderProduct uop left join fetch uop.product p" +
            " left join fetch p.productCategory left join fetch p.seller where" +
            " uo.orderPoint.id =:orderPointId AND uo.orderStatus='CREATED'")
    List<UserOrder> findAllCreatedUserOrder(@Param("orderPointId") Long orderPointId);

    @Query("select distinct uo from UserOrder uo left join fetch uo.userOrderProduct uop left join fetch uop.product p" +
            " left join fetch p.productCategory left join fetch p.seller where" +
            " uo.orderPoint.id =:orderPointId AND uo.orderStatus='READY'")
    List<UserOrder> getReadyUserOrderByOrderPoint(Long orderPointId);

}
