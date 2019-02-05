package com.flame.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.flame.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 实体对象：
 */
public class Paper extends BaseEntity<Long> {

	private static final long serialVersionUID = 3964235766839329248L;

	// ~~~~实体属性
	//
	@Valid
	@NotNull(message="试卷名不能为空")
	private String paperName;
	// 
	private Long adminId;
	// 0考试1考查
	private Long examType;
	// 
	private Long paperTime;
	//
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date paperEndTime;
	//
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date paperStartTime;
	//
	@Valid
	@NotNull(message="课程不能为空")
	private String subject;
	// 
	private Long singleChoiceNum;
	// 
	private java.math.BigDecimal singleChoiceScort;
	// 
	private Long multipleChoiceNum;
	// 
	private java.math.BigDecimal multipleChoiceScort;
	// 
	private Long trueOrFalseNum;
	// 
	private java.math.BigDecimal trueOrFalseScort;
	// 
	private Long blankQuestionNum;
	// 
	private java.math.BigDecimal blankQuestionScort;
	// 
	private Long questionsAndAnswersNum;
	// 
	private String questionsAndAnswersScorts;
	// 
	private Boolean isRandom;

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
	public String getPaperName() {
		return this.paperName;
	}

	/**
	 * 
	 */
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	
	/**
	 * 
	 */
	public Long getAdminId() {
		return this.adminId;
	}

	/**
	 * 
	 */
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	
	/**
	 * 0考试1考查
	 */
	public Long getExamType() {
		return this.examType;
	}

	/**
	 * 0考试1考查
	 */
	public void setExamType(Long examType) {
		this.examType = examType;
	}
	
	/**
	 * 
	 */
	public Long getPaperTime() {
		return this.paperTime;
	}

	/**
	 * 
	 */
	public void setPaperTime(Long paperTime) {
		this.paperTime = paperTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getPaperEndTime() {
		return this.paperEndTime;
	}

	/**
	 * 
	 */
	public void setPaperEndTime(java.util.Date paperEndTime) {
		this.paperEndTime = paperEndTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getPaperStartTime() {
		return this.paperStartTime;
	}

	/**
	 * 
	 */
	public void setPaperStartTime(java.util.Date paperStartTime) {
		this.paperStartTime = paperStartTime;
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
	public Long getSingleChoiceNum() {
		return this.singleChoiceNum;
	}

	/**
	 * 
	 */
	public void setSingleChoiceNum(Long singleChoiceNum) {
		this.singleChoiceNum = singleChoiceNum;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getSingleChoiceScort() {
		return this.singleChoiceScort;
	}

	/**
	 * 
	 */
	public void setSingleChoiceScort(java.math.BigDecimal singleChoiceScort) {
		this.singleChoiceScort = singleChoiceScort;
	}
	
	/**
	 * 
	 */
	public Long getMultipleChoiceNum() {
		return this.multipleChoiceNum;
	}

	/**
	 * 
	 */
	public void setMultipleChoiceNum(Long multipleChoiceNum) {
		this.multipleChoiceNum = multipleChoiceNum;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getMultipleChoiceScort() {
		return this.multipleChoiceScort;
	}

	/**
	 * 
	 */
	public void setMultipleChoiceScort(java.math.BigDecimal multipleChoiceScort) {
		this.multipleChoiceScort = multipleChoiceScort;
	}
	
	/**
	 * 
	 */
	public Long getTrueOrFalseNum() {
		return this.trueOrFalseNum;
	}

	/**
	 * 
	 */
	public void setTrueOrFalseNum(Long trueOrFalseNum) {
		this.trueOrFalseNum = trueOrFalseNum;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getTrueOrFalseScort() {
		return this.trueOrFalseScort;
	}

	/**
	 * 
	 */
	public void setTrueOrFalseScort(java.math.BigDecimal trueOrFalseScort) {
		this.trueOrFalseScort = trueOrFalseScort;
	}
	
	/**
	 * 
	 */
	public Long getBlankQuestionNum() {
		return this.blankQuestionNum;
	}

	/**
	 * 
	 */
	public void setBlankQuestionNum(Long blankQuestionNum) {
		this.blankQuestionNum = blankQuestionNum;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getBlankQuestionScort() {
		return this.blankQuestionScort;
	}

	/**
	 * 
	 */
	public void setBlankQuestionScort(java.math.BigDecimal blankQuestionScort) {
		this.blankQuestionScort = blankQuestionScort;
	}
	
	/**
	 * 
	 */
	public Long getQuestionsAndAnswersNum() {
		return this.questionsAndAnswersNum;
	}

	/**
	 * 
	 */
	public void setQuestionsAndAnswersNum(Long questionsAndAnswersNum) {
		this.questionsAndAnswersNum = questionsAndAnswersNum;
	}
	
	/**
	 * 
	 */
	public String getQuestionsAndAnswersScorts() {
		return this.questionsAndAnswersScorts;
	}

	/**
	 * 
	 */
	public void setQuestionsAndAnswersScorts(String questionsAndAnswersScorts) {
		this.questionsAndAnswersScorts = questionsAndAnswersScorts;
	}
	
	/**
	 * 
	 */
	public Boolean getIsRandom() {
		return this.isRandom;
	}

	/**
	 * 
	 */
	public void setIsRandom(Boolean isRandom) {
		this.isRandom = isRandom;
	}
}
