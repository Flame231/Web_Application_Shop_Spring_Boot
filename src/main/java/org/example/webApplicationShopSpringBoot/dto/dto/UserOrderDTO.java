package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;


@Builder
@Getter
public class UserOrderDTO {

    private Long orderId;

    private OrderStatus orderStatus;

    private String orderPoint;

    private BigDecimal orderSum;

    private Timestamp OrderCreateDateTime;

    private Set<UserOrderProductDTO> userOrderProducts;

}
