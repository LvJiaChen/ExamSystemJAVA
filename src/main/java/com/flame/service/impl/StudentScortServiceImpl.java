package com.flame.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flame.base.dao.BaseDao;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.StudentScortDao;
import com.flame.entity.StudentScort;
import com.flame.service.StudentScortService;

@Service("studentScortService")
public class StudentScortServiceImpl extends BaseServiceImpl<StudentScort, Long> implements StudentScortService {

	@Resource
	private StudentScortDao studentScortDao;

	@Override
	protected BaseDao<StudentScort, Long> getBaseDao() {
		return this.studentScortDao;
	}
}
