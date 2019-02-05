package com.flame.entity;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class PaperQuestion extends BaseEntity<Long> {

	private static final long serialVersionUID = 2536549441121975556L;

	// ~~~~实体属性
	// 
	private Long paperId;
	// 
	private Long questionId;

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
}
