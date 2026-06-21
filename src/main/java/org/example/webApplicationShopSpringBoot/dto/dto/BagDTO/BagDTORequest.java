package org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class BagDTORequest {

    private Long userId;

    private Long productId;

    private Integer count;
}