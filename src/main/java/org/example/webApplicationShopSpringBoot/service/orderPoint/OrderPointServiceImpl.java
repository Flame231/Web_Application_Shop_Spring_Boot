package org.example.webApplicationShopSpringBoot.service.orderPoint;

import org.example.webApplicationShopSpringBoot.dao.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.OrderPointDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;

import java.util.List;

public class OrderPointServiceImpl implements OrderPointService {

    private OrderPointRepository orderPointDAO;
    private UserOrderRepository userOrderDAO;

    @Override
    public List<OrderPointDTO> getAllOrderPoints() {
        ConverterDTO<OrderPoint, OrderPointDTO> converterDTO = new OrderPointDTOConverter();
        return orderPointDAO.findAll().stream().map(converterDTO::toDTO)
                .toList();
    }


}
