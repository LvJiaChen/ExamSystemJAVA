package com.flame.service;

import java.io.InputStream;
import java.util.List;

import com.flame.dto.AnalysisDto;
import org.springframework.web.multipart.MultipartFile;

import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseService;
import com.flame.dto.StudentPageDto;
import com.flame.dto.StudentScoreDto;
import com.flame.entity.Student;

/**
 * 服务接口。
 */
public interface StudentService extends BaseService<Student, Long> {

	DataGrid<StudentPageDto> findStudentAll(Params params, Page page);

	Student selectByName(Student student);

	Student selectByStuNo(Student student);

	List<StudentScoreDto> selectScore(Student student2);

	List<Student> selectClass(Params params);

	DataGrid<StudentScoreDto> selectScorePage(Params params, Page page);

	void importExcel(InputStream in, MultipartFile file);

    List<AnalysisDto> performanceAnalysis(Params params);
}
