package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

import java.io.InputStream;

public interface ShopService {

    ShopExecution addShop(Shop shop,  ImageHolder imageHolder);

    int updateShop(Shop shop);

    Shop queryByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder);

    ShopExecution getShopList(Shop shopCondition ,int pageIndex ,int pageSize);
}
