package org.example.webApplicationShopSpringBoot.service.bag;


import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dao.product.ProductRepository;
import org.example.webApplicationShopSpringBoot.dao.user.UserRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.ConverterDTONew;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BagServiceImpl implements BagService {
    private final MappingsEndpoint mappingsEndpoint;
    private BagRepository bagRepository;
    private ProductRepository productRepository;
    private ConverterDTO<Bag, BagDTOResponse> converterDTO;
    private ConverterDTONew<Bag, BagDTORequest, BagDTOResponse> converterDTONew;

    public BagServiceImpl(BagRepository bagRepository, ProductRepository productRepository, ConverterDTO<Bag, BagDTOResponse> converterDTO, ConverterDTONew<Bag, BagDTORequest, BagDTOResponse> converterDTONew, MappingsEndpoint mappingsEndpoint) {
        this.bagRepository = bagRepository;
        this.productRepository = productRepository;
        this.converterDTO = converterDTO;
        this.converterDTONew = converterDTONew;
        this.mappingsEndpoint = mappingsEndpoint;
    }

    @Override
    public void addProductToBag(BagDTORequest bagDTORequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0).build());
        int currentCount = bag.getCount();
        bag.setCount(++currentCount);
        bagRepository.save(bag);

    }

    @Override
    public void deleteProductFromBag(BagDTORequest bagDTORequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.getReferenceById(bagDTORequest.getProductId());
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(user, product);
        Bag bag = bagRepository.findById(primaryKeyBag).orElse(Bag.builder().user(user).product(product).count(0).build());
        int currentCount = bag.getCount();
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
        return bagList.stream().map(converterDTO::toDTO).toList();
    }

    @Override
    public BigDecimal showBagSum() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BigDecimal bagSum = bagRepository.getBagList(user.getId())
                .stream().map(e -> new BigDecimal(e.getCount()).multiply(e.getProduct().getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return bagSum;
    }

    @Override
    public void clearAllBags() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bagRepository.deleteAllByUserId(user.getId());
    }
}
