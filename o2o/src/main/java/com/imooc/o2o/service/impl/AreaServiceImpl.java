package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;


    public List<Area>  getAreaList(){
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(!jedisKeys.exists(key)){
            areaList = areaDao.getAreaList();
            String jsonString =null;
            try {
                 jsonString = objectMapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                areaList = objectMapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw  new RuntimeException(e.getMessage());
            }
        }
        return areaList;
    }

}
