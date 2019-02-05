package com.flame.dto;

import com.flame.entity.Admin;

public class AdminPageDto extends Admin{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3358683505662259353L;
	private String power;
	private Integer currentPage;
	private Integer PageSize;
	
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return PageSize;
	}
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	

}
