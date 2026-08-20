package org.example.webApplicationShopSpringBoot.service.orderPoint;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.OrderPointConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
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
