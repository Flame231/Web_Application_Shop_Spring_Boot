package org.example.webApplicationShopSpringBoot.dto.dto.bagDTO;

import lombok.*;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BagDTOResponse {

    private Long userId;

    private ProductDTO productDTO;

    private Long count;
}