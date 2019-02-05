package com.flame.dao;

import com.flame.base.dao.BaseDao;
import com.flame.dto.QuestionSumDto;
import com.flame.entity.StudentScort;

public interface StudentScortDao extends BaseDao<StudentScort, Long> {

	StudentScort selectSumScort(QuestionSumDto questionSumDto);

}
