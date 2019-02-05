package com.flame.dto;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.flame.base.dto.BaseDto;
import com.flame.entity.StudentPaper;
import org.springframework.format.annotation.DateTimeFormat;

public class StudentPaperDto extends BaseDto{

	private static final long serialVersionUID = 4796031090150123849L;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date startTime;
	//
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date endTime;
	
	private String startTimeStr;
	// 
	private String endTimeStr;
	
	private List<StudentPaper> studentPaper=new ArrayList<StudentPaper>();

	
	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public List<StudentPaper> getStudentPaper() {
		return studentPaper;
	}

	public void setStudentPaper(List<StudentPaper> studentPaper) {
		this.studentPaper = studentPaper;
	}
	
	
	
}
