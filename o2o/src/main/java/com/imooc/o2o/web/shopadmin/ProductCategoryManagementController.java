package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/shop_admin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/get_product_category_list",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductCategoryList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if(currentShop!=null && currentShop.getShopId()!=null){
            List<ProductCategory> list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            map.put("success",true);
            map.put("productCategoryList",list);
        }else {
            map.put("success",false);
            map.put("errorMsg","店铺id为空 或 找不到店铺id！");
        }
        return map;
    }
    @RequestMapping(value = "/batch_add_product_category", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> batchAddProductCategory(@RequestBody List<ProductCategory> productCategoryList,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for(ProductCategory productCategory:productCategoryList){
            productCategory.setCreateTime(new Date());
            productCategory.setShopId(currentShop.getShopId());
        }
        if(productCategoryList!=null && productCategoryList.size()>0){
            try{
            int i = productCategoryService.batchAddProductCategory(productCategoryList);
            if(i <=0){
                map.put("success",false);
                map.put("errorMsg","商品类别添加失败!");
            }else {
                map.put("success",true);
            }
            }catch (RuntimeException e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","至少输入一个商品类别!");
         }
            return map;
    }
    @RequestMapping(value = "/remove_product_category", method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        if(productCategoryId != null && productCategoryId >= 0){
            try{
                Shop shop = (Shop) request.getSession().getAttribute("currentShop");
                int effectNum = productCategoryService.removeProductCategory(productCategoryId,shop.getShopId());
                if(effectNum <= 0){
                    map.put("success",false);
                    map.put("errorMsg","商品类别删除失败!");
                }else {
                    map.put("success",true);
                }
            }catch (RuntimeException e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","至少选择一个商品类别!");
        }
        return map;
    }

}
