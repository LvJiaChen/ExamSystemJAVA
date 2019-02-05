package com.flame.dao;

import java.util.List;

import com.flame.base.dao.BaseDao;
import com.flame.dto.ExamQuestionDto;
import com.flame.entity.PaperQuestion;

public interface PaperQuestionDao extends BaseDao<PaperQuestion, Long> {

	List<PaperQuestion> selectQuestIdByPaperId(Long id);

	List<ExamQuestionDto> getQuestionIds(Long paperId);

	void deleteQuestFixedPaper(PaperQuestion paperQuestion);

}
