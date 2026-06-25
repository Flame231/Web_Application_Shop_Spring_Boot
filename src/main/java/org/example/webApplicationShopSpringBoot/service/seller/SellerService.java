package org.example.webApplicationShopSpringBoot.service.seller;

import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {

    PageResponse<SellerDTO> getSellerDTOList(Pageable pageable);

    List<SellerDTO> getSellerDTOList();

    void updateSeller(SellerDTO sellerDTO);

    void addSeller(SellerDTO sellerDTO);

    void removeSeller(Long id);

    SellerDTO getSeller(Long id);
}
