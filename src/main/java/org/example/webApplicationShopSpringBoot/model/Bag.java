package org.example.webApplicationShopSpringBoot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.ProductSum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.stream.Stream;

@Entity
@SuperBuilder
@Table
@NoArgsConstructor
@Setter
@Getter
@IdClass(PrimaryKeyBag.class)
public class Bag implements ProductSum<Bag> {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private Long count;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updateDateTime;

    @Override
    public String toString() {
        return "Bag{" +
                "user=" + user +
                ", product=" + product +
                ", count=" + count +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                '}';
    }

    @Override
    public BigDecimal getPrice() {
        return product.getPrice();
    }
}
