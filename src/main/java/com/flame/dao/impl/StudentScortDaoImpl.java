package com.flame.dao.impl;

import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.dao.StudentScortDao;
import com.flame.dto.QuestionSumDto;
import com.flame.entity.StudentScort;

@Repository("studentScortDao")
public class StudentScortDaoImpl extends BaseDaoImpl<StudentScort, Long> implements StudentScortDao {

	@Override
	public StudentScort selectSumScort(QuestionSumDto questionSumDto) {
		return this.selectOne(this.getMethodName(), questionSumDto);
	}

}
