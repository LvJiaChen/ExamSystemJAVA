package com.flame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flame.base.dao.BaseDaoImpl;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.dao.AdminDao;
import com.flame.dto.AdminPageDto;
import com.flame.entity.Admin;
import com.flame.entity.Paper;

@Repository("adminDao")
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {

	@Override
	public DataGrid<AdminPageDto> findAdmin(Params params, Page page) {
		return this.selectPage(this.getMethodName() ,params, page);
	}

	@Override
	public Admin selectByTeacherNo(Admin admin) {
		return this.selectOne(this.getMethodName(), admin);
	}

	@Override
	public Admin selectByName(Admin admin) {
		return this.selectOne(this.getMethodName(), admin);
	}

	@Override
	public List<Paper> selectSubject(Params params) {
		return this.selectList(this.getMethodName(), params);
	}

}
