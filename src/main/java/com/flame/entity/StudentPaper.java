package com.flame.entity;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class StudentPaper extends BaseEntity<Long> {

	private static final long serialVersionUID = 4323078548029717892L;

	// ~~~~实体属性
	// 
	private Long studentId;
	// 
	private Long paperId;
	// 
	private Long questionId;
	// 
	private java.math.BigDecimal questionScort;
	// 
	private String answer;
	// 
	private java.math.BigDecimal sumScort;
	// 
	private Boolean isCheck;

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
	public Long getStudentId() {
		return this.studentId;
	}

	/**
	 * 
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	/**
	 * 
	 */
	public Long getPaperId() {
		return this.paperId;
	}

	/**
	 * 
	 */
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	
	/**
	 * 
	 */
	public Long getQuestionId() {
		return this.questionId;
	}

	/**
	 * 
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getQuestionScort() {
		return this.questionScort;
	}

	/**
	 * 
	 */
	public void setQuestionScort(java.math.BigDecimal questionScort) {
		this.questionScort = questionScort;
	}
	
	/**
	 * 
	 */
	public String getAnswer() {
		return this.answer;
	}

	/**
	 * 
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getSumScort() {
		return this.sumScort;
	}

	/**
	 * 
	 */
	public void setSumScort(java.math.BigDecimal sumScort) {
		this.sumScort = sumScort;
	}
	
	/**
	 * 
	 */
	public Boolean getIsCheck() {
		return this.isCheck;
	}

	/**
	 * 
	 */
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
}
