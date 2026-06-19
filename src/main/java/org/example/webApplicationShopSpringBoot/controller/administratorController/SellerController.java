package org.example.webApplicationShopSpringBoot.controller.administratorController;

import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/administrator/editSellers")
    public String showEditSellersPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        Page<SellerDTO> sellerDTOList = sellerService.getSellerDTOList(pageable);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/seller/editSellers";
    }

    @PostMapping("/administrator/deleteSeller/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.removeSeller(id);
        return "redirect:/editSellers";
    }

    @GetMapping("/administrator/addSeller")
    public String showAddSellerPage() {
        return "/superUser/seller/addSellerPage";
    }

    @PostMapping("/administrator/addNewSeller")
    public String addNewSeller(@ModelAttribute SellerDTO sellerDTO) {
        sellerService.addSeller(sellerDTO);
        return "redirect:editSellers";
    }
}
