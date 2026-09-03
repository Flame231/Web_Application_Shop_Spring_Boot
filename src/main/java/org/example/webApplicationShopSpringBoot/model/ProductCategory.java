package org.example.webApplicationShopSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_category")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategory extends DataEntity {

    @Column
    private String category;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
