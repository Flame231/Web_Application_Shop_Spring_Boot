package org.example.webApplicationShopSpringBoot.dto.dto.bagDTO;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BagDTORequest {

    private Long userId;

    private Long productId;

    private Long count;
}