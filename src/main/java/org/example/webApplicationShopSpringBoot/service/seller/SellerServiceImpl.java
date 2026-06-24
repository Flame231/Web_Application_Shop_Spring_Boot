package org.example.webApplicationShopSpringBoot.service.seller;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;
    private ConversionService conversionService;

    public Page<SellerDTO> getSellerDTOList(Pageable pageable) {
        Page<Seller> page = sellerRepository.findAll(pageable);
        return page.map(seller -> conversionService.convert(seller, SellerDTO.class));
    }

    public List<SellerDTO> getSellerDTOList() {
        return sellerRepository.findAll().stream().map(seller -> conversionService.convert(seller, SellerDTO.class)).toList();
    }

    @Override
    public void updateSeller(SellerDTO sellerDTO) {
        Seller seller = conversionService.convert(sellerDTO,Seller.class);
        sellerRepository.save(seller);
    }

    @Override
    public void addSeller(SellerDTO sellerDTO) {
        Seller seller = conversionService.convert(sellerDTO, Seller.class);
        sellerRepository.save(seller);
    }

    @Override
    public void removeSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        return conversionService.convert(seller,SellerDTO.class);
    }
}
