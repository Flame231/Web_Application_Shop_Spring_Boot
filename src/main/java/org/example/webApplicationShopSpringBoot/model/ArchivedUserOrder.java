package org.example.webApplicationShopSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ArchivedUserOrder {

    @Id
    private Long userOrderId;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private OrderStatus orderStatus;

    @Column(updatable = false)
    private Long userId;

    @Column(updatable = false)
    private String orderPoint;

    @Column(updatable = false)
    private BigDecimal orderSum;

    @Column(updatable = false)
    private BigDecimal finalOrderSum;

    @Column(updatable = false, nullable = false)
    private Timestamp userOrderCreateDateTime;

    @OneToMany(mappedBy = "archivedUserOrder", cascade = CascadeType.ALL)
    private Set<ArchivedUserOrderProduct> archivedUserOrderProducts = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp CreateDateTime;

    @Override
    public String toString() {
        return "ArchivedUserOrder{" +
                "userOrderId=" + userOrderId +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                ", orderPoint='" + orderPoint + '\'' +
                ", orderSum=" + orderSum +
                ", finalOrderSum=" + finalOrderSum +
                ", userOrderCreateDateTime=" + userOrderCreateDateTime +
                ", CreateDateTime=" + CreateDateTime +
                '}';
    }
}
