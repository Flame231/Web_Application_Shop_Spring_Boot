package org.example.webApplicationShopSpringBoot.service.orderPoint;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toEntity.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderPointServiceImpl implements OrderPointService {
    private OrderPointRepository orderPointRepository;
    private OrderPointConverter orderPointConverter;

    @Override
    public List<OrderPointDTO> getAllOrderPoints() {
        return orderPointRepository.findAll().stream().map(orderPoint -> orderPointConverter.toDTO(orderPoint))
                .toList();
    }


}
