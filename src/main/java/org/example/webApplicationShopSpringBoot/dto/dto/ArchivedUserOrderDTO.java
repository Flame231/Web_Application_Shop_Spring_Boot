package org.example.webApplicationShopSpringBoot.dto.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Builder
@Getter
public class ArchivedUserOrderDTO {

    private Long userOrderId;

    private OrderStatus orderStatus;

    private Long userId;

    private String orderPoint;

    private BigDecimal orderSum;

    private BigDecimal finalOrderSum;

    private Timestamp userOrderCreateDateTime;

    private Timestamp createDateTime;

    private Set<ArchivedUserOrderProductDTO> archivedUserOrderProducts;

    @Override
    public String toString() {
        return "ArchivedUserOrderDTO{" +
                "userOrderId=" + userOrderId +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                ", orderPoint='" + orderPoint + '\'' +
                ", orderSum=" + orderSum +
                ", finalOrderSum=" + finalOrderSum +
                ", userOrderCreateDateTime=" + userOrderCreateDateTime +
                ", createDateTime=" + createDateTime +
                '}';
    }
}
