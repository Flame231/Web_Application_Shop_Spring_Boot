package org.example.webApplicationShopSpringBoot.service.seller;


import org.example.webApplicationShopSpringBoot.dao.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;
    private ConverterDTO<Seller, SellerDTO> converterDTO;

    public SellerServiceImpl(SellerRepository sellerRepository, ConverterDTO<Seller, SellerDTO> converterDTO) {
        this.sellerRepository = sellerRepository;
        this.converterDTO = converterDTO;
    }

    public Page<SellerDTO> getSellerDTOList(Pageable pageable) {
        Page<Seller> page = sellerRepository.findAll(pageable);
        return page.map(converterDTO::toDTO);
    }

    public List<SellerDTO> getSellerDTOList() {
        return sellerRepository.findAll().stream().map(converterDTO::toDTO).toList();
    }

    @Override
    public void updateSeller(SellerDTO sellerDTO) {
        Seller seller = converterDTO.toEntity(sellerDTO);
        sellerRepository.save(seller);
    }

    @Override
    public void addSeller(SellerDTO sellerDTO) {
        Seller seller = converterDTO.toEntity(sellerDTO);
        sellerRepository.save(seller);
    }

    @Override
    public void removeSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller = sellerRepository.findById(id).get();
        return converterDTO.toDTO(seller);
    }
}
