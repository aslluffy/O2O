package com.imooc.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class WechatAuth {
	private Long wechatAuthId;
	private Long userId;
	private String openId;
	private Date createTime;
	private Person personInfo;

	public Long getWechatAuthId() {
		return wechatAuthId;
	}

	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Person getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(Person personInfo) {
		this.personInfo = personInfo;
	}

}
