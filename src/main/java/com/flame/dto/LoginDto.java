package com.flame.dto;

import com.flame.base.dto.BaseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class LoginDto extends BaseDto{
	private static final long serialVersionUID = 8519498844317988706L;
	private Boolean isUser;
	@Valid
	@NotNull(message="学号/工号不能为空")
	private String userNo;
	@Valid
	@NotNull(message="密码不能为空")
	private String passWord;
	private Long paperId;
	private Long studentId;
	private Long paperTime;

	public Long getPaperTime() {
		return paperTime;
	}

	public void setPaperTime(Long paperTime) {
		this.paperTime = paperTime;
	}

	public Boolean getUser() {
		return isUser;
	}

	public void setUser(Boolean user) {
		isUser = user;
	}


	public Boolean getIsUser() {
		return isUser;
	}
	public void setIsUser(Boolean isUser) {
		this.isUser = isUser;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Long getPaperId() {
		return paperId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	
}
