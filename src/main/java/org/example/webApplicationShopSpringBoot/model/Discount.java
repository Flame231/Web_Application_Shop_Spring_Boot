package org.example.webApplicationShopSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Discount extends DataEntity {

    @Column
    private Integer discount;

    @Column
    private BigDecimal totalSum;

    @OneToMany(mappedBy = "discount")
    private Set<User> users = new HashSet<>();
}
