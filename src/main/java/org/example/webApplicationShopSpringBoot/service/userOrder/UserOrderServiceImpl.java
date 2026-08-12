package org.example.webApplicationShopSpringBoot.service.userOrder;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dao.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private static final Logger logger = LogManager.getLogger(UserOrderService.class);
    private final ConversionService conversionService;

    private UserOrderRepository userOrderRepository;
    private UserRepository userRepository;
    private OrderPointRepository orderPointRepository;
    private BagRepository bagRepository;
    private ProductRepository productRepository;
    private UserOrderProductRepository userOrderProductRepository;

    @Override
    public void confirmOrder(List<OrderDTO> list) {
        System.out.println(list.size());
        if (list.isEmpty()){
            throw new EmptyList("Корзина товаров пуста!");
        }
            BigDecimal orderSum = BigDecimal.ZERO;
        UserOrder userOrder = UserOrder.builder().orderStatus(OrderStatus.CREATED)
                .user(userRepository.findById(list.get(0).getUserId()).get())
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
        userOrder.setOrderSum(orderSum);
        userOrderRepository.save(userOrder);
    }

    @Override
    public List<UserOrderDTO> showAllUserOrders() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserOrderDTO> userOrderList = userOrderRepository.findAllByUserId(user.getId()).stream().map(userOrder -> conversionService.convert(userOrder, UserOrderDTO.class))
                .toList();
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Активные заказы отсутствуют!");
        }
        return userOrderList;
    }

    @Override
    public List<UserOrderDTO> showUserOrdersByOrderPoint() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.findAllByOrderPointId(orderPointId);
        return userOrderList.stream().map(userOrder -> conversionService.convert(userOrder, UserOrderDTO.class)).toList();
    }

    @Override
    public List<UserOrderDTO> showReadyUserOrdersByOrderPoint() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getReadyUserOrderByOrderPoint(orderPointId);
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Готовые заказы отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> conversionService.convert(userOrder, UserOrderDTO.class)).toList();
    }

    @Override
    public UserOrderDTO getUserOrderDTO(Long id) {
        UserOrder userOrder = userOrderRepository.findById(id).get();
        return conversionService.convert(userOrder, UserOrderDTO.class);
    }


    @Override
    public void readyUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderRepository.findById(userOrderId).get();
        userOrder.setOrderStatus(OrderStatus.READY);
        userOrderRepository.save(userOrder);
    }

    @Override
    public List<UserOrderDTO> showCreatedUserOrders() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserOrder> userOrderList = userOrderRepository
                .findAllCreatedUserOrder(user.getOrderPoint().getId());
        if (userOrderList.isEmpty()) {
            throw new EmptyList("Заказы \"в пути\" отсутствуют!");
        }
        return userOrderList.stream().map(userOrder -> conversionService.convert(userOrder, UserOrderDTO.class)).toList();
    }
}
