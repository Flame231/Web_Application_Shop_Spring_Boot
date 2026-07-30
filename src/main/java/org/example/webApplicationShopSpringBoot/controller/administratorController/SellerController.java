package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${adminPath}")
@AllArgsConstructor
public class SellerController {
    private SellerService sellerService;

    @RequestMapping(value = "editSellers", method = {RequestMethod.GET, RequestMethod.POST})
    public String showEditSellersPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("id").ascending());
        PageResponse<SellerDTO> sellerDTOList = sellerService.getSellerDTOList(pageable);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/seller/editSellers";
    }

    @PostMapping("deleteSeller/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.removeSeller(id);
        return "redirect:/administrator/editSellers";
    }

    @GetMapping("addSeller")
    public String showAddSellerPage() {
        return "/superUser/seller/addSellerPage";
    }

    @PostMapping("addNewSeller")
    public String addNewSeller(@ModelAttribute SellerDTO sellerDTO) {
        sellerService.addSeller(sellerDTO);
        return "redirect:editSellers";
    }

    @PostMapping("editSeller/{id}")
    public String editSeller(@PathVariable Long id, Model model) {
        SellerDTO sellerDTO = sellerService.getSeller(id);
        model.addAttribute(sellerDTO);
        return "/superUser/seller/editSeller";
    }

    @PostMapping("updateSeller")
    public String updateSeller(@ModelAttribute SellerDTO sellerDTO){
        sellerService.updateSeller(sellerDTO);
        return "redirect:editSellers";
    }
}
