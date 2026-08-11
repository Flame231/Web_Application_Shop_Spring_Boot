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
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Seller extends DataEntity {

    @Column
    private String sellerName;

    @Column
    private String sellerAddress;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @OneToMany(mappedBy = "seller")
    private Set<Product> product = new HashSet<>();
}
