package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPointDTO {

    private Long id;

    private String orderPointAddress;

    private Time openingTime;

    private Time closeTime;
}
