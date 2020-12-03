package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //将页码转换成数据库的行码，
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同样的查询条件下，返回该查询条件下商品的总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductList(productList);
        productExecution.setCount(count);
        return productExecution;
    }


    /**
     * 获取指定商品信息
     *
     * @param productId
     * @return
     */
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);
    }


    /**
     * 更新商品
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    /*
     1.若缩略图参数有值，则处理缩略图，若原先存在缩略图,则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	 3.将product_img下面的该商品原先的商品详情图记录全部清除
	 4.更新product_img以及product的信息
     */
    public int modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) {
        int effectedNum = 0;
        //控制判断
        if(product !=null && product.getShop() != null && product.getShop().getShopId() != null){
            //给商品设置上默认属性
            product.setLastEditTime(new Date());
            //如果传入的商品缩略图不为空，且原有缩略图不为空，则删除原有缩略图，再添加
            if(thumbnail !=null){
                //根据商品id获取商品信息
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                //判断原有商品缩略图是否为空
                if(tempProduct.getImgAddress() !=null){
                    //删除原有的图片
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddress());
                }
                //添加新的商品缩略图
                addThumbnail(product,thumbnail);
            }
            //如果有新存入的商品详情图，则将原有的所有商品详情图删除，然后添加传入的详情图
            if(productImgHolderList !=null && productImgHolderList.size()>0){
                deleteProductImgList(product.getProductId());
                batchAddImageList(product,productImgHolderList);
            }
            try{
                effectedNum = productDao.updateProduct(product);
                if(effectedNum <=0){
                    throw new RuntimeException("更新商品信息失败!");
                }
            }catch (Exception e){
                throw new RuntimeException("更新商品信息失败!" + e.getMessage());
            }
        }
        return effectedNum;
    }


    /**
     * 删除某个商品下的所有详情图（磁盘中存储的物理图片 和 数据库中的图片信息）
     *
     * @param productId
     */
    private void deleteProductImgList(long productId){
        //根据商品id获取所有商品详情图
        List<ProductImg> productImgList = productImgDao.queryProductImgListByProductId(productId);
        //删除物理详情图（即存储在磁盘中的商品详情图）
        for(ProductImg productImg : productImgList){
            ImageUtil.deleteFileOrPath(productImg.getProductDetailImg());
        }
        //删除数据库里原有详情图的信息
        productImgDao.deleteProductImgByProductId(productId);

    }
    @Transactional
    public int  addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) {
        /**
         * 1、生成缩略图，获取缩略图相对路径并赋值给product
         * 2、向product表中写入商品信息，获取productId
         * 3、结合productId批量处理商品详情图
         * 4、将商品详情图列表批量插入product_img表中
         */
        int i =0 ;
        if(product != null && product.getShop()!=null && product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setEnableStatus(1);
            if(thumbnail!=null){
                addThumbnail(product,thumbnail);
            }
            try {
                 i = productDao.insertProduct(product);
                if(i<= 0){
                    throw new RuntimeException("添加商品失败");
                }
            }catch (Exception e){
                throw new RuntimeException("添加商品失败"+e.getMessage());
            }
            if(productImgHolderList!=null && productImgHolderList.size()>0){
                batchAddImageList(product,productImgHolderList);
            }
        }else {
            throw new RuntimeException("商品信息不能为空");
        }
        return i;
    }

    private void batchAddImageList(Product product, List<ImageHolder> productImgHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for(ImageHolder productImgHolder :productImgHolderList){
            String s = ImageUtil.generateThumbnail(productImgHolder, dest);
            ProductImg productImg= new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setProductDetailImg(s);
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if(productImgHolderList.size() >0 ){
            try{
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectNum <=0){
                    throw new RuntimeException("添加图片详情图失败:");
                }
            }catch (Exception e){
                throw new RuntimeException("添加图片详情图失败: " + e.getMessage());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String s = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddress(s);
    }
}
