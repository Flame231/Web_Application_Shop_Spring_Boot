package org.example.webApplicationShopSpringBoot.dao.bag;

import jakarta.transaction.Transactional;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BagRepository extends JpaRepository<Bag, PrimaryKeyBag> {

    @Query("select distinct bag from Bag bag left join fetch bag.product p left join fetch p.productCategory " +
            "left join fetch p.seller where bag.user.id = :userId")
   List<Bag> getBagList(Long userId);

    @Transactional
    void deleteAllByUserId(Long userId);
}
