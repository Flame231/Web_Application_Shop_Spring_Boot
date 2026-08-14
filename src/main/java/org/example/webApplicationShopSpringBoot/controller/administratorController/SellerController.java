package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("${adminPath}")
@AllArgsConstructor
public class SellerController {
    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private SellerService sellerService;

    @RequestMapping(value = "editSellers", method = {RequestMethod.GET, RequestMethod.POST})
    public String showEditSellersPage(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<SellerDTO> sellerDTOList = sellerService.getSellerDTOList(pageable);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/seller/editSellers";
    }

    @PostMapping("deleteSeller/{id}")
    public String deleteSeller(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sellerService.deleteSeller(id);
        redirectAttributes.addFlashAttribute("successMessage", "Продавец успешно удалён!");
        return "redirect:/administrator/editSellers";
    }

    @PostMapping("recoverSeller/{id}")
    public String recoverSeller(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sellerService.recoverSeller(id);
        redirectAttributes.addFlashAttribute("successMessage", "Продавец успешно восстановлен!");
        return "redirect:/administrator/editSellers";
    }

    @GetMapping("addSeller")
    public String showAddSellerPage() {
        return "/superUser/seller/addSellerPage";
    }

    @PostMapping("addNewSeller")
    public String addNewSeller(@ModelAttribute SellerDTO sellerDTO, RedirectAttributes redirectAttributes) {
        sellerService.addSeller(sellerDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Продавец успешно добавлен!");
        return "redirect:editSellers";
    }

    @GetMapping("editSeller/{id}")
    public String editSeller(@PathVariable Long id, Model model) {
        SellerDTO sellerDTO = sellerService.getSeller(id);
        model.addAttribute(sellerDTO);
        return "/superUser/seller/editSeller";
    }

    @PostMapping("updateSeller")
    public String updateSeller(@ModelAttribute SellerDTO sellerDTO, RedirectAttributes redirectAttributes) {
        sellerService.updateSeller(sellerDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Продавец успешно обновлён!");
        return "redirect:editSellers";
    }
}
