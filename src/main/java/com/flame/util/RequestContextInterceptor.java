package com.flame.util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flame.base.interceptor.RequestHolder;
import com.flame.dto.LoginDto;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.flame.base.dto.BaseResult;
import com.flame.entity.Admin;
import com.flame.entity.Student;
import com.flame.service.AdminService;
import com.flame.service.StudentService;

public class RequestContextInterceptor extends HandlerInterceptorAdapter implements InitializingBean{
	
	@Resource
	private AdminService adminService;
	@Resource
	private StudentService studentService;
	@Override
	public void afterPropertiesSet() throws Exception {
		
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		RequestHolder.init();
		HttpSession session=request.getSession();
		BaseResult<String> baseResult=new BaseResult<String>();
		Student student=new Student();
		Admin admin=new Admin();
		LoginDto loginDto=(LoginDto) session.getAttribute("user");
		if (loginDto!=null){
			if (loginDto.getIsUser()){
				student.setStuNo(loginDto.getUserNo());
			}else {
				admin.setTeacherNo(loginDto.getUserNo());
			}
		}else {
			baseResult.setResult(false);
			baseResult.setMessage("登录状态已过期,请重新登录");
			response.getWriter().print(baseResult);
			return false;
		}
		Student student2=studentService.selectByStuNo(student);
		Admin admin2=adminService.selectByTeacherNo(admin);
		if (admin2!=null||student2!=null) {
			return true;
		}else {
			baseResult.setResult(false);
			baseResult.setMessage("登录状态已过期,请重新登录");
			response.getWriter().print(baseResult);
			return false;
		}
	}
}
