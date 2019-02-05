package com.flame.dao.impl;

import java.util.List;

import com.flame.dto.AnalysisDto;
import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dao.StudentDao;
import com.flame.dto.StudentPageDto;
import com.flame.dto.StudentScoreDto;
import com.flame.entity.Student;

@Repository("studentDao")
public class StudentDaoImpl extends BaseDaoImpl<Student, Long> implements StudentDao {

	@Override
	public DataGrid<StudentPageDto> findStudentAll(Params params, Page page) {
		return this.selectPage(this.getMethodName(), params, page);
	}

	@Override
	public Student selectByName(Student student) {
		return this.selectOne(this.getMethodName(), student);
	}

	@Override
	public Student selectByStuNo(Student student) {
		return this.selectOne(this.getMethodName(), student);
	}

	@Override
	public List<StudentScoreDto> selectScore(Student student2) {
		return this.selectList(this.getMethodName(), student2);
	}

	@Override
	public List<Student> selectClass(Params params) {
		return this.selectList(this.getMethodName(), params);
	}

	@Override
	public DataGrid<StudentScoreDto> selectScorePage(Params params,Page page) {
		return this.selectPage(this.getMethodName(), params, page);
	}

	@Override
	public void importExcel(List<Student> students) {
		this.insert(this.getMethodName(), students);
	}

	@Override
	public List<AnalysisDto> performanceAnalysis(Params params) {
		return this.selectList(this.getMethodName(),params);
	}

}
