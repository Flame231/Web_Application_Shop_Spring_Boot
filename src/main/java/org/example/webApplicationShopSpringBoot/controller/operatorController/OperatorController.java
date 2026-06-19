package org.example.webApplicationShopSpringBoot.controller.operatorController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OperatorController {

    @RequestMapping(value = "/operator/accountOperator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountOperator";
    }
}
