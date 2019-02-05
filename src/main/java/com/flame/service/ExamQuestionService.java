package com.flame.service;

import java.io.InputStream;

import com.flame.entity.Paper;
import org.springframework.web.multipart.MultipartFile;

import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseService;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.dto.PaperQuestionDto;
import com.flame.entity.ExamQuestion;

/**
 * 服务接口。
 */
public interface ExamQuestionService extends BaseService<ExamQuestion, Long> {

	DataGrid<ExamQuestionDto> findQuestion(Params params, Page page);

	void importExcel(InputStream in, MultipartFile file);

	PaperDto addQuestFixedPaper(PaperQuestionDto paperQuestion);

	DataGrid<ExamQuestionDto> findQuestionForAdd(Params params, Page page);


	PaperDto getCurrentNum(Paper paper);
}
