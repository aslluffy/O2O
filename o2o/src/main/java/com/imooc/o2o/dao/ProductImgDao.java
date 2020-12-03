package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);


    /**
     * 根据商品id删除所有商品详情图片
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);


    /**
     * 根据商品id获取该商品的所有详情图
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgListByProductId(long productId);


}
