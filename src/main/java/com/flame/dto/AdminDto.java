package com.flame.dto;

import com.flame.entity.Admin;

public class AdminDto extends Admin{

	private static final long serialVersionUID = -1922003953007063896L;
	
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
