package com.flame.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.flame.service.PaperService;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.flame.base.controller.BasicController;
import com.flame.base.dto.BaseResult;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.util.MD5;
import com.flame.dto.AdminDto;
import com.flame.dto.AdminPageDto;
import com.flame.dto.LoginDto;
import com.flame.dto.PaperDto;
import com.flame.dto.StudentDto;
import com.flame.dto.StudentPageDto;
import com.flame.dto.StudentScoreDto;
import com.flame.entity.Admin;
import com.flame.entity.Paper;
import com.flame.entity.Student;
import com.flame.service.AdminService;
import com.flame.service.StudentService;
import sun.rmi.runtime.Log;

@Controller
@RequestMapping("AdminController")
@SessionAttributes(value = {"user"})
public class AdminController extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;
    @Resource
    private PaperService paperService;

    // 用户登入
   @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object loginAdmin(String userNo, String passWord, Boolean isUser, Long paperId,HttpSession session ) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            LoginDto loginDto=new LoginDto();
            loginDto.setIsUser(isUser);
            loginDto.setPassWord(passWord);
            loginDto.setUserNo(userNo);
            loginDto.setPaperId(paperId);
            if (loginDto.getIsUser()) {
                Student student = new Student();
                student.setStuNo(loginDto.getUserNo());
                student.setPassword(loginDto.getPassWord());
                Student student2 = studentService.selectByStuNo(student);
                if (student2.getPassword().equals(MD5.md5(student.getPassword()))) {
                    loginDto.setStudentId(student2.getId());
                    baseResult.setMessage("登录成功");
                    baseResult.setResult(true);
                }else {
                    baseResult.setMessage("用户名 或密码 错误");
                    baseResult.setResult(false);
                }
            } else {
                Admin admin = new Admin();
                admin.setTeacherNo(loginDto.getUserNo());
                admin.setPassword(loginDto.getPassWord());
                Admin admin2 = adminService.selectByTeacherNo(admin);
                if (admin2.getPassword().equals(MD5.md5(admin.getPassword()))) {
                    baseResult.setMessage("登录成功");
                    baseResult.setResult(true);
                } else {
                    baseResult.setMessage("用户名 或密码 错误");
                    baseResult.setResult(false);
                }
            }
           session.setAttribute("user",loginDto);
        } catch (BusinessException e) {
            baseResult.setMessage("用户名 或密码 错误");
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("用户名 或密码 错误"+e.getMessage());
            baseResult.setResult(false);
        }
        return baseResult;

    }

    // 添加管理员
    @ResponseBody
    @RequestMapping(value = "insertAdmin", method = RequestMethod.POST)
    public Object insertAdmin(HttpServletRequest request, @Valid Admin admin, BindingResult bindingResult) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            this.vaildParamsDefault(baseResult, bindingResult);
            HttpSession session=request.getSession();
            LoginDto loginDto=(LoginDto) session.getAttribute("user");
            Admin user=new Admin();
            user.setTeacherNo(loginDto.getUserNo());
            Long types = adminService.selectByTeacherNo(user).getTypes();
            if (types != 1) {
                baseResult.setMessage("您无权添加管理员");
                baseResult.setResult(false);
                return baseResult;
            }
            if (admin.getName() == null || admin.getName().equals("")) {
                baseResult.setMessage("管理员名字不能为空");
                baseResult.setResult(false);
                return baseResult;
            }
            Admin admin2 = adminService.selectByName(admin);
            if (admin2 != null) {
                if (admin2.getName() != null || admin2.getName() != "") {
                    if (admin.getName().equals(admin2.getName())) {
                        baseResult.setMessage("管理员名字已经存在");
                        baseResult.setResult(false);
                        return baseResult;
                    }
                }
            }
            admin.setPassword(MD5.md5(admin.getPassword()));
            adminService.insert(admin);
            baseResult.setModel("添加管理员成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 查询管理员
    @ResponseBody
    @RequestMapping(value = "findAdmin", method = RequestMethod.POST)
    public Object findAdmin(AdminPageDto adminPageDto) {
        BaseResult<DataGrid<AdminPageDto>> baseResult = new BaseResult<DataGrid<AdminPageDto>>();
        try {
            Integer pageSize = null;
            Integer currentPage = null;
            Page page = new Page();
            if (adminPageDto.getPageSize() != null || adminPageDto.getPageSize() != null) {
                pageSize = adminPageDto.getPageSize();
                currentPage = adminPageDto.getCurrentPage();
                page.setLimit(pageSize);
                page.setOffset((currentPage - 1) * pageSize);
            }
            Params params = Params.create();
            params.add("entity", adminPageDto);
            DataGrid<AdminPageDto> grid = adminService.findAdmin(params, page);
            for (AdminPageDto pageDto : grid.getItems()) {
                if (pageDto.getTypes() == 1) {
                    pageDto.setPower("超级管理员");
                } else {
                    pageDto.setPower("普通管理员");
                }
            }
            baseResult.setMessage("查询成功");
            baseResult.setModel(grid);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
            e.printStackTrace();
        }
        return baseResult;
    }

    // 批量删除管理员
    @ResponseBody
    @RequestMapping("deleteAdmin")
    public Object deleteAdmin(HttpServletRequest request, String ids) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            HttpSession session=request.getSession();
            LoginDto loginDto=(LoginDto) session.getAttribute("user");
            Admin user=new Admin();
            user.setTeacherNo(loginDto.getUserNo());
            Long types = adminService.selectByTeacherNo(user).getTypes();
            if (types != 1) {
                baseResult.setMessage("您无权删除管理员");
                baseResult.setResult(false);
                return baseResult;
            }
            String[] id = ids.split(",");
            for (int j = 0; j < id.length; j++) {
                adminService.delete(Long.parseLong(id[j]));
            }
            baseResult.setModel("删除成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 编辑管理员
    @ResponseBody
    @RequestMapping("updateAdmin")
    public Object updateAdmin(HttpServletRequest request, Admin admin) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            HttpSession session=request.getSession();
            LoginDto loginDto=(LoginDto) session.getAttribute("user");
            Admin user=new Admin();
            user.setTeacherNo(loginDto.getUserNo());
            Long types = adminService.selectByTeacherNo(user).getTypes();
            if (types != 1) {
                baseResult.setMessage("您无权编辑管理员");
                baseResult.setResult(false);
                return baseResult;
            }
            if (admin.getPassword() != null) {
                admin.setPassword(MD5.md5(admin.getPassword()));
            }
            adminService.update(admin);
            baseResult.setModel("更新成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 修改密码
    @ResponseBody
    @RequestMapping(value = "updatePassword")
    public Object updatePassword(HttpServletRequest request, AdminDto admin) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            if (admin.getTeacherNo() == null || admin.getPassword() == null || admin.getNewPassword() == null) {
                baseResult.setMessage("请输入账号和密码");
                baseResult.setResult(false);
                return baseResult;
            }
            Admin admin2 = adminService.selectByTeacherNo(admin);
            if (!admin2.getPassword().equals(MD5.md5(admin.getPassword()))) {
                baseResult.setMessage("原密码或账号错误");
                baseResult.setResult(false);
                return baseResult;
            }
            admin2.setPassword(MD5.md5(admin.getNewPassword()));
            adminService.update(admin2);
            baseResult.setMessage("修改成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    //查询班级
    @ResponseBody
    @RequestMapping(value = "selectClass")
    public Object selectClass() {
        BaseResult<List<StudentDto>> baseResult = new BaseResult<List<StudentDto>>();
        try {
            // 查询学生Id
            Params params = Params.create();
            List<Student> students = studentService.selectClass(params);
            List<StudentDto> studentDtos = new ArrayList<StudentDto>();
            for (Student student : students) {
                StudentDto studentDto = new StudentDto();
                studentDto.setName(student.getTClass());
                studentDto.setValue(student.getTClass());
                studentDtos.add(studentDto);
            }
            baseResult.setModel(studentDtos);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    //试卷名查询
    @ResponseBody
    @RequestMapping(value = "selectPaperName",method={RequestMethod.POST,RequestMethod.GET})
    public Object selectPaperName() {
        BaseResult<List<PaperDto>> baseResult = new BaseResult<List<PaperDto>>();
        try {
            // 查询学生Id
            Params params = Params.create();
            List<Paper> papers = adminService.selectSubject(params);
            List<PaperDto> paperDtos = new ArrayList<PaperDto>();
            for (Paper paper : papers) {
                PaperDto paperDto = new PaperDto();
                paperDto.setName(paper.getPaperName());
                paperDto.setValue(paper.getPaperName());
                paperDtos.add(paperDto);
            }
            baseResult.setModel(paperDtos);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 成绩查询
    @ResponseBody
    @RequestMapping(value = "selectScore")
    public Object selectScore(StudentPageDto studentPageDto) {
        BaseResult<DataGrid<StudentScoreDto>> baseResult = new BaseResult<DataGrid<StudentScoreDto>>();
        try {
            Integer pageSize = null;
            Integer currentPage = null;
            Page page = new Page();
            if (studentPageDto.getPageSize() != null || studentPageDto.getPageSize() != null) {
                pageSize = studentPageDto.getPageSize();
                currentPage = studentPageDto.getCurrentPage();
                page.setLimit(pageSize);
                page.setOffset((currentPage - 1) * pageSize);
            }
            Params params = Params.create();
            params.add("entity", studentPageDto);
            DataGrid<StudentScoreDto> list = studentService.selectScorePage(params, page);
            baseResult.setModel(list);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }


    /**
     * 获取用户信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getLoginInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object getLoginInfo(HttpServletRequest request) {
        BaseResult<LoginDto> baseResult = new BaseResult<LoginDto>();
        try {
            LoginDto loginDto=(LoginDto) request.getSession().getAttribute("user");
            Paper paper=paperService.get(loginDto.getPaperId());
            loginDto.setPaperTime(paper.getPaperTime());
            baseResult.setModel(loginDto);
            baseResult.setResult(true);
            baseResult.setMessage("获取当前用户信息成功！");
        } catch (BusinessException be) {
            logger.debug("业务异常" + be.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("获取当前用户信息失败！");
        } catch (Exception e) {
            logger.debug("系统异常"+e.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("获取当前用户信息失败！");
        }
        return JSON.toJSONString(baseResult);
    }

    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/logOut", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object logOut(HttpServletRequest request) {
        BaseResult<LoginDto> baseResult = new BaseResult<LoginDto>();
        try {
            request.getSession().invalidate();
            baseResult.setResult(true);
            baseResult.setMessage("用户退出成功！");
        } catch (BusinessException be) {
            logger.debug("业务异常" + be.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("获取当前用户信息失败！");
        } catch (Exception e) {
            logger.debug("系统异常"+e.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("获取当前用户信息失败！");
        }
        return JSON.toJSONString(baseResult);
    }
}
