package com.flame.dao;

import java.util.List;

import com.flame.base.dao.BaseDao;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dto.AnalysisDto;
import com.flame.dto.StudentPageDto;
import com.flame.dto.StudentScoreDto;
import com.flame.entity.Student;

public interface StudentDao extends BaseDao<Student, Long> {

	DataGrid<StudentPageDto> findStudentAll(Params params, Page page);

	Student selectByName(Student student);

	Student selectByStuNo(Student student);

	List<StudentScoreDto> selectScore(Student student2);

	List<Student> selectClass(Params params);

	DataGrid<StudentScoreDto> selectScorePage(Params params, Page page);

	void importExcel(List<Student> students);

    List<AnalysisDto> performanceAnalysis(Params params);
}
