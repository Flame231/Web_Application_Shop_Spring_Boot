package org.example.webApplicationShopSpringBoot.service.bag;


import jakarta.servlet.http.HttpServletRequest;
import org.example.webApplicationShopSpringBoot.dao.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.BagDTOConverter;
import org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import java.util.List;

public class BagServiceImpl implements BagService {
    private BagRepository bagDAO;
    private ConverterDTO<Bag, BagDTO> converterDTO = new BagDTOConverter();

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

  /*  public List<BagDTO> showAllBags(HttpServletRequest request) {
        ConverterPost converterPost = new ConverterPost(request);
        Integer userId = converterPost.convertSessionAttribute("userId");
        List<Bag> bagList = bagDAO.getBagList(userId);
        return bagList.stream().map(converterDTO::toDTO).toList();
    }*/

}
