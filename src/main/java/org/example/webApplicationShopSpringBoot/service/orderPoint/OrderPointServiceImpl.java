package org.example.webApplicationShopSpringBoot.service.orderPoint;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderPointServiceImpl implements OrderPointService {

    private final OrderPointRepository orderPointRepository;
    private final OrderPointConverter orderPointConverter;

    @Override
    public List<OrderPointDTO> getAllOrderPoints() {
        return orderPointRepository.findAll().stream().map(orderPointConverter::toDTO)
                .toList();
    }

    @Override
    public OrderPoint getOrderPoint(Long orderPointId) {
        return orderPointRepository.findById(orderPointId).orElseThrow(() -> new ResourceNotFound("Пункт выдачи заказов не найден!"));
    }

}
