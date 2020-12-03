package com.imooc.o2o.service;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SHOPCATEGORYLIST = "shopcategory";
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);


}
