package org.example.webApplicationShopSpringBoot.model.userOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserOrder extends DataEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "orderPoint_id")
    private OrderPoint orderPoint;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.REMOVE)
    @OrderBy("userOrder desc")
    private Set<UserOrderProduct> userOrderProduct = new LinkedHashSet<>();

    private BigDecimal orderSum;
}
