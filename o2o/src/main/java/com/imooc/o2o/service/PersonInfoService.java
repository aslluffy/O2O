package com.imooc.o2o.service;

import com.imooc.o2o.dto.PersonInfoExecution;
import com.imooc.o2o.entity.Person;

public interface PersonInfoService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Person getPersonInfoById(Long userId);

	/**
	 * 
	 * @param personInfoCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PersonInfoExecution getPersonInfoList(Person personInfoCondition,
										  int pageIndex, int pageSize);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	PersonInfoExecution addPersonInfo(Person personInfo);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	PersonInfoExecution modifyPersonInfo(Person personInfo);

}
