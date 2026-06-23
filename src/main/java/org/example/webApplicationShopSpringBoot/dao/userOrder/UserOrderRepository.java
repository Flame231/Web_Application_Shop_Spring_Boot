package org.example.webApplicationShopSpringBoot.dao.userOrder;


import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    List<UserOrder> findAllByUserId( Long userId);

    List<UserOrder> findAllByOrderPointId(Long orderPointId);
    @Query("select distinct uo from UserOrder uo left join fetch uo.userOrderProduct uop left join fetch uop.product p" +
            " left join fetch p.productCategory left join fetch p.seller where" +
            " uo.orderPoint.id =:orderPointId AND uo.orderStatus=:READY")
    List<UserOrder> getArrivedUserOrderByOrderPoint(Serializable orderPointId);

}
