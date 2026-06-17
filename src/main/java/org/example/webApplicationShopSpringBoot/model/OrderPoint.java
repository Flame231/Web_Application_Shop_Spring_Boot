package org.example.webApplicationShopSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderPoint extends DataEntity {

    @Column
    private String orderPointAddress;

    @Column
    private Time openingTime;

    @Column
    private Time closeTime;

    @OneToMany(mappedBy = "orderPoint")
    private Set<UserOrder> userOrder = new HashSet<>();

    @OneToMany(mappedBy = "orderPoint")
    private Set<User> users;
}
