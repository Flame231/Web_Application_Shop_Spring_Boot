package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

public interface UserOrderProcessingService {

    void closeUserOrder(Long userOrderId, User operator);

    void refuseUserOrder(Long userOrderId, User operator);

    void createUserOrder(User user, Long orderPointId);

    ShowOrderDTO returnOrderInfo(Long id, User operator);

    }
