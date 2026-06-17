package org.example.webApplicationShopSpringBoot.service.seller;

import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;

import java.io.Serializable;
import java.util.List;

public interface SellerService {

    List<SellerDTO> getSellerDTOList();

    void updateSeller(SellerDTO sellerDTO);

    void addSeller(SellerDTO sellerDTO);

    void removeSeller(Long id);

    SellerDTO getSeller(Long id);
}
