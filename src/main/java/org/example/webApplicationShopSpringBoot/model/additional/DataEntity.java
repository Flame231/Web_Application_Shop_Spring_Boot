package org.example.webApplicationShopSpringBoot.model.additional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Getter
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @CreationTimestamp
    @Column( name = "createDateTime",updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(name = "updateDateTime",nullable = false)
    private Timestamp updateDateTime;
}
