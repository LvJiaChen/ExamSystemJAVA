package com.flame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dao.ExamQuestionDao;
import com.flame.dto.ExamQuestionDto;
import com.flame.entity.ExamQuestion;

@Repository("examQuestionDao")
public class ExamQuestionDaoImpl extends BaseDaoImpl<ExamQuestion, Long> implements ExamQuestionDao {
	@Override
	public DataGrid<ExamQuestionDto> findQuestion(Params params, Page page) {
		return this.selectPage(this.getMethodName(), params, page);
	}

	@Override
	public void importExcel(List<ExamQuestion> examQuestions) {
		this.insert(this.getMethodName(), examQuestions);
	}

	@Override
	public ExamQuestion selectObjectiveQuestions(Long questionId) {
		return this.selectOne(this.getMethodName(), questionId);
	}

	@Override
	public DataGrid<ExamQuestionDto> findQuestionForAdd(Params params, Page page) {
		return this.selectPage(this.getMethodName(), params, page);
	}
}
