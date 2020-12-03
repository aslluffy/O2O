package com.imooc.o2o.service;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.WechatAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface WechatAuthService {

    /**
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     *
     * @param wechatAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    WechatAuthExecution register(WechatAuth wechatAuth,
                                 CommonsMultipartFile profileImg) throws RuntimeException;

}
