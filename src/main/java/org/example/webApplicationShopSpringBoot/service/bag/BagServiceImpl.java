package org.example.webApplicationShopSpringBoot.service.bag;


import jakarta.servlet.http.HttpServletRequest;
import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.BagDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagServiceImpl implements BagService {
    private BagRepository bagDAO;
    private ConverterDTO<Bag, BagDTO> converterDTO;

    public BagServiceImpl(BagRepository bagDAO, ConverterDTO<Bag, BagDTO> converterDTO) {
        this.bagDAO = bagDAO;
        this.converterDTO = converterDTO;
    }

    @Override
    public void addProductToBag(BagDTO bagDTO) {
        Bag bag = converterDTO.toEntity(bagDTO);
        PrimaryKeyBag primaryKeyBag = PrimaryKeyUtil.getPrimaryKeyBag(bag.getUser(), bag.getProduct());
        Bag bagManaged = bagDAO.findById(primaryKeyBag).get();
        if (bagManaged != null) {
            int count = bagManaged.getCount() + bag.getCount();
            if (count > 0) {
                bagManaged.setCount(count);
            } else {
                bagDAO.deleteById(primaryKeyBag);
            }
        } else if (bag.getCount() >= 1) {
            bagDAO.save(bag);
        }
    }

    public void closeBag() {
    }

    public List<BagDTO> showAllBags() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Bag> bagList = bagDAO.getBagList(user.getId());
        return bagList.stream().map(converterDTO::toDTO).toList();
    }

}
