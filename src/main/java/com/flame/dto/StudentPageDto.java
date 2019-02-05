package com.flame.dto;

import com.flame.entity.Student;

public class StudentPageDto extends Student{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1204670009129595805L;
	private String paperName;
	private String subject;
	private String stateStr;
	private Integer currentPage;
	private Integer PageSize;
	
	
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
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
