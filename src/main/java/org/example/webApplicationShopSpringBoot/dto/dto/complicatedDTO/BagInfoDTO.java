package org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class BagInfoDTO {

    private List<BagDTOResponse> bagDTOResponseList;

    private BagSumWithDiscountDTO bagSum;

    private List<OrderPointDTO> orderPointDTOList;

}
