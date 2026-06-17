package org.example.webApplicationShopSpringBoot.dao.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArchivedUserOrderRepository extends JpaRepository<ArchivedUserOrder, Long> {

    @Query("select distinct a from ArchivedUserOrder a left join fetch a.archivedUserOrderProducts where a.userId=:userId order by a.userOrderId desc")
    List<ArchivedUserOrder> getArchivedUserOrders( Long userId);

}
