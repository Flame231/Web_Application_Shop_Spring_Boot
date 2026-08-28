package org.example.webApplicationShopSpringBoot.service.bag;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.BagConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.BagInfoDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BagServiceImpl implements BagService {
    private BagRepository bagRepository;
    private ProductRepository productRepository;
    private BagConverter bagConverter;
    private OrderPointService orderPointService;

    @Override
    public void addProductToBag(BagDTORequest bagDTORequest, User user) {

        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0L).build());
        Long currentCount = bag.getCount();
        bag.setCount(++currentCount);
        bagRepository.save(bag);
        log.info("Значение продукта с id {} успешно увеличено до {} в корзине пользователя с id {}!", product.getId(),bag.getCount(), user.getId());
    }

    @Override
    public void deleteProductFromBag(BagDTORequest bagDTORequest, User user) {
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0L).build());
        Long currentCount = bag.getCount();
        bag.setCount(--currentCount);
        if (currentCount < 1) {
            bagRepository.delete(bag);
        } else {
            bagRepository.save(bag);
            log.info("Значение продукта с id {} успешно уменьшено до {} в корзине пользователя с id {}!", product.getId(),bag.getCount(), user.getId());
        }
    }

    public List<BagDTOResponse> getAllBags(User user) {
        List<Bag> bagList = bagRepository.getBagList(user.getId());
        return bagList.stream().map(bag -> bagConverter.toDTO(bag)).toList();
    }

    @Override
    public BigDecimal showBagSum(User user) {
        return Calculate.calculateSum(bagRepository.getBagList(user.getId()).stream());
    }

    @Override
    public BigDecimal showCalculatedDiscount(BigDecimal fullPrice, Integer discountValue) {
        return fullPrice.multiply(new BigDecimal(discountValue)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public BagSumWithDiscountDTO calculateBagSumWithDiscount(User user) {
        Integer discountValue = user.getDiscount().getDiscount();
        BigDecimal fullPrice = showBagSum(user);
        BigDecimal calculatedDiscount = showCalculatedDiscount(fullPrice, discountValue);
        BigDecimal priceWithDiscount = fullPrice.subtract(calculatedDiscount);
        return BagSumWithDiscountDTO.builder()
                .fullPrice(fullPrice)
                .discountValue(discountValue)
                .calculatedDiscount(calculatedDiscount)
                .priceWithDiscount(priceWithDiscount).build();
    }

    @Override
    public void clearAllBags(User user) {
        bagRepository.deleteAllByUserId(user.getId());
        log.info("Корзина пользователя с id {} успешно очищена!", user.getId());
    }

    @Override
    public List<BagDTOResponse> openBag(User user) {
        List<Bag> bagList = bagRepository.getBagList(user.getId());
        if (bagList.isEmpty()) {
            throw new EmptyList("Корзина пуста!");
        }
        return bagList.stream().map(bag -> bagConverter.toDTO(bag)).toList();
    }

    @Override
    public BagInfoDTO returnBagInfo(User user) {
        List<BagDTOResponse> bagDTOResponseList = openBag(user);
        BagSumWithDiscountDTO bagSum = calculateBagSumWithDiscount(user);
        List<OrderPointDTO> orderPointDTOList = orderPointService.getAllOrderPoints();
        return BagInfoDTO.builder().bagDTOResponseList(bagDTOResponseList).bagSum(bagSum).orderPointDTOList(orderPointDTOList).build();
    }
}
