package com.flame.dao;

import java.util.List;

import com.flame.base.dao.BaseDao;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.entity.ExamQuestion;
import com.flame.entity.Paper;

public interface PaperDao extends BaseDao<Paper, Long> {

	DataGrid<PaperDto> findPaper(Params params, Page page);

	List<ExamQuestionDto> selestSingleChoices(Paper paper);

	List<ExamQuestionDto> selestMultipleChoices(Paper paper);

	List<ExamQuestion> selestTrueOrFalses(Paper paper);

	List<ExamQuestion> selestBlankQuestions(Paper paper);

	List<ExamQuestion> selestQuestionsAndAnswers(Paper paper);

	List<PaperDto> selectParer(Params params);

	List<Paper> selectReadPaper(Params params);

	Paper selectPaperByName(String paperName);

}
