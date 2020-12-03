package com.imooc.o2o.service;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);
    int batchAddProductCategory(List<ProductCategory> productCategoryList);
    int removeProductCategory (@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);
}
