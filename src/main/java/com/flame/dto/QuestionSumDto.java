package com.flame.dto;

import java.math.BigDecimal;

import com.flame.base.dto.BaseDto;

public class QuestionSumDto extends BaseDto{

	private static final long serialVersionUID = 3024388273707385023L;
	
	private BigDecimal sumScort;
	
	private Long studentId;
	
	private Long paperId;

	public BigDecimal getSumScort() {
		return sumScort;
	}

	public void setSumScort(BigDecimal sumScort) {
		this.sumScort = sumScort;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	
	
}
