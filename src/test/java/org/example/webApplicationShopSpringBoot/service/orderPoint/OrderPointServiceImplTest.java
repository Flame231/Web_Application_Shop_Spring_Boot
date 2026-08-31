package org.example.webApplicationShopSpringBoot.service.orderPoint;

import org.example.webApplicationShopSpringBoot.dto.converterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPointServiceImplTest {

    @InjectMocks
    OrderPointServiceImpl orderPointService;

    @Mock
    OrderPointRepository orderPointRepository;
    @Mock
    OrderPointConverter orderPointConverter;

    @Test
    void getAllOrderPoints() {
        List<OrderPoint> list = new ArrayList<>();
        list.add(new OrderPoint("Москва, ул. Ленина, д. 10", Time.valueOf("08:00:00"), Time.valueOf("20:00:00"), null, null));
        list.add(new OrderPoint());
        list.add(new OrderPoint());
        when(orderPointRepository.findAll()).thenReturn(list);
        when(orderPointConverter.toDTO(any(OrderPoint.class))).thenReturn(new OrderPointDTO());
        List<OrderPointDTO> orderPointDTOList = orderPointService.getAllOrderPoints();
        assertEquals(OrderPointDTO.class, orderPointDTOList.getFirst().getClass());
        assertEquals(3, orderPointDTOList.size());
    }
}