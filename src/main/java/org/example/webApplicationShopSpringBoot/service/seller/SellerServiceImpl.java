package org.example.webApplicationShopSpringBoot.service.seller;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.repository.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.SellerConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;
    private SellerConverter sellerConverter;

    public PageResponse<SellerDTO> getSellerDTOList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<Seller> resultPage = sellerRepository.findAll(pageable);
        return new PageResponse<>(resultPage.map(seller -> sellerConverter.toDTO(seller)));
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
        log.info("Продавец с id {} успешно обновлён!", existingSeller.getId());
    }

    @Override
    public void addSeller(SellerDTO sellerDTO) {
        Seller seller = sellerConverter.toEntity(sellerDTO);
        sellerRepository.save(seller);
        log.info("Продавец {} успешно добавлен!", seller.getSellerName());
    }

    @Transactional
    @Override
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        seller.setStatus(ItemStatus.DELETED);
        log.info("Статус категории продукта с id {} успешно изменён!", seller.getStatus().name());
    }

    @Transactional
    @Override
    public void recoverSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        seller.setStatus(ItemStatus.ACTIVE);
        log.info("Статус категории продукта с id {} успешно изменён!", seller.getStatus().name());
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        return sellerConverter.toDTO(seller);
    }
}
