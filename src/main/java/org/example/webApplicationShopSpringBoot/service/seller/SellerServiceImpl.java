package org.example.webApplicationShopSpringBoot.service.seller;


import org.example.webApplicationShopSpringBoot.dao.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.SellerDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerDAO;
    private ConverterDTO<Seller, SellerDTO> converterDTO;

    public SellerServiceImpl(SellerRepository sellerDAO, ConverterDTO<Seller, SellerDTO> converterDTO) {
        this.sellerDAO = sellerDAO;
        this.converterDTO = converterDTO;
    }

    public List<SellerDTO> getSellerDTOList() {
        ConverterDTO<Seller, SellerDTO> converterDTO = new SellerDTOConverter();
        return sellerDAO.findAll()
                .stream()
                .map(converterDTO::toDTO)
                .toList();
    }

    @Override
    public void updateSeller(SellerDTO sellerDTO) {
        Seller seller = converterDTO.toEntity(sellerDTO);
        sellerDAO.save(seller);
    }

    @Override
    public void addSeller(SellerDTO sellerDTO) {
        Seller seller = converterDTO.toEntity(sellerDTO);
        sellerDAO.save(seller);
    }

    @Override
    public void removeSeller(Long id) {
        sellerDAO.deleteById(id);
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller  = sellerDAO.findById(id).get();
        return converterDTO.toDTO(seller);
    }
}
