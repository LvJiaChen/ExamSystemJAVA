package com.flame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flame.base.dao.BaseDao;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.AdminDao;
import com.flame.dto.AdminPageDto;
import com.flame.entity.Admin;
import com.flame.entity.Paper;
import com.flame.service.AdminService;

@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Override
	protected BaseDao<Admin, Long> getBaseDao() {
		return this.adminDao;
	}

	@Override
	public DataGrid<AdminPageDto> findAdmin(Params params, Page page) {
		return adminDao.findAdmin(params, page);
	}

	@Override
	public Admin selectByTeacherNo(Admin admin) {
		return adminDao.selectByTeacherNo(admin);
	}

	@Override
	public Admin selectByName(Admin admin) {
		return adminDao.selectByName(admin);
	}

	@Override
	public List<Paper> selectSubject(Params params) {
		return adminDao.selectSubject(params);
	}
}
