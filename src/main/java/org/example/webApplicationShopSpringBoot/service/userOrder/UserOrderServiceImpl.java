package org.example.webApplicationShopSpringBoot.service.userOrder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dto.dto.BagFormDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.exceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.example.webApplicationShopSpringBoot.service.PrincipalProvider.getUserFromSecurityContext;

@Service
@AllArgsConstructor
@Slf4j
public class UserOrderServiceImpl implements UserOrderService {

    private static final Logger logger = LogManager.getLogger(UserOrderService.class);

    private UserOrderRepository userOrderRepository;
    private UserRepository userRepository;
    private OrderPointRepository orderPointRepository;
    private BagRepository bagRepository;
    private ProductRepository productRepository;
    private UserOrderProductRepository userOrderProductRepository;
    private UserOrderConverter userOrderConverter;
    private UserOrderProductService userOrderProductService;

    @Override
    @Transactional
    public void confirmOrder(BagFormDTO bagFormDTO, User user) {
        List<OrderDTO> list = toNewOrderDTO(bagFormDTO, user);
        if (list.isEmpty()) {
            throw new EmptyList("Корзина товаров пуста!");
        }
        User managedUser = userRepository.findById(list.get(0).getUserId()).get();
        BigDecimal orderSum = BigDecimal.ZERO;
        UserOrder userOrder = UserOrder.builder().orderStatus(OrderStatus.CREATED)
                .user(managedUser)
                .orderPoint(orderPointRepository.findById(list.get(0).getOrderPointId()).get())
                .build();
        userOrderRepository.save(userOrder);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCount() != 0) {
                UserOrderProduct userOrderProduct = UserOrderProduct.builder()
                        .userOrder(userOrder).product(productRepository.findById(list.get(i).getProductId()).get())
                        .productCount(list.get(i).getCount()).actualProductCount(list.get(i).getCount()).productPrice(list.get(i).getProductPrice()).build();
                orderSum = orderSum.add(((list.get(i).getProductPrice()).multiply(new BigDecimal(list.get(i).getCount()))));
                userOrderProductRepository.save(userOrderProduct);
                PrimaryKeyBag primaryKeyBag = new PrimaryKeyBag(list.get(i).getUserId(), list.get(i).getProductId());
                bagRepository.deleteById(primaryKeyBag);
            }
        }
        Integer discountValue = managedUser.getDiscount().getDiscount();
        BigDecimal calculatedDiscount = orderSum.multiply(new BigDecimal(discountValue)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal orderSumWithDiscount = orderSum.subtract(calculatedDiscount);
        userOrder.setOrderSum(orderSumWithDiscount);
        userOrderRepository.save(userOrder);
        log.info("Заказ с id {} для пользователя с id {} успешно создан!", managedUser.getId(), userOrder.getId());
    }

    @Override
    public List<UserOrderDTO> showAllUserOrders() {
        User user = getUserFromSecurityContext();
        List<UserOrder> userOrderList = userOrderRepository.findAllByUserId(user.getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Активные заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> userOrderConverter.toDTO(userOrder))
                .toList();
    }

    @Override
    public List<UserOrderDTO> showReadyUserOrdersByOrderPoint() {
        User user = getUserFromSecurityContext();
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getReadyUserOrderByOrderPoint(orderPointId);
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Готовые заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> userOrderConverter.toDTO(userOrder)).toList();
    }

    @Override
    public UserOrderDTO getUserOrderDTO(Long id) {
        UserOrder userOrder = userOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Заказ не найден!"));
        return userOrderConverter.toDTO(userOrder);
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
        UserOrderDTO userOrderDTO = getUserOrderDTO(id);
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
}
