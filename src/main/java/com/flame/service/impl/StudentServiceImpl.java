package com.flame.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.flame.dto.AnalysisDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flame.base.dao.BaseDao;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseServiceImpl;
import com.flame.base.util.MD5;
import com.flame.dao.StudentDao;
import com.flame.dto.StudentPageDto;
import com.flame.dto.StudentScoreDto;
import com.flame.entity.Student;
import com.flame.service.StudentService;
import com.flame.util.ExcelUtil;

@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student, Long> implements StudentService {

	@Resource
	private StudentDao studentDao;

	@Override
	protected BaseDao<Student, Long> getBaseDao() {
		return this.studentDao;
	}

	@Override
	public DataGrid<StudentPageDto> findStudentAll(Params params, Page page) {
		return studentDao.findStudentAll(params,page);
	}

	@Override
	public Student selectByName(Student student) {
		return studentDao.selectByName(student);
	}

	@Override
	public Student selectByStuNo(Student student) {
		return studentDao.selectByStuNo(student);
	}

	@Override
	public List<StudentScoreDto> selectScore(Student student2) {
		return studentDao.selectScore(student2);
	}

	@Override
	public List<Student> selectClass(Params params) {
		return studentDao.selectClass(params);
	}

	@Override
	public DataGrid<StudentScoreDto> selectScorePage(Params params,Page page) {
		return studentDao.selectScorePage(params,page);
	}

	@Override
	public void importExcel(InputStream in, MultipartFile file) {
		List<List<Object>> listob = null;
		try {
			listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Student> students = new ArrayList<Student>();
		// 遍历listob数据，把数据放到List中
		for (int i = 0; i < listob.size(); i++) {
			List<Object> ob = listob.get(i);
			Student student = new Student();
			
			Student student2=new Student();
			student2.setStuNo(String.valueOf(ob.get(0)));
			Student student3=studentDao.selectByStuNo(student2);
			if (student3!=null) {
				throw new BusinessException("学号："+student3.getStuNo()+"姓名："+student3.getName()+"已经添加");
			}
			// 通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
			if (ob.get(0) != null) {
				student.setStuNo(String.valueOf(ob.get(0)));
			} else {
				throw new BusinessException("学号不能为空");
			}
			if (ob.get(1) != null) {
				student.setName(String.valueOf(ob.get(1)));
			} else {
				throw new BusinessException("姓名不能为空");
			}
			if (ob.get(2) != null) {
				student.setSex(String.valueOf(ob.get(2)));
			} else {
				throw new BusinessException("性别不能为空");
			}
			if (ob.get(3) != null) {
				student.setTClass(String.valueOf(ob.get(3)));
			} else {
				throw new BusinessException("班级不能为空");
			}
			if (ob.get(4) != null) {
				student.setPassword(MD5.md5(String.valueOf(ob.get(4))));
			}else{
				throw new BusinessException("密码不能为空");
			}
			if (ob.get(5) != null) {
				student.setState(new Long(ob.get(5).toString()));
			}else{
				throw new BusinessException("状态不能为空");
			}
			students.add(student);
		}
		// 批量插入
		studentDao.importExcel(students);
	}

	@Override
	public List<AnalysisDto> performanceAnalysis(Params params) {
		return studentDao.performanceAnalysis(params);
	}

}
