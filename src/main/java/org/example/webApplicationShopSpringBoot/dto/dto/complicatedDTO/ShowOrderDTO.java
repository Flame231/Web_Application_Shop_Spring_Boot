package org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class ShowOrderDTO {

    private UserOrderDTO userOrderDTO;

    private BigDecimal UserOrderProductSum;
}
