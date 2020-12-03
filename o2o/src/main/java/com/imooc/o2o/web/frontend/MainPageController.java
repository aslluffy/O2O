package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 初始化前端展示系统的主页信息，包括获取一级ShopCategory列表、可用头条信息
     *
     * @return
     */
    @RequestMapping("/main_page_info_list")
    @ResponseBody
    private Map<String,Object> mainPageInfoList(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(null);
            map.put("shopCategoryList",shopCategoryList);
            HeadLine line = new HeadLine();
            line.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.queryHeadLine(line);
            map.put("headLineList",headLineList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }
        map.put("success",true);
        return map;
    }
}
