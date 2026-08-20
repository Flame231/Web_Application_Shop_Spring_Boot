package org.example.webApplicationShopSpringBoot.service.discount;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.discount.DiscountRepository;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private DiscountRepository discountRepository;

    @Override
    public List<Discount> getDiscountList() {
        return discountRepository.findAll();
    }

    @Transactional
    public void checkUserDiscount(User user) {
        BigDecimal sumOfPurchases = user.getSumOfPurchases();
        List<Discount> discountList = getDiscountList();
        for (Discount discountFormList : discountList) {
            if (sumOfPurchases.compareTo(discountFormList.getTotalSum()) >= 0) {
                user.setDiscount(discountFormList);
            }
        }
    }
}
