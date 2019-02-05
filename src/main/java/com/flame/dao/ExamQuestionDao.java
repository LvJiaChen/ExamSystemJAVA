package com.flame.dao;

import java.util.List;

import com.flame.base.dao.BaseDao;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dto.ExamQuestionDto;
import com.flame.entity.ExamQuestion;

public interface ExamQuestionDao extends BaseDao<ExamQuestion, Long> {

	DataGrid<ExamQuestionDto> findQuestion(Params params, Page page);

	void importExcel(List<ExamQuestion> examQuestions);

	ExamQuestion selectObjectiveQuestions(Long questionId);

	DataGrid<ExamQuestionDto> findQuestionForAdd(Params params, Page page);


}
