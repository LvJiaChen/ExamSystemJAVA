package com.flame.dto;

import com.flame.entity.Student;

public class StudentDto extends Student {

	private static final long serialVersionUID = -5429403535959416805L;
	private String newPassword;
	private String name;
	private String value;
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
