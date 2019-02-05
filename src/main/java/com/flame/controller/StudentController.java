package com.flame.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.flame.dto.*;
import com.flame.entity.Paper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.flame.base.controller.BasicController;
import com.flame.base.dto.BaseResult;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.util.MD5;
import com.flame.entity.Student;
import com.flame.service.PaperService;
import com.flame.service.StudentService;

@RequestMapping("/StudentController")
@Controller
public class StudentController extends BasicController {
    @Resource
    private StudentService studentService;
    @Resource
    private PaperService paperService;

    @RequestMapping(value = "/findStudentAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findStudentAll(StudentPageDto studentPageDto) {
        BaseResult<DataGrid<StudentPageDto>> baseResult = new BaseResult<DataGrid<StudentPageDto>>();
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
            DataGrid<StudentPageDto> grid = studentService.findStudentAll(params, page);
            baseResult.setModel(grid);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("查找学生失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("查找学生失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 添加学生
    @ResponseBody
    @RequestMapping(value = "insertStudent", method = RequestMethod.POST)
    public Object insertStudent(@Valid Student student, BindingResult bindingResult) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            this.vaildParamsDefault(baseResult, bindingResult);
            student.setPassword(MD5.md5(student.getPassword()));
            studentService.insert(student);
            baseResult.setModel("添加学生成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 批量删除管理员
    @ResponseBody
    @RequestMapping("deleteStudent")
    public Object deleteStudent(String ids) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                studentService.delete(Long.parseLong(id[i]));
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

    // 编辑题目，题目更新
    @ResponseBody
    @RequestMapping("updateStudent")
    public Object updateStudent(Student student) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            if (student.getId() == null) {
                baseResult.setMessage("学生Id不能为空");
                return baseResult;
            }
            studentService.update(student);
            baseResult.setModel("更新成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    /**
     * 学生前台
     */
    //选择试卷
    @ResponseBody
    @RequestMapping(value = "selectParer" ,method = {RequestMethod.POST, RequestMethod.GET})
    public Object selectParer() {
        BaseResult<List<PaperDto>> baseResult = new BaseResult<List<PaperDto>>();
        try {
            Params params = Params.create();
            params.add("newDate",new Date());
            List<PaperDto> paper = paperService.selectParer(params);
            baseResult.setModel(paper);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setResult(false);
            baseResult.setMessage(e.getMessage());
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return JSON.toJSONString(baseResult);
    }

    //个人成绩查询
    @ResponseBody
    @RequestMapping(value = "selectScore")
    public Object selectScore(HttpServletRequest request) {
        BaseResult<List<StudentScoreDto>> baseResult = new BaseResult<List<StudentScoreDto>>();
        try {
            Student student = new Student();
            //登入后解封
            student.setStuNo((String) request.getSession().getAttribute("studnet"));
            student.setStuNo("123456");
            //查询学生Id
            Student student2 = studentService.selectByStuNo(student);
            List<StudentScoreDto> list = studentService.selectScore(student2);
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

    //批量导入学生
    @ResponseBody
    @RequestMapping(value = "importExcel", method = RequestMethod.POST)
    public Object importExcel(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            // 获取上传的文件
            if (!file.isEmpty()) {
                InputStream in = file.getInputStream();
                // 数据导入
                studentService.importExcel(in, file);
                in.close();
            }
            baseResult.setResult(true);
            baseResult.setMessage("导入成功");
        } catch (BusinessException e) {
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setResult(false);
            baseResult.setMessage("导入失败");
        }
        return baseResult;
    }

    // 修改密码
    @ResponseBody
    @RequestMapping(value = "updatePassword")
    public Object updatePassword(HttpServletRequest request, StudentDto student) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            if (student.getStuNo() == null || student.getPassword() == null || student.getNewPassword() == null) {
                baseResult.setMessage("请输入账号和密码");
                baseResult.setResult(false);
                return baseResult;
            }
            Student student2 = studentService.selectByStuNo(student);
            if (!student2.getPassword().equals(MD5.md5(student.getPassword()))) {
                baseResult.setMessage("原密码或账号错误");
                baseResult.setResult(false);
                return baseResult;
            }
            student2.setPassword(MD5.md5(student.getNewPassword()));
            studentService.update(student2);
            baseResult.setMessage("修改成功");
            ;
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

    @ResponseBody
    @RequestMapping(value = "performanceAnalysis" ,method = {RequestMethod.POST, RequestMethod.GET})
    public Object performanceAnalysis(Paper paper) {
        BaseResult<List<AnalysisDto>> baseResult = new BaseResult<List<AnalysisDto>>();
        try {
            Params params = Params.create();
            params.add("paperId",paper.getId());
            List<AnalysisDto> analysisDto = studentService.performanceAnalysis(params);
            baseResult.setMessage("成绩查询成功");
            baseResult.setModel(analysisDto);
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

}
