package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import org.example.webApplicationShopSpringBoot.dto.dto.BagFormDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

public interface UserOrderProcessingService {

    void closeUserOrder(Long userOrderId);

    void refuseUserOrder(Long userOrderId);

    void createUserOrder(BagFormDTO bagFormDTO, User user);

}
