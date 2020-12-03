package com.imooc.o2o.dto;

import java.util.List;


import com.imooc.o2o.entity.Person;
import com.imooc.o2o.enums.PersonInfoStateEnum;

/**
 * 封装执行后结果
 */
public class PersonInfoExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的personInfo（增删改店铺的时候用）
	private Person personInfo;

	// 获取的personInfo列表(查询店铺列表的时候用)
	private List<Person> personInfoList;

	public PersonInfoExecution() {
	}

	// 失败的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,
			Person personInfo) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.personInfo = personInfo;
	}

	// 成功的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,
			List<Person> personInfoList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.personInfoList = personInfoList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Person getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(Person personInfo) {
		this.personInfo = personInfo;
	}

	public List<Person> getPersonInfoList() {
		return personInfoList;
	}

	public void setPersonInfoList(List<Person> personInfoList) {
		this.personInfoList = personInfoList;
	}

}