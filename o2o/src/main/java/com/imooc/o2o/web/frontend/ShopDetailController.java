package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/shop_detail_info")
    @ResponseBody
    private Map<String,Object> shopDetailInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        Shop shop =null;
        List<ProductCategory> productCategoryList = null;
        if(shopId!=-1){
             shop = shopService.queryByShopId(shopId);
             productCategoryList = productCategoryService.getProductCategoryList(shopId);
            map.put("shop",shop);
            map.put("productCategoryList",productCategoryList);
            map.put("success",true);
        }
        else {
            map.put("success",false);
            map.put("errorMsg","empty shopId or pageIndex or pageSize!");
        }
        return map;
    }
    @RequestMapping("/product_list_by_shop")
    @ResponseBody
    private Map<String,Object> productListByShop (HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        if((pageIndex > -1) && (pageSize > -1) && (shopId != -1)){
            String productName = HttpServletRequestUtil.getString(request,"productName");
            Long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            Product productCondition =  compactProductCondition(productName,productCategoryId,shopId);
            ProductExecution productList = productService.getProductList(productCondition, pageIndex, pageSize);
            map.put("productList",productList.getProductList());
            map.put("count",productList.getCount());
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","empty shopId or pageIndex or pageSize!");
        }
        return map;
    }

    private Product compactProductCondition(String productName, Long productCategoryId, Long shopId) {
        Product product = new Product();
        Shop shop =new Shop();
        shop.setShopId(shopId);
        product.setShop(shop);
        if(productName!=null){
            product.setProductName(productName);
        }
        if(productCategoryId!=-1){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }
        return product;
    }
}
