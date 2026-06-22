package org.example.webApplicationShopSpringBoot.controller.administratorController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.example.webApplicationShopSpringBoot.controller.ControllerUtil.ADMINISTRATOR_PREFIX;

@Controller
public class AccountAdministratorController {


    @RequestMapping(value = ADMINISTRATOR_PREFIX + "accountAdministrator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountAdministrator";
    }


}
