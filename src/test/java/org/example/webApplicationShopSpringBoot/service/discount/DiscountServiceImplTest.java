package org.example.webApplicationShopSpringBoot.service.discount;

import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.repository.discount.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @InjectMocks
    DiscountServiceImpl discountServiceImpl;
    @Mock
    private DiscountRepository discountRepository;
    List<Discount> list;

    @BeforeEach
    void setUp() {
        Discount discount = new Discount(1, new BigDecimal(100000), null);
        Discount discount2 = new Discount(2, new BigDecimal(200000), null);
        list = List.of(discount, discount2);
    }

    @Test
    void getDiscountList() {
        when(discountRepository.findAll()).thenReturn(list);
        assertEquals(2, discountServiceImpl.getDiscountList().size());
    }

    @Test
    void checkUserDiscount() {
        User user = new User();
        user.setDiscount(list.get(0));
        user.setSumOfPurchases(new BigDecimal(220000));
        when(discountRepository.findAll()).thenReturn(list);
        discountServiceImpl.checkUserDiscount(user);
        assertEquals(list.get(1), user.getDiscount());
    }
}