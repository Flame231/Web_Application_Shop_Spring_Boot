package org.example.webApplicationShopSpringBoot.service.bag;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private BagRepository bagRepository;
    private ProductRepository productRepository;
    private ConversionService conversionService;

    @Override
    public void addProductToBag(BagDTORequest bagDTORequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0L).build());
        Long currentCount = bag.getCount();
        bag.setCount(++currentCount);
        bagRepository.save(bag);

    }

    @Override
    public void deleteProductFromBag(BagDTORequest bagDTORequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    }

    public List<BagDTOResponse> showAllBags() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Bag> bagList = bagRepository.getBagList(user.getId());
        return bagList.stream().map(bag -> conversionService.convert(bag, BagDTOResponse.class)).toList();
    }

    @Override
    public BigDecimal showBagSum() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Calculate.calculateSum(bagRepository.getBagList(user.getId()).stream());
    }

    @Override
    public void clearAllBags() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bagRepository.deleteAllByUserId(user.getId());
    }
}
