package org.example.webApplicationShopSpringBoot.repository.archivedUserOrder;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedUserOrderRepository extends JpaRepository<ArchivedUserOrder, Long> {

    @Query(value = "FROM ArchivedUserOrder ao WHERE ao.userId=:userId",
            countQuery = "SELECT count(ao) FROM ArchivedUserOrder ao WHERE ao.userId = :userId")
    Page<ArchivedUserOrder> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
