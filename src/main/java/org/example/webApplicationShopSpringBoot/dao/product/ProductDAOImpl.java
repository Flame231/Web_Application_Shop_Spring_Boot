/*
package org.example.webApplicationShopSpringBoot.dao.product;


import org.example.webApplicationShopSpringBoot.dao.DAOImpl;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.ProductCategory;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.webApplicationShopSpringBoot.util.NamesUtil.PRODUCT_PER_PAGE;
public class ProductDAOImpl extends DAOImpl<Product> implements ProductRepository {

    public ProductDAOImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> getProductList() {
        getEm().clear();
        return getEm().createQuery("select distinct p from Product p left join fetch p.productCategory" +
                        " left join fetch  p.seller", Product.class)
               .getResultList();
    }

    @Override
    public Integer getProductsCount() {
        Long count = (Long) getEm().createQuery("select count(*) from  Product ").getSingleResult();
        return count.intValue();
    }
}
*/
