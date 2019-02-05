package com.flame.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class Admin extends BaseEntity<Long> {

	private static final long serialVersionUID = 1872227773465174720L;

	// ~~~~实体属性
	// 
	@Valid
	@NotNull(message="教师工号不能为空")
	@Pattern(regexp="^[0-9]*$",message="教师工号只能是数字")
	private String teacherNo;
	// 
	@Valid
	@NotNull(message="用户名不能为空")
	private String name;
	// 
	@Valid
	@NotNull(message="密码不能为空")
	@Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message="请输入正确的密码格式6-16位数字和字母的组合")
	private String password;
	// 
	@Valid
	@NotNull(message="所属部门不能为空")
	private String department;
	// 权限
	@Valid
	@NotNull(message="权限不能为空")
	private Long types;

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
	public String getTeacherNo() {
		return this.teacherNo;
	}

	/**
	 * 
	 */
	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
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
	public String getDepartment() {
		return this.department;
	}

	/**
	 * 
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * 权限
	 */
	public Long getTypes() {
		return this.types;
	}

	/**
	 * 权限
	 */
	public void setTypes(Long types) {
		this.types = types;
	}
}
