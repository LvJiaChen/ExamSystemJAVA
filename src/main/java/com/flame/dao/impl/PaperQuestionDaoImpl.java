package com.flame.dao.impl;

import java.util.List;

import com.flame.dto.ExamQuestionDto;
import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.dao.PaperQuestionDao;
import com.flame.entity.PaperQuestion;

@Repository("paperQuestionDao")
public class PaperQuestionDaoImpl extends BaseDaoImpl<PaperQuestion, Long> implements PaperQuestionDao {

	@Override
	public List<PaperQuestion> selectQuestIdByPaperId(Long id) {
		return this.selectList(this.getMethodName(),id);
	}

	@Override
	public List<ExamQuestionDto> getQuestionIds(Long paperId) {
		return this.selectList(this.getMethodName(), paperId);
	}

	@Override
	public void deleteQuestFixedPaper(PaperQuestion paperQuestion) {
		this.delete(this.getMethodName(), paperQuestion);
	}

}
