package org.example.webApplicationShopSpringBoot.service.orderPoint;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.service.exceptions.ResourceNotFound;
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

    public OrderPoint getOrderPoint(Long orderPointId) {
        return orderPointRepository.findById(orderPointId).orElseThrow(() -> new ResourceNotFound("Пункт выдачи заказов не найден!"));
    }

}
