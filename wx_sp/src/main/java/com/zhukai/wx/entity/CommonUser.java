package com.zhukai.wx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author zhukai
 * @email kzhu@today36524.com.cn
 * @date 2018-08-28 21:58:47
 */
public class CommonUser implements Serializable {
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机
	 */
	private String phone;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	
	/**
	 * 生命周期(0:禁用，1:正常，2:删除)
	 */
	private Integer lifeCycle;
	
	/**
	 * 
	 */
	private String remark;
	

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	
	public void setLifeCycle(Integer lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
	public Integer getLifeCycle() {
		return lifeCycle;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return remark;
	}
	
}
