package com.flame.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.flame.base.entity.BaseEntity;

/**
 * 实体对象：
 */
public class ExamQuestion extends BaseEntity<Long> {

	private static final long serialVersionUID = 7716361532240175976L;

	// ~~~~实体属性
	//
	@Valid
	@NotNull(message="题目类型不能为空")
	private String questionType;
	// 要求
	private String questionClaim;
	//
	@Valid
	@NotNull(message="题目内容不能为空")
	private String questionContent;
	// 
	private String pic;
	// 
	private String optionA;
	// 
	private String optionB;
	// 
	private String optionC;
	// 
	private String optionD;
	// 
	private String optionE;
	// 
	private String optionF;
	//
	@Valid
	@NotNull(message="题目答案不能为空")
	private String answer;
	//
	@Valid
	@NotNull(message="题目学科不能为空")
	private String subject;
	// 
	private java.math.BigDecimal scort;

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
	public String getQuestionType() {
		return this.questionType;
	}

	/**
	 * 
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	/**
	 * 要求
	 */
	public String getQuestionClaim() {
		return this.questionClaim;
	}

	/**
	 * 要求
	 */
	public void setQuestionClaim(String questionClaim) {
		this.questionClaim = questionClaim;
	}
	
	/**
	 * 
	 */
	public String getQuestionContent() {
		return this.questionContent;
	}

	/**
	 * 
	 */
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	
	/**
	 * 
	 */
	public String getPic() {
		return this.pic;
	}

	/**
	 * 
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	/**
	 * 
	 */
	public String getOptionA() {
		return this.optionA;
	}

	/**
	 * 
	 */
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	
	/**
	 * 
	 */
	public String getOptionB() {
		return this.optionB;
	}

	/**
	 * 
	 */
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	
	/**
	 * 
	 */
	public String getOptionC() {
		return this.optionC;
	}

	/**
	 * 
	 */
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	
	/**
	 * 
	 */
	public String getOptionD() {
		return this.optionD;
	}

	/**
	 * 
	 */
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	
	/**
	 * 
	 */
	public String getOptionE() {
		return this.optionE;
	}

	/**
	 * 
	 */
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	
	/**
	 * 
	 */
	public String getOptionF() {
		return this.optionF;
	}

	/**
	 * 
	 */
	public void setOptionF(String optionF) {
		this.optionF = optionF;
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
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getScort() {
		return this.scort;
	}

	/**
	 * 
	 */
	public void setScort(java.math.BigDecimal scort) {
		this.scort = scort;
	}
}
