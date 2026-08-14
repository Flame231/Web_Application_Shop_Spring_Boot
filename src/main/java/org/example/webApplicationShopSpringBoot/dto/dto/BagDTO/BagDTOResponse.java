package org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;

@ToString
@Builder
@Getter
public class BagDTOResponse {

    private Long userId;

    private ProductDTO productDTO;

    private Long count;

    @Override
    public String toString() {
        return "BagDTOResponse{" +
                "userId=" + userId +
                ", productDTO=" + productDTO +
                ", count=" + count +
                '}';
    }
}