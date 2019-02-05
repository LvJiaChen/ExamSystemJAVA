package com.flame.dto;

import java.math.BigDecimal;

import com.flame.base.dto.BaseDto;

public class StudentScoreDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4900165574742908325L;
	private String paperName;
	private String subject;
	private String studentName;
	private String sex;
	private String stuNo;
	private BigDecimal sumScort;
	private String tClass;
	
	public String gettClass() {
		return tClass;
	}
	public void settClass(String tClass) {
		this.tClass = tClass;
	}
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
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public BigDecimal getSumScort() {
		return sumScort;
	}
	public void setSumScort(BigDecimal sumScort) {
		this.sumScort = sumScort;
	}
	
	
}
