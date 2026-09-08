package org.example.webApplicationShopSpringBoot.service.userOrder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderConverter userOrderConverter;
    private final UserOrderRepository userOrderRepository;

    @Override
    public List<UserOrderDTO> showAllUserOrders(User user) {
        List<UserOrder> userOrderList = userOrderRepository.findAllByUserId(user.getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Активные заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrderConverter::toDTO)
                .toList();
    }

    @Override
    public List<UserOrderDTO> showReadyUserOrdersByOrderPoint(User user) {
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getReadyUserOrderByOrderPoint(orderPointId);
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Готовые заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrderConverter::toDTO).toList();
    }

    @Override
    public void readyUserOrder(Long userOrderId) {
        UserOrder userOrder = getUserOrder(userOrderId);
        userOrder.setOrderStatus(OrderStatus.READY);
        userOrderRepository.save(userOrder);
        log.info("Статус заказа с id {} успешно изменён на {}", userOrder.getId(), userOrder.getOrderStatus());
    }

    @Override
    public List<UserOrderDTO> showCreatedUserOrders(User user) {
        List<UserOrder> userOrderList = userOrderRepository
                .findAllCreatedUserOrder(user.getOrderPoint().getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Заказы \"в пути\" отсутствуют!");
        }
        return userOrderList.stream().map(userOrderConverter::toDTO).toList();
    }

    @Override
    public UserOrder getUserOrder(Long id) {
        return userOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Заказ не найден!"));
    }

    @Override
    public void deleteUserOrder(Long id) {
        userOrderRepository.deleteById(id);
    }

    @Override
    public UserOrder createUserOrder(User user, OrderPoint orderPoint) {
        return UserOrder.builder().orderStatus(OrderStatus.CREATED)
                .user(user)
                .orderPoint(orderPoint)
                .build();
    }
}
