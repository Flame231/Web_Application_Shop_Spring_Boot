package org.example.webApplicationShopSpringBoot.service.seller;

import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.example.webApplicationShopSpringBoot.service.PageResponse;

import java.util.List;

public interface SellerService {

    PageResponse<SellerDTO> getSellerDTOList(int page,  int pageSize);

    List<SellerDTO> getSellerDTOList();

    List<SellerDTO> getActiveSellerDTOList();

    void updateSeller(SellerDTO sellerDTO);

    void addSeller(SellerDTO sellerDTO);

    void deleteSeller(Long id);

    void recoverSeller(Long id);

    Seller getSeller(Long id);

    SellerDTO getSellerDTO(Long id);
}
