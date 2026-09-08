package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

public interface UserOrderProcessingService {

    void closeUserOrder(Long userOrderId);

    void refuseUserOrder(Long userOrderId);

    void createUserOrder(User user, Long orderPointId);

    ShowOrderDTO returnOrderInfo(Long id);

    }
