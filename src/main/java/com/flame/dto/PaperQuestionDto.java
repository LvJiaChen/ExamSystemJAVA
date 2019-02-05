package com.flame.dto;

import java.util.List;

import com.flame.base.dto.BaseDto;
import com.flame.entity.PaperQuestion;

public class PaperQuestionDto extends BaseDto{

	private static final long serialVersionUID = -2726589529010763263L;
	private List<PaperQuestion> paperQuestion;

	public List<PaperQuestion> getPaperQuestion() {
		return paperQuestion;
	}

	public void setPaperQuestion(List<PaperQuestion> paperQuestion) {
		this.paperQuestion = paperQuestion;
	}
	
}
