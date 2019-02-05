package com.flame.dto;

import com.flame.entity.Paper;


import java.util.Date;

public class PaperDto extends Paper {

	private static final long serialVersionUID = -7633450442170906977L;
	private String name;
	private String value;
	private Long paperId;
	private Long questionId;
	private String examTypeStr;
	private String isRandomStr;
	private Long singleChoiceNumCurrent;
	private Long multipleChoiceNumCurrent;
	private Long trueOrFalseNumCurrent;
	private Long blankQuestionNumCurrent;
	private Long questionsAndAnswersNumCurrent;
	private java.util.Date[] testDate;

	public Date[] getTestDate() {
		return testDate;
	}

	public void setTestDate(Date[] testDate) {
		this.testDate = testDate;
	}

	public String getIsRandomStr() {
		return isRandomStr;
	}
	public void setIsRandomStr(String isRandomStr) {
		this.isRandomStr = isRandomStr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getSingleChoiceNumCurrent() {
		return singleChoiceNumCurrent;
	}
	public void setSingleChoiceNumCurrent(Long singleChoiceNumCurrent) {
		this.singleChoiceNumCurrent = singleChoiceNumCurrent;
	}
	public Long getMultipleChoiceNumCurrent() {
		return multipleChoiceNumCurrent;
	}
	public void setMultipleChoiceNumCurrent(Long multipleChoiceNumCurrent) {
		this.multipleChoiceNumCurrent = multipleChoiceNumCurrent;
	}
	public Long getTrueOrFalseNumCurrent() {
		return trueOrFalseNumCurrent;
	}
	public void setTrueOrFalseNumCurrent(Long trueOrFalseNumCurrent) {
		this.trueOrFalseNumCurrent = trueOrFalseNumCurrent;
	}
	public Long getBlankQuestionNumCurrent() {
		return blankQuestionNumCurrent;
	}
	public void setBlankQuestionNumCurrent(Long blankQuestionNumCurrent) {
		this.blankQuestionNumCurrent = blankQuestionNumCurrent;
	}
	public Long getQuestionsAndAnswersNumCurrent() {
		return questionsAndAnswersNumCurrent;
	}
	public void setQuestionsAndAnswersNumCurrent(Long questionsAndAnswersNumCurrent) {
		this.questionsAndAnswersNumCurrent = questionsAndAnswersNumCurrent;
	}
	public String getExamTypeStr() {
		return examTypeStr;
	}
	public void setExamTypeStr(String examTypeStr) {
		this.examTypeStr = examTypeStr;
	}
	public Long getPaperId() {
		return paperId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
}
