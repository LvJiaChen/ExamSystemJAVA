package com.flame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dao.PaperDao;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.entity.ExamQuestion;
import com.flame.entity.Paper;

@Repository("paperDao")
public class PaperDaoImpl extends BaseDaoImpl<Paper, Long>implements PaperDao {
	@Override
	public DataGrid<PaperDto> findPaper(Params params, Page page) {
		return this.selectPage(this.getMethodName(), params, page);
	}

	// 单选题
	@Override
	public List<ExamQuestionDto> selestSingleChoices(Paper paperDto) {
		return this.selectList(this.getMethodName(), paperDto);
	}

	// 多选题
	@Override
	public List<ExamQuestionDto> selestMultipleChoices(Paper paperDto) {
		return this.selectList(this.getMethodName(), paperDto);
	}

	// 判断题
	@Override
	public List<ExamQuestion> selestTrueOrFalses(Paper paperDto) {
		return this.selectList(this.getMethodName(), paperDto);
	}

	// 填空题
	@Override
	public List<ExamQuestion> selestBlankQuestions(Paper paperDto) {
		return this.selectList(this.getMethodName(), paperDto);
	}

	// 简答题
	@Override
	public List<ExamQuestion> selestQuestionsAndAnswers(Paper paperDto) {
		return this.selectList(this.getMethodName(), paperDto);
	}

	@Override
	public List<PaperDto> selectParer(Params params) {
		return this.selectList(this.getMethodName(), params);
	}

	@Override
	public List<Paper> selectReadPaper(Params params) {
		return this.selectList(this.getMethodName(), params);
	}

	@Override
	public Paper selectPaperByName(String paperName) {
		return this.selectOne(this.getMethodName(), paperName);
	}


}
