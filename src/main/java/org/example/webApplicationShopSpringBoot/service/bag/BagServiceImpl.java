package org.example.webApplicationShopSpringBoot.service.bag;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.BagSumWithDiscountDTO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.BagConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
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
    private UserRepository userRepository;

    @Override
    public void addProductToBag(BagDTORequest bagDTORequest) {
        User user = PrincipalProvider.getUserFromSecurityContext();
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0L).build());
        Long currentCount = bag.getCount();
        bag.setCount(++currentCount);
        bagRepository.save(bag);
        log.info("Значение продукта с id {} успешно увеличено в корзине пользователя с id {}!", product.getId(), user.getId());
    }

    @Override
    public void deleteProductFromBag(BagDTORequest bagDTORequest) {
        User user = PrincipalProvider.getUserFromSecurityContext();
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0L).build());
        Long currentCount = bag.getCount();
        bag.setCount(--currentCount);
        if (currentCount < 1) {
            bagRepository.delete(bag);
        } else {
            bagRepository.save(bag);
        }
        log.info("Значение продукта с id {} успешно уменьшено в корзине пользователя с id {}!", product.getId(), user.getId());
    }

    public List<BagDTOResponse> getAllBags() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        List<Bag> bagList = bagRepository.getBagList(user.getId());
        return bagList.stream().map(bag -> bagConverter.toDTO(bag)).toList();
    }

    @Override
    public BigDecimal showBagSum() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        User managedUser = userRepository.findById(user.getId()).get();
        return Calculate.calculateSum(bagRepository.getBagList(user.getId()).stream());
    }

    @Override
    public BigDecimal showCalculatedDiscount(BigDecimal fullPrice, Integer discountValue) {
        return fullPrice.multiply(new BigDecimal(discountValue)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public BagSumWithDiscountDTO calculateBagSumWithDiscount() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        User managedUser = userRepository.findById(user.getId()).get();
        Integer discountValue = managedUser.getDiscount().getDiscount();
        BigDecimal fullPrice = showBagSum();
        BigDecimal calculatedDiscount = showCalculatedDiscount(fullPrice, discountValue);
        BigDecimal priceWithDiscount = fullPrice.subtract(calculatedDiscount);
        return BagSumWithDiscountDTO.builder()
                .fullPrice(fullPrice)
                .discountValue(discountValue)
                .calculatedDiscount(calculatedDiscount)
                .priceWithDiscount(priceWithDiscount).build();
    }

    @Override
    public void clearAllBags() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        bagRepository.deleteAllByUserId(user.getId());
        log.info("Корзина пользователя с id {} успешно очищена!", user.getId());
    }

    @Override
    public List<BagDTOResponse> openBag() {
        User user = PrincipalProvider.getUserFromSecurityContext();
        List<Bag> bagList = bagRepository.getBagList(user.getId());
        if (bagList.isEmpty()) {
            throw new EmptyList("Корзина пуста!");
        }
        return bagList.stream().map(bag -> bagConverter.toDTO(bag)).toList();
    }
}
