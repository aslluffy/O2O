package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineService {
    public static final String HEADLINEKEY = "headline";
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
