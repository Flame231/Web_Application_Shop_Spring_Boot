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


@Repository
@Qualifier("productDAO")
public class ProductDAOImpl extends DAOImpl<Product> implements ProductRepository {
    public ProductDAOImpl() {
        super(Product.class);
    }
    @Override
    public void addSeller(Product product, Seller seller) {
        product.setSeller(seller);
        seller.getProduct().add(product);
    }

    @Override
    public void addProductCategory(Product product, ProductCategory productCategory) {
        product.setProductCategory(productCategory);
        productCategory.getProducts().add(product);
    }

    @Override
    public List<Product> getProductList(int currentPage) {
        getEm().clear();
        return getEm().createQuery("select distinct p from Product p left join fetch p.productCategory" +
                        " left join fetch  p.seller", Product.class)
                .setFirstResult((currentPage - 1) * PRODUCT_PER_PAGE).setMaxResults(PRODUCT_PER_PAGE).getResultList();
    }

    @Override
    public Integer getProductsCount() {
        Long count = (Long) getEm().createQuery("select count(*) from  Product ").getSingleResult();
        return count.intValue();
    }
}
