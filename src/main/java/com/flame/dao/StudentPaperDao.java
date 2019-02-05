package com.flame.dao;

import java.util.List;

import com.flame.base.dao.BaseDao;
import com.flame.base.model.Params;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.QuestionSumDto;
import com.flame.entity.Student;
import com.flame.entity.StudentPaper;

public interface StudentPaperDao extends BaseDao<StudentPaper, Long> {

	List<Student> selectStudentId(Long id);

	List<ExamQuestionDto> selectQuestion(Params params);

	List<StudentPaper> selectByPaperIdStudentId(QuestionSumDto questionSumDto);

}
