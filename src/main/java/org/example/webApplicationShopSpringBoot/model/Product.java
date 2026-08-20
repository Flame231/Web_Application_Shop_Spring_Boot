package org.example.webApplicationShopSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product extends DataEntity {

    @Column
    private String productName;

    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;

    @Column
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "product")
    private Set<Bag> bags = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<UserOrderProduct> userOrderProducts = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
