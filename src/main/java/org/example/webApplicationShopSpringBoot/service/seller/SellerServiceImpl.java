package org.example.webApplicationShopSpringBoot.service.seller;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity.SellerConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;
    private SellerConverter sellerConverter;

    public PageResponse<SellerDTO> getSellerDTOList(Pageable pageable) {
        Page<Seller> page = sellerRepository.findAll(pageable);
        return new PageResponse<SellerDTO>(page.map(seller -> sellerConverter.toDTO(seller)));
    }

    public List<SellerDTO> getSellerDTOList() {
        return sellerRepository.findAll().stream().map(seller -> sellerConverter.toDTO(seller)).toList();
    }

    public List<SellerDTO> getActiveSellerDTOList() {
        return sellerRepository.findAll(ItemStatus.ACTIVE).stream().map(seller -> sellerConverter.toDTO(seller)).toList();
    }

    @Override
    public void updateSeller(SellerDTO sellerDTO) {
        Seller existingSeller = sellerRepository.findById(sellerDTO.getId()).get();
        Seller seller = sellerConverter.updateSeller(sellerDTO, existingSeller);
        sellerRepository.save(seller);
    }

    @Override
    public void addSeller(SellerDTO sellerDTO) {
        Seller seller = sellerConverter.toEntity(sellerDTO);
        sellerRepository.save(seller);
    }

    @Transactional
    @Override
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        seller.setStatus(ItemStatus.DELETED);
    }

    @Transactional
    @Override
    public void recoverSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        seller.setStatus(ItemStatus.ACTIVE);
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        return sellerConverter.toDTO(seller);
    }
}
