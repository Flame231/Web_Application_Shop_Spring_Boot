package org.example.webApplicationShopSpringBoot.service.seller;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.SellerConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.model.ItemStatus;
import org.example.webApplicationShopSpringBoot.model.Seller;
import org.example.webApplicationShopSpringBoot.repository.seller.SellerRepository;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {

    @InjectMocks
    SellerServiceImpl sellerService;

    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private SellerConverter sellerConverter;

    List<Seller> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
    }

    @Test
    void getSellerDTOList() {
        int page = 1;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        list.add(new Seller());
        list.add(new Seller());
        list.add(new Seller());
        list.add(new Seller());
        Page<Seller> pageList = new PageImpl<>(list, pageable, list.size());
        when(sellerRepository.findAll(any(Pageable.class))).thenReturn(pageList);
        when(sellerConverter.toDTO(any(Seller.class))).thenReturn(new SellerDTO());
        PageResponse<SellerDTO> pageResponse = sellerService.getSellerDTOList(page, pageSize);
        assertEquals(PageResponse.class, pageResponse.getClass());
        assertEquals(list.size(), pageResponse.content().size());
    }


    @Test
    void testGetSellerDTOList() {
        list.add(new Seller());
        list.add(new Seller());
        list.add(new Seller());
        list.add(new Seller());
        when(sellerRepository.findAll()).thenReturn(list);
        when(sellerConverter.toDTO(any(Seller.class))).thenReturn(new SellerDTO());
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        assertEquals(SellerDTO.class, sellerDTOList.getFirst().getClass());
        assertEquals(list.size(), sellerDTOList.size());
    }

    @Test
    void getActiveSellerDTOList() {
        List<Seller> list = new ArrayList<>();
        Seller firstActiveSeller = new Seller();
        firstActiveSeller.setStatus(ItemStatus.ACTIVE);
        Seller secondActiveSeller = new Seller();
        secondActiveSeller.setStatus(ItemStatus.ACTIVE);
        list.add(firstActiveSeller);
        list.add(secondActiveSeller);
        when(sellerRepository.findAll(ItemStatus.ACTIVE)).thenReturn(list);
        when(sellerConverter.toDTO(any(Seller.class))).thenReturn(new SellerDTO());
        assertEquals(2, sellerService.getActiveSellerDTOList().size());
    }

    @Test
    void updateSeller() {
        Seller seller = new Seller();
        seller.setSellerName("Вася");
        seller.setId(2L);
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerName("Петя");
        sellerDTO.setId(2L);
        Optional<Seller> optional = Optional.of(seller);
        when(sellerRepository.findById(2L)).thenReturn(optional);
        when(sellerConverter.updateSeller(sellerDTO, seller)).thenAnswer(e -> {
            sellerDTO.setSellerName(seller.getSellerName());
            return seller;
        });
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        sellerService.updateSeller(sellerDTO);
        verify(sellerRepository, times(1)).save(seller);
        assertEquals(sellerDTO.getSellerName(), seller.getSellerName());
    }

    @Test
    void addSeller() {
        SellerDTO sellerDTO = new SellerDTO();
        when(sellerConverter.toEntity(sellerDTO)).thenReturn(new Seller());
        sellerService.addSeller(sellerDTO);
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }

    @Test
    void deleteSeller() {
        Seller seller = new Seller();
        seller.setId(2L);
        seller.setStatus(ItemStatus.ACTIVE);
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        sellerService.deleteSeller(seller.getId());
        verify(sellerRepository, times(1)).save(seller);
        verify(sellerRepository, times(1)).findById(seller.getId());
        assertEquals(ItemStatus.DELETED, seller.getStatus());
    }

    @Test
    void recoverSeller() {
        Seller seller = new Seller();
        seller.setId(2L);
        seller.setStatus(ItemStatus.DELETED);
        when(sellerRepository.findById(any(Long.class))).thenReturn(Optional.of(seller));
        sellerService.recoverSeller(seller.getId());
        verify(sellerRepository, times(1)).save(seller);
        verify(sellerRepository, times(1)).findById(seller.getId());
        assertEquals(ItemStatus.ACTIVE, seller.getStatus());
    }

    @Test
    void getSeller() {
        Long id = 2L;
        Seller seller = new Seller();
        seller.setId(id);
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(id);
        when(sellerRepository.findById(id)).thenReturn(Optional.of(seller));
        when(sellerConverter.toDTO(seller)).thenReturn(sellerDTO);
        SellerDTO returnedSellerDTO = sellerService.getSellerDTO(id);
        verify(sellerRepository, times(1)).findById(id);
        assertEquals(SellerDTO.class, returnedSellerDTO.getClass());
        assertNotNull(returnedSellerDTO);
        assertEquals(id, returnedSellerDTO.getId());
    }
}