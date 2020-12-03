package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.Person;
import org.apache.ibatis.annotations.Param;


public interface PersonInfoDao {

	/**
	 * 
	 * @param personInfoCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Person> queryPersonInfoList(
            @Param("personInfoCondition") Person personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 
	 * @param personInfoCondition
	 * @return
	 */
	int queryPersonInfoCount(
            @Param("personInfoCondition") Person personInfoCondition);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Person queryPersonInfoById(long userId);

	/**
	 * 
	 * @param wechatAuth
	 * @return
	 */
	int insertPersonInfo(Person personInfo);

	/**
	 * 
	 * @param wechatAuth
	 * @return
	 */
	int updatePersonInfo(Person personInfo);

	/**
	 * 
	 * @param wechatAuth
	 * @return
	 */
	int deletePersonInfo(long userId);
}
