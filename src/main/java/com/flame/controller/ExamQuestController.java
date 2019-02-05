package com.flame.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.dto.PaperQuestionDto;
import com.flame.entity.Paper;
import com.flame.entity.PaperQuestion;
import com.flame.service.ExamQuestionService;
import com.flame.service.PaperQuestionService;
import com.flame.service.PaperService;

@Controller
@RequestMapping("ExamQuestController")
public class ExamQuestController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(ExamQuestController.class);
    @Resource
    private ExamQuestionService questionService;
    @Resource
    private PaperService paperService;
    @Resource
    private PaperQuestionService paperQuestionService;

    // ----------------后台题目管理---------------
    // 插入题目
    @ResponseBody
    @RequestMapping(value = "insertQuestion", method = RequestMethod.POST)
    public Object insertQuestion(@Valid ExamQuestionDto question, BindingResult bindingResult) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            this.vaildParamsDefault(baseResult, bindingResult);
            if (question.getQuestionContent() == null || question.getQuestionContent().equals("")) {
                baseResult.setMessage("题干不能为空");
                baseResult.setResult(false);
                return baseResult;
            }
            questionService.insert(question);
            baseResult.setMessage("插入题目成功");
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

    // Excel插入数据库
    @ResponseBody
    @RequestMapping(value = "importExcel", method = RequestMethod.POST)
    public Object importExcel(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            // 获取上传的文件
            if (!file.isEmpty()) {
                InputStream in = file.getInputStream();
                // 数据导入
                questionService.importExcel(in, file);
                in.close();
            }
            baseResult.setResult(true);
            baseResult.setMessage("导入成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
            return baseResult;
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setResult(false);
            baseResult.setMessage("导入失败");
        }
        return baseResult;
    }

    // 题目图片
    @ResponseBody
    @RequestMapping(value = "uploadPic", method = RequestMethod.POST)
    public Object uploadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            if (!file.isEmpty()) {
                String path = request.getServletContext().getRealPath("/images");
                String fileName = file.getOriginalFilename();
                File filepath = new File(path, fileName);
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                file.transferTo(new File(path + File.separator + fileName));
                baseResult.setModel(path + File.separator + fileName);
            }
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

    // 题目类型
    @ResponseBody
    @RequestMapping(value = "selectQuestionType")
    public Object selectQuestionType() {
        BaseResult<List<String>> baseResult = new BaseResult<List<String>>();
        try {
            List<String> list = new ArrayList<String>();
            list.add("单选题");
            list.add("多选题");
            list.add("判断题");
            list.add("填空题");
            list.add("问答题");
            baseResult.setModel(list);
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

    // 查询题目
    @ResponseBody
    @RequestMapping(value = "findQuestion", method = RequestMethod.POST)
    public Object findQuestion(ExamQuestionDto questionDto) {
        BaseResult<DataGrid<ExamQuestionDto>> baseResult = new BaseResult<DataGrid<ExamQuestionDto>>();
        try {
            Integer pageSize = null;
            Integer currentPage = null;
            Page page = new Page();
            if (questionDto.getPageSize() != null || questionDto.getPageSize() != null) {
                pageSize = questionDto.getPageSize();
                currentPage = questionDto.getCurrentPage();
                page.setLimit(pageSize);
                page.setOffset((currentPage - 1) * pageSize);
            }
            Params params = Params.create();
            params.add("entity", questionDto);
            DataGrid<ExamQuestionDto> grid = questionService.findQuestion(params, page);
            baseResult.setModel(grid);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    // 查询题目
    @ResponseBody
    @RequestMapping(value = "findQuestionForAdd", method = RequestMethod.POST)
    public Object findQuestionForAdd(ExamQuestionDto questionDto) {
        BaseResult<DataGrid<ExamQuestionDto>> baseResult = new BaseResult<DataGrid<ExamQuestionDto>>();
        try {
            Integer pageSize = null;
            Integer currentPage = null;
            Page page = new Page();
            if (questionDto.getPageSize() != null || questionDto.getPageSize() != null) {
                pageSize = questionDto.getPageSize();
                currentPage = questionDto.getCurrentPage();
                page.setLimit(pageSize);
                page.setOffset((currentPage - 1) * pageSize);
            }
            Paper paper = paperService.get(questionDto.getPaperId());
            questionDto.setSubject(paper.getSubject());
            Params params = Params.create();
            params.add("entity", questionDto);
            DataGrid<ExamQuestionDto> grid = questionService.findQuestionForAdd(params, page);
            baseResult.setModel(grid);
            baseResult.setResult(true);
        } catch (BusinessException e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

    //获得当前已插入题目数量
    @RequestMapping(value = "/getCurrentNum", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object getCurrentNum(Paper paper) {
        BaseResult<PaperDto> baseResult = new BaseResult<PaperDto>();
        try {
            PaperDto paperQuestionDto= questionService.getCurrentNum(paper);
            baseResult.setModel(paperQuestionDto);
            baseResult.setResult(true);
            baseResult.setMessage("查询成功！");
        } catch (BusinessException be) {
            logger.debug("业务异常" + be.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("删除失败！");
        } catch (Exception e) {
            logger.debug("系统异常"+e.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage("删除失败！");
        }
        return JSON.toJSONString(baseResult);
    }

    // 批量删除题目
    @ResponseBody
    @RequestMapping("deleteQuest")
    public Object deleteQuest(String ids) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                questionService.delete(Long.parseLong(id[i]));
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
    @RequestMapping("updateQuest")
    public Object updateQuest(@Valid ExamQuestionDto question, BindingResult bindingResult) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            this.vaildParamsDefault(baseResult, bindingResult);
            questionService.update(question);
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

    // 选择固定试卷
    @ResponseBody
    @RequestMapping("selectFixedPaper")
    public Object selectFixedPaper(Paper paper) {
        BaseResult<List<PaperDto>> baseResult = new BaseResult<List<PaperDto>>();
        try {
            Params params = Params.create();
            params.add("paperId", paper.getId());
            params.add("isRandom", false);
            List<PaperDto> papers = paperService.selectFixedPaper(params);
            baseResult.setModel(papers);
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

    // 向固定试卷中添加题目
    @ResponseBody
    @RequestMapping(value = "addQuestFixedPaper", method = RequestMethod.POST)
    public Object addQuestFixedPaper(@RequestBody PaperQuestionDto paperQuestion) {
        BaseResult<PaperDto> baseResult = new BaseResult<PaperDto>();
        try {
            PaperDto paper = questionService.addQuestFixedPaper(paperQuestion);
            baseResult.setModel(paper);
            baseResult.setMessage("添加成功");
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

    // 向固定试卷中删除题目
    @ResponseBody
    @RequestMapping(value = "deleteQuestFixedPaper", method = RequestMethod.POST)
    public Object deleteQuestFixedPaper(PaperQuestion paperQuestion) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            paperQuestionService.deleteQuestFixedPaper(paperQuestion);
            baseResult.setMessage("删除成功");
            baseResult.setResult(true);
        } catch (BusinessException e) {
            baseResult.setMessage(e.getMessage());
            baseResult.setResult(false);
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMessage("操作失败");
            baseResult.setResult(false);
        }
        return baseResult;
    }

}
