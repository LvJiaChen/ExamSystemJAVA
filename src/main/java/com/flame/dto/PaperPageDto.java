package com.flame.dto;

import com.flame.entity.Paper;

public class PaperPageDto extends Paper{

	private static final long serialVersionUID = -6520574891671717060L;
	private String examTypeStr;
	private Integer currentPage;
	private Integer PageSize;

	public String getExamTypeStr() {
		return examTypeStr;
	}
	public void setExamTypeStr(String examTypeStr) {
		this.examTypeStr = examTypeStr;
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
