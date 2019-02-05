package com.flame.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.flame.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flame.base.controller.BasicController;
import com.flame.base.dto.BaseResult;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.entity.ExamQuestion;
import com.flame.entity.Paper;
import com.flame.entity.StudentPaper;
import com.flame.service.AdminService;
import com.flame.service.PaperService;

@Controller
@RequestMapping("PaperController")
public class PaperController extends BasicController {
    @Resource
    private PaperService paperService;
    @Resource
    private AdminService adminService;

    // -----------试卷后台---------
    // 查询试卷
    @ResponseBody
    @RequestMapping(value = "findPaper", method = RequestMethod.POST)
    public Object findPaper(PaperPageDto paperDto) {
        BaseResult<DataGrid<PaperDto>> baseResult = new BaseResult<DataGrid<PaperDto>>();
        try {
            Integer pageSize = null;
            Integer currentPage = null;
            Page page = new Page();
            if (paperDto.getPageSize() != null || paperDto.getPageSize() != null) {
                pageSize = paperDto.getPageSize();
                currentPage = paperDto.getCurrentPage();
                page.setLimit(pageSize);
                page.setOffset((currentPage - 1) * pageSize);
            }
            Params params = Params.create();
            params.add("entity", paperDto);
            DataGrid<PaperDto> grid = paperService.findPaper(params, page);
            for (PaperDto pageDto : grid.getItems()) {
                if (pageDto.getIsRandom() == true) {
                    pageDto.setIsRandomStr("随机试卷");
                } else {
                    pageDto.setIsRandomStr("固定试卷");
                }
                Date[] arr=new Date[2];
                arr[0]=pageDto.getPaperStartTime();
                arr[1]=pageDto.getPaperEndTime();
                pageDto.setTestDate(arr);
            }
            baseResult.setModel(grid);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return JSON.toJSONString(baseResult);
    }

    // 批量删除试卷
    @ResponseBody
    @RequestMapping("deletePaper")
    public Object deletePaper(String ids) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                paperService.delete(Long.parseLong(id[i]));
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

    // 插入试卷对象
    @ResponseBody
    @RequestMapping(value = "generatePaperObject", method = RequestMethod.POST)
    public Object generatePaperObject(HttpServletRequest request, @Valid Paper paper, BindingResult bindingResult) {
        BaseResult<Paper> baseResult = new BaseResult<Paper>();
        try {
            // 插入adminId
            // Admin user=new Admin();
            // user.setTeacherNo((String)request.getSession().getAttribute("user"));
            // Admin admin=adminService.selectByName(user);
            // paper.setAdminId(admin.getId());
            this.vaildParamsDefault(baseResult, bindingResult);
            Paper paper2 = paperService.selectPaperByName(paper.getPaperName());
            if (paper2 != null) {
                throw new BusinessException("该试卷名已经存在");
            }
            paperService.insert(paper);
            Paper paper3 = paperService.selectPaperByName(paper.getPaperName());
            baseResult.setMessage("插入试卷成功");
            baseResult.setModel(paper3);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            e.printStackTrace();
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 编辑试卷
    @ResponseBody
    @RequestMapping("updatePaper")
    public Object updatePaper(@Valid Paper paper, BindingResult bindingResult) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            this.vaildParamsDefault(baseResult, bindingResult);
            paperService.update(paper);
            baseResult.setModel("更新成功");
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

    // 批阅主观题
    // 选择批阅试卷
    @ResponseBody
    @RequestMapping(value = "selectReadPaper", method = RequestMethod.POST)
    public Object selectReadPaper() {
        BaseResult<List<Paper>> baseResult = new BaseResult<List<Paper>>();
        try {
            Params params = Params.create();
            List<Paper> paper = paperService.selectReadPaper(params);
            baseResult.setModel(paper);
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

    // 批阅题目返给前台
    @ResponseBody
    @RequestMapping(value = "selectReadQuest", method = RequestMethod.POST)
    public Object selectReadQuest(Paper paper) {
        BaseResult<List<ExamQuestionDto>> baseResult = new BaseResult<List<ExamQuestionDto>>();
        try {
            List<ExamQuestionDto> questions = paperService.selectReadQuest(paper);
            baseResult.setMessage("查询成功");
            baseResult.setModel(questions);
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

    // 更新分数，得最终分数
    //参数：studentId，paperId，sumScort
    @ResponseBody
    @RequestMapping(value = "getSumScort", method = RequestMethod.POST)
    public Object getSumScort(QuestionSumDto questionSumDto) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            paperService.getSumScort(questionSumDto);
            baseResult.setModel("提交成功");
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

    /********************************** 试卷前台 *************************************************/
    // 生成试卷
    @ResponseBody
    @RequestMapping(value = "generatePapers", method = RequestMethod.POST)
    public Object generatePapers(PaperDto paperDto) {
        BaseResult<Map<String, Object>> baseResult = new BaseResult<Map<String, Object>>();
        try {
            Map<String, Object> Map = paperService.generatePapers(paperDto);
            baseResult.setModel(Map);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    /**
     * 学生提交答案并 批阅单选题，多选题，判断题(客观题)
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "submitPapers", method = RequestMethod.POST)
    public Object submitPapers(@RequestBody StudentPaperDto studentPaperDto) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            paperService.submitPapers(studentPaperDto);
            baseResult.setMessage("试卷已提交");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

}
