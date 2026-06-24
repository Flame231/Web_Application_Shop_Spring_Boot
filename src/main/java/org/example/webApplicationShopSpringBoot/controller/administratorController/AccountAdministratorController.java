package org.example.webApplicationShopSpringBoot.controller.administratorController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("${adminPath}")
public class AccountAdministratorController {

    @RequestMapping(value ="accountAdministrator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountAdministrator";
    }


}
