package org.example.webApplicationShopSpringBoot.service.bag;


import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface BagService {
    void addProductToBag(BagDTORequest bagDTORequest);

    void deleteProductFromBag(BagDTORequest bagDTORequest);

    List<BagDTOResponse> getAllBags();

    BigDecimal showBagSum();

    BigDecimal showCalculatedDiscount(BigDecimal fullPrice, Integer discountValue);

    BagSumWithDiscountDTO calculateBagSumWithDiscount();

    void clearAllBags();

    List<BagDTOResponse> openBag();
}
