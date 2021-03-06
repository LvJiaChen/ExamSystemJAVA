package com.flame.service;

import java.util.List;

import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseService;
import com.flame.dto.AdminPageDto;
import com.flame.entity.Admin;
import com.flame.entity.Paper;

/**
 * 服务接口。
 */
public interface AdminService extends BaseService<Admin, Long> {

	DataGrid<AdminPageDto> findAdmin(Params params, Page page);

	Admin selectByTeacherNo(Admin admin);

	Admin selectByName(Admin admin);

	List<Paper> selectSubject(Params params);

}
