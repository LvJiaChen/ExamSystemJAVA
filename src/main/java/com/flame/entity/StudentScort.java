package com.flame.entity;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class StudentScort extends BaseEntity<Long> {

	private static final long serialVersionUID = 2520577040487931288L;

	// ~~~~实体属性
	// 
	private Long studentId;
	// 
	private Long paperId;
	// 
	private java.math.BigDecimal sumScort;
	// 
	private java.util.Date startTime;
	// 
	private java.util.Date endTime;
	// 
	private Boolean isComplete;

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
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 
	 */
	public Boolean getIsComplete() {
		return this.isComplete;
	}

	/**
	 * 
	 */
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
}
