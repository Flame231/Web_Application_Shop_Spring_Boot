package org.example.webApplicationShopSpringBoot.model;

import lombok.*;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyArchivedUserOrderProduct;
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
public class ArchivedUserOrderProduct {

    @Id
    @ManyToOne
    @JoinColumn(name = "userOrderId")
    private ArchivedUserOrder archivedUserOrder;

    @Id
    private Long productId;

    @Column(updatable = false)
    private String productName;

    @Column(updatable = false)
    private Integer productCount;

    @Column(updatable = false)
    private Integer finalProductCount;

    @Column(updatable = false)
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp updateDateTime;

}
