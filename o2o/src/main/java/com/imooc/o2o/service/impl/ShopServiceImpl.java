package com.imooc.o2o.service.impl;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao ;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop,  ImageHolder imageHolder) {
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setLastEditTime(new Date());
            shop.setCreateTime(new Date());
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            }else{
                    if(imageHolder.getImage()!= null){
                            addShopImg(shop,imageHolder);
                            effectedNum = shopDao.updateShop(shop);
                            if(effectedNum<=0){
                                throw new ShopOperationException("更新图片地址失败");
                            }
                        }
            }
        }
        catch (Exception e){
            throw new ShopOperationException("addShop Error"+e.getMessage());
        }
    return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop,  ImageHolder imageHolder) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgInputStreamAddr = ImageUtil.generateThumbnail(imageHolder,dest);
        shop.setShopImg(shopImgInputStreamAddr);
    }

    @Override
    public int updateShop(Shop shop) {
        return shopDao.updateShop(shop);
    }

    public Shop queryByShopId(long shopId){
        return shopDao.queryByShopId(shopId);
   }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Shop> shops = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shops!=null){
            se.setShopList(shops);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    public  ShopExecution modifyShop(Shop shop, ImageHolder imageHolder){
        if(shop==null || shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try{
                if(imageHolder !=null){
                if(imageHolder.getImage()!=null && imageHolder.getImageName()!=null && !"".equals(imageHolder.getImageName())){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,imageHolder); }
                }
            shop.setLastEditTime(new Date());
                int i = shopDao.updateShop(shop);
                if(i<=0){
                  return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }

            }catch (Exception e){
                throw new ShopOperationException(e.getMessage());
            }
        }

   }
}
