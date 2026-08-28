package org.example.webApplicationShopSpringBoot.service.bag;


import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.BagInfoDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.math.BigDecimal;
import java.util.List;

public interface BagService {
    void addProductToBag(BagDTORequest bagDTORequest, User user);

    void deleteProductFromBag(BagDTORequest bagDTORequest, User user);

    List<BagDTOResponse> getAllBags(User user);

    BigDecimal showBagSum(User user);

    BigDecimal showCalculatedDiscount(BigDecimal fullPrice, Integer discountValue);

    BagSumWithDiscountDTO calculateBagSumWithDiscount(User user);

    void clearAllBags(User user);

    List<BagDTOResponse> openBag(User user);

    BagInfoDTO returnBagInfo(User user);
}
