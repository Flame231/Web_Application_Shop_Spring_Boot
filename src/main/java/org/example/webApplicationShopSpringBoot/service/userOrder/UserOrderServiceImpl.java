package org.example.webApplicationShopSpringBoot.service.userOrder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.BagFormDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.exceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointServiceImpl;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserOrderServiceImpl implements UserOrderService {

    @Lazy
    private UserOrderProductService userOrderProductService;
    private UserOrderRepository userOrderRepository;
    private UserOrderConverter userOrderConverter;

    @Override
    public List<UserOrderDTO> showAllUserOrders(User user) {
        List<UserOrder> userOrderList = userOrderRepository.findAllByUserId(user.getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Активные заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> userOrderConverter.toDTO(userOrder))
                .toList();
    }

    @Override
    public List<UserOrderDTO> showReadyUserOrdersByOrderPoint(User user) {
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getReadyUserOrderByOrderPoint(orderPointId);
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Готовые заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> userOrderConverter.toDTO(userOrder)).toList();
    }

    @Override
    public void readyUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderRepository.findById(userOrderId)
                .orElseThrow(() -> new ResourceNotFound("Пользователь не найден!"));
        userOrder.setOrderStatus(OrderStatus.READY);
        userOrderRepository.save(userOrder);
        log.info("Статус заказа с id успешно изменён на {}", userOrder.getOrderStatus());
    }

    @Override
    public List<UserOrderDTO> showCreatedUserOrders(User user) {
        List<UserOrder> userOrderList = userOrderRepository
                .findAllCreatedUserOrder(user.getOrderPoint().getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Заказы \"в пути\" отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> userOrderConverter.toDTO(userOrder)).toList();
    }

    @Override
    public ShowOrderDTO returnOrderInfo(Long id) {
        UserOrderDTO userOrderDTO = userOrderConverter.toDTO(getUserOrder(id));
        BigDecimal UserOrderProductSum = userOrderProductService.showUserOrderProductSum(id);
        return ShowOrderDTO.builder().userOrderDTO(userOrderDTO).UserOrderProductSum(UserOrderProductSum).build();
    }

    public List<OrderDTO> toNewOrderDTO(BagFormDTO bagFormDTO, User user) {
        if (bagFormDTO.getProductId() == null || bagFormDTO.getProductPrice() == null || bagFormDTO.getCount() == null) {
            throw new EmptyList("Данные корзины некорректны!");
        } else if (bagFormDTO.getProductId().size() != bagFormDTO.getProductPrice().size() || bagFormDTO.getProductId().size() != bagFormDTO.getCount().size()) {
            throw new EmptyList("Данные корзины некорректны!");
        }
        List<OrderDTO> list = new ArrayList<>();
        for (int i = 0; i < bagFormDTO.getProductId().size(); i++) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .userId(user.getId()).orderPointId(bagFormDTO.getOrderPointId())
                    .productId(bagFormDTO.getProductId().get(i)).Count(bagFormDTO.getCount().get(i))
                    .productPrice(bagFormDTO.getProductPrice().get(i))
                    .build();
            list.add(orderDTO);
        }
        return list;
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
