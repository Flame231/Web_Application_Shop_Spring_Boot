package org.example.webApplicationShopSpringBoot.service.userOrder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dao.orderPoint.OrderPointRepository;
import org.example.webApplicationShopSpringBoot.dao.product.ProductDAOImpl;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserDAOImpl;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductDAO;
import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductDAOImpl;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.UserOrderDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    private static final Logger logger = LogManager.getLogger(UserOrderService.class);

    private UserOrderRepository userOrderRepository;
    private UserRepository userDAO;
    private ConverterDTO<UserOrder, UserOrderDTO> converterDTO = new UserOrderDTOConverter();
    private OrderPointRepository orderPointDAO;
    private BagRepository bagDAO;
    private ProductRepository productDAO = new ProductDAOImpl();
    private UserOrderProductDAO userOrderProductDAO = new UserOrderProductDAOImpl();


    public UserOrderServiceImpl(UserOrderRepository userOrderRepository, UserRepository userDAO, @Qualifier("converterDTO") ConverterDTO<UserOrder, UserOrderDTO> converterDTO, OrderPointRepository orderPointDAO, BagRepository bagDAO, @Qualifier("productDAO") ProductRepository productDAO,@Qualifier("userOrderProductDAO") UserOrderProductDAO userOrderProductDAO) {
        this.userOrderRepository = userOrderRepository;
        this.userDAO = userDAO;
        this.converterDTO = converterDTO;
        this.orderPointDAO = orderPointDAO;
        this.bagDAO = bagDAO;
        this.productDAO = productDAO;
        this.userOrderProductDAO = userOrderProductDAO;
    }

    @Override
    public void confirmOrder(List<OrderDTO> list) {




        BigDecimal orderSum = BigDecimal.ZERO;
        UserOrder userOrder = UserOrder.builder().orderStatus(OrderStatus.CREATED)
                .user(userDAO.get(list.get(0).getUserId()))
                .orderPoint(orderPointDAO.findById(list.get(0).getOrderPointId()).get())
                .build();
        userOrderRepository.save(userOrder);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCount() != 0) {
                UserOrderProduct userOrderProduct = UserOrderProduct.builder()
                        .userOrder(userOrder).product(productDAO.get(list.get(i).getProductId()))
                        .productCount(list.get(i).getCount()).actualProductCount(list.get(i).getCount()).productPrice(list.get(i).getProductPrice()).build();

                orderSum = orderSum.add(((list.get(i).getProductPrice()).multiply(new BigDecimal(list.get(i).getCount()))));

                userOrderProductDAO.save(userOrderProduct);
                PrimaryKeyBag primaryKeyBag = new PrimaryKeyBag(list.get(i).getUserId(), list.get(i).getProductId());
                bagDAO.deleteById(primaryKeyBag);
            }
        }
        userOrder.setOrderSum(orderSum);
        userOrderRepository.save(userOrder);
    }

    @Override
    public List<UserOrderDTO> showAllUserOrders() {
        ConverterDTO<UserOrder, UserOrderDTO> converterDTO = new UserOrderDTOConverter();
        return userOrderRepository.getUserOrderList().stream().map(converterDTO::toDTO)
                .toList();
    }

    @Override
    public List<UserOrderDTO> showUserOrdersByOrderPoint(Long userId) {
        User user = userDAO.get(userId);
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getUserOrderByOrderPoint(orderPointId);
        return userOrderList.stream().map(converterDTO::toDTO).toList();
    }

    @Override
    public List<UserOrderDTO> showArrivedUserOrdersByOrderPoint(Long userId) {
        User user = userDAO.get(userId);
        Long orderPointId = user.getOrderPoint().getId();
        List<UserOrder> userOrderList = userOrderRepository.getArrivedUserOrderByOrderPoint(orderPointId);
        return userOrderList.stream().map(converterDTO::toDTO).toList();
    }

    @Override
    public UserOrderDTO getUserOrderDTO(Long id) {
        UserOrder userOrder = userOrderRepository.findById(id).get();
        return converterDTO.toDTO(userOrder);
    }

}
