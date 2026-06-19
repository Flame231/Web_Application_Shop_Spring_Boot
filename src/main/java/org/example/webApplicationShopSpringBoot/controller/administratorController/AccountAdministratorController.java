package org.example.webApplicationShopSpringBoot.controller.administratorController;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountAdministratorController {


    @RequestMapping(value = "/administrator/accountAdministrator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountAdministrator";
    }


}
