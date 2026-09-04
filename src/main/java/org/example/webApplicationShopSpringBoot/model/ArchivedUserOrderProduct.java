package org.example.webApplicationShopSpringBoot.model;

import lombok.*;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.service.ProductSum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PrimaryKeyArchivedUserOrderProduct.class)
public class ArchivedUserOrderProduct implements ProductSum {

    @Id
    @ManyToOne
    @JoinColumn(name = "userOrderId")
    private ArchivedUserOrder archivedUserOrder;

    @Id
    private Long productId;

    @Column(updatable = false)
    private String productName;

    @Column(updatable = false)
    private Long productCount;

    @Column(updatable = false)
    private Long finalProductCount;

    @Column(updatable = false)
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp updateDateTime;

    @Override
    public Long getCount() {
        return finalProductCount;
    }
}
