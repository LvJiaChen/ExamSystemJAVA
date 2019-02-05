package com.flame.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class Student extends BaseEntity<Long> {

	private static final long serialVersionUID = 4567664637274372584L;

	// ~~~~实体属性
	//
	@Valid
	@NotNull(message="学号不能为空")
	@Pattern(regexp="^[0-9]*$",message="学号只能是数字")
	private String stuNo;
	//
	@Valid
	@NotNull(message="密码不能为空")
	@Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message="请输入正确的密码格式6-16位数字和字母的组合")
	private String password;
	//
	@Valid
	@NotNull(message="名字不能为空")
	private String name;
	//
	@Valid
	@NotNull(message="性别不能为空")
	private String sex;
	// 
	@Valid
	@NotNull(message="班级不能为空")
	private String tClass;
	// 头像
	private String avater;
	// 0正常1禁考
	private Long state;

	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}
	
	/**
	 * 
	 */
	public String getStuNo() {
		return this.stuNo;
	}

	/**
	 * 
	 */
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	
	/**
	 * 
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * 
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 
	 */
	public String getTClass() {
		return this.tClass;
	}

	/**
	 * 
	 */
	public void setTClass(String tClass) {
		this.tClass = tClass;
	}
	
	/**
	 * 头像
	 */
	public String getAvater() {
		return this.avater;
	}

	/**
	 * 头像
	 */
	public void setAvater(String avater) {
		this.avater = avater;
	}
	
	/**
	 * 0正常1禁考
	 */
	public Long getState() {
		return this.state;
	}

	/**
	 * 0正常1禁考
	 */
	public void setState(Long state) {
		this.state = state;
	}
}
