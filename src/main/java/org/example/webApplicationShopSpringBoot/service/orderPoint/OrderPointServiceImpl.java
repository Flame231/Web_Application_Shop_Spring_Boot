package org.example.webApplicationShopSpringBoot.service.orderPoint;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderPointServiceImpl implements OrderPointService {
    private ConversionService conversionService;
    private OrderPointRepository orderPointRepository;

    @Override
    public List<OrderPointDTO> getAllOrderPoints() {
        return orderPointRepository.findAll().stream().map(orderPoint -> conversionService.convert(orderPoint, OrderPointDTO.class))
                .toList();
    }


}
