package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Person;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop_admin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/get_shop_by_id",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId > -1){
            try {
                Shop shop = shopService.queryByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("success", true);
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/get_shop_info",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitinfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
      try {
          shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
          areaList = areaService.getAreaList();
          modelMap.put("shopCategoryList",shopCategoryList);
          modelMap.put("areaList",areaList);
          modelMap.put("success",true);
      }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
      }
      return modelMap;
    }
    @RequestMapping(value = "/register_shop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入错误验证码");
            return modelMap;
        }
        //1:接受并转换参数，包括店铺以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop =null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg =null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
        }
        //2:注册店铺
        if(shop!=null &&shopImg!=null){
            try {
                Person user = new Person();
                user.setUserId(2L);
                shop.setOwner(user);
                ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
                ShopExecution se = shopService.addShop(shop,imageHolder);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    // 若shop创建成功，则加入session中，作为权限使用
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>) request.getSession()
                            .getAttribute("shopList");
                    if (shopList != null && shopList.size() > 0) {
                        shopList.add(se.getShop());
                        request.getSession().setAttribute("shopList", shopList);
                    } else {
                        shopList = new ArrayList<Shop>();
                        shopList.add(se.getShop());
                        request.getSession().setAttribute("shopList", shopList);
                    }
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺相关信息");
        }
        //3:返回结果
        return modelMap;
    }
    @RequestMapping(value = "/modify_shop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入错误验证码");
            return modelMap;
        }
        //1:接受并转换参数，包括店铺以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop =null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg =null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        if(shop!=null &&shop.getShopId()!=null){
                ShopExecution se ;
            try {
                if(shopImg == null){
                     se = shopService.modifyShop(shop,null);
                }else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
                   se = shopService.modifyShop(shop,imageHolder);
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID");
        }
        //3:返回结果
        return modelMap;
    }
    @RequestMapping(value = "/get_shop_list",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Person user = new Person();
        user.setUserId(1L);
        request.getSession().setAttribute("person",user);
         user = (Person) request.getSession().getAttribute("person");
        try{
            Shop shop = new Shop();
            shop.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shop, 0, 100);
            map.put("shopList",shopExecution.getShopList());
            map.put("user",user);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
        }
        return map;
    }
    @RequestMapping(value = "/get_shop_management_info",method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                map.put("redirect",true);
                map.put("url","/o2o/shop_admin/shop_list");
            }else {
                Shop currentShop = (Shop) currentShopObj;
                map.put("redirect", false);
                map.put("shopId", currentShop.getShopId());
            }
        }else {
            //将shop添加到session中
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            map.put("redirect", false);
        }
        return map;
    }

}
