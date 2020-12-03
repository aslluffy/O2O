package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;

import java.util.List;

public interface ProductService {
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    int addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList);

    int modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList);


    /**
     * 根据productId获取指定商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);
}
