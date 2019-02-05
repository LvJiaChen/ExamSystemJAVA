package com.flame.dto;

import java.util.List;

import com.flame.entity.ExamQuestion;

public class ExamQuestionDto extends ExamQuestion{ 
	
	private static final long serialVersionUID = 2949115200407447735L;
	private Long paperId;
	private Long studentId;

	private List<String> options;

	private Integer currentPage;
	private Integer PageSize;
	private String trueAnswer;
	public String getTrueAnswer() {
		return trueAnswer;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}

	public Long getPaperId() {
		return paperId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
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
