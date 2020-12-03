package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;
@Data
public class ShopExecution {

    private int state;

    private String stateInfo;

    private Shop shop;

    private List<Shop> shopList;

    private int count;


    public ShopExecution(){

    }
    public ShopExecution(ShopStateEnum shopEumn){
        this.state =shopEumn.getState();
        this.stateInfo=shopEumn.getStateInfo();
    }
    public ShopExecution(ShopStateEnum shopEumn,Shop shop){
        this.state =shopEumn.getState();
        this.stateInfo=shopEumn.getStateInfo();
        this.shop=shop;
    }
    public ShopExecution(ShopStateEnum shopEumn, List<Shop> shopList){
        this.state =shopEumn.getState();
        this.stateInfo=shopEumn.getStateInfo();
        this.shopList=shopList;
    }
}
