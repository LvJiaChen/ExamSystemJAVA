package com.flame.service;

import java.util.List;
import java.util.Map;

import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseService;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.dto.QuestionSumDto;
import com.flame.dto.StudentPaperDto;
import com.flame.entity.Paper;

/**
 * 服务接口。
 */
public interface PaperService extends BaseService<Paper, Long> {

	Map<String, Object> generatePapers(PaperDto paperDto);

	DataGrid<PaperDto> findPaper(Params params, Page page);

	List<PaperDto> selectParer(Params params);

	List<PaperDto> selectFixedPaper(Params params);

	void submitPapers(StudentPaperDto studentPaperDto);

	List<Paper> selectReadPaper(Params params);

	List<ExamQuestionDto> selectReadQuest(Paper paper);

	void getSumScort(QuestionSumDto questionSumDto);

	Paper selectPaperByName(String paperName);

}
