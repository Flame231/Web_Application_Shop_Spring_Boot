package org.example.webApplicationShopSpringBoot.model.additional.primaryKeys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKeyBag implements Serializable {

    private Long user;

    private Long product;
}
