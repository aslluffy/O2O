package com.imooc.o2o.enums;

import lombok.Getter;

@Getter
public enum ShopStateEnum {
    CHECK(0,"审核中"),NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop为空"),
    OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误");
    private int state;

    private String stateInfo;

    private ShopStateEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }
    public static ShopStateEnum stateof(int state){
        for(ShopStateEnum shopStateEnum : values()){
            if(shopStateEnum.state==state){
                return shopStateEnum;
            }
        }
        return null;
    }

}
