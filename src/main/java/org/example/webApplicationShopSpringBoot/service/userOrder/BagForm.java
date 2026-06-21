package org.example.webApplicationShopSpringBoot.service.userOrder;

import lombok.Setter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Setter
public class BagForm {
    private Long orderPointId;
    private List<Long> productId;
    private List<BigDecimal> productPrice;
    private List<Long> count;

    public List<OrderDTO> toNewOrderDTO() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDTO> list = new ArrayList<>();
        for (int i = 0; i < this.productId.size(); i++) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .userId(user.getId()).orderPointId(this.orderPointId)
                    .productId(this.productId.get(i)).Count(this.count.get(i))
                    .productPrice(this.productPrice.get(i))
                    .build();
            list.add(orderDTO);
        }
        return list;
    }
}

