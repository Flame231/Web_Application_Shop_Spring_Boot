package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.webApplicationShopSpringBoot.model.UserOrder.OrderStatus;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Builder
@Getter
public class ArchivedUserOrderDTO {

    private Long UserOrderId;

    private OrderStatus orderStatus;

    private Long userId;

    private String OrderPoint;

    private BigDecimal orderSum;

    private BigDecimal finalOrderSum;

    private Timestamp userOrderCreateDateTime;

    private Timestamp createDateTime;

    private Set<ArchivedUserOrderProductDTO> archivedUserOrderProducts;

}
