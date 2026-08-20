package org.example.webApplicationShopSpringBoot.model.userOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.service.ProductSum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "userOrder_product")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@IdClass(PrimaryKeyUserOrderProduct.class)
public class UserOrderProduct implements ProductSum {
    @Id
    @ManyToOne
    @JoinColumn(name = "userOrder_id")
    private UserOrder userOrder;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(updatable = false)
    private Long productCount;

    @Column
    private Long actualProductCount;

    @Column
    private BigDecimal productPrice;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updateDateTime;

    @Override
    public Long getCount() {
        return actualProductCount;
    }

    @Override
    public BigDecimal getPrice() {
        return productPrice;
    }
}
