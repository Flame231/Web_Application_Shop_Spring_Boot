package org.example.webApplicationShopSpringBoot.dao.archivedUserOrderProduct;

import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedUserOrderProductRepository extends JpaRepository<ArchivedUserOrderProduct, Long> {

}
