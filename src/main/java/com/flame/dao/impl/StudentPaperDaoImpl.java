package com.flame.dao.impl;

import java.util.List;

import com.flame.dto.ExamQuestionDto;
import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.base.model.Params;
import com.flame.dao.StudentPaperDao;
import com.flame.dto.QuestionSumDto;
import com.flame.entity.Student;
import com.flame.entity.StudentPaper;

@Repository("studentPaperDao")
public class StudentPaperDaoImpl extends BaseDaoImpl<StudentPaper, Long> implements StudentPaperDao {

	@Override
	public List<Student> selectStudentId(Long PaperId) {
		return this.selectList(this.getMethodName(), PaperId);
	}

	@Override
	public List<ExamQuestionDto> selectQuestion(Params params) {
		return this.selectList(this.getMethodName(), params);
	}

	@Override
	public List<StudentPaper> selectByPaperIdStudentId(QuestionSumDto questionSumDto) {
		return this.selectList(this.getMethodName(), questionSumDto);
	}

}
