package com.flame.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flame.base.dao.BaseDao;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.StudentPaperDao;
import com.flame.entity.StudentPaper;
import com.flame.service.StudentPaperService;

@Service("studentPaperService")
public class StudentPaperServiceImpl extends BaseServiceImpl<StudentPaper, Long> implements StudentPaperService {

	@Resource
	private StudentPaperDao studentPaperDao;

	@Override
	protected BaseDao<StudentPaper, Long> getBaseDao() {
		return this.studentPaperDao;
	}
}
