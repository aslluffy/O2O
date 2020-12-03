package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shop_admin" ,method = RequestMethod.GET)
public class ShopAdminController {
    @RequestMapping(value = "/shop_operation")
    public String shopOperation(){
        return "shop/shop_operation";
    }
    @RequestMapping(value = "/shop_list")
    public String shopList(){
        return "shop/shop_list";
    }
    @RequestMapping(value = "/shop_management")
    public String shopManagement(){
        return "shop/shop_management";
    }
    @RequestMapping(value = "/product_category_management")  //路由
    public String productCategoryManagement(){
        return "shop/product_category_management";
    }
    @RequestMapping(value = "/product_operation")  //路由
    public String productOperation(){
        return "shop/product_operation";
    }
    @RequestMapping(value = "/product_management")  //路由
    public String productManagement(){
        return "shop/product_management";
    }
    @RequestMapping(value = "/ownerlogin")  //路由
    public String ownerlogin(){
        return "shop/ownerlogin";
    }
    @RequestMapping(value = "/register")  //路由
    public String register(){
        return "shop/register";
    }


}
