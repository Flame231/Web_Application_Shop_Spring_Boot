package org.example.webApplicationShopSpringBoot.service.bag;


import jakarta.servlet.http.HttpServletRequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;

import java.util.List;

public interface BagService {
    void addProductToBag(BagDTO bagDTO);

    /*List<BagDTO> showAllBags(HttpServletRequest request);*/

}
