package org.example.webApplicationShopSpringBoot.controller.userController;


import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClientController {

    private ProductService productService;
    private BagService bagService;

    public ClientController(ProductService productService, BagService bagService) {
        this.productService = productService;
        this.bagService = bagService;
    }

    @RequestMapping(value = "/client/accountClient", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountClient";
    }

    @GetMapping("/client/catalog")
    public String showCatalog(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        Page<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        List<BagDTO> bagDTOList = bagService.showAllBags();
                model.addAttribute("bagDTOList", bagDTOList);
        return "/catalog";
    }
}
