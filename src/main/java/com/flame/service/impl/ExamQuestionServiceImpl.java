package com.flame.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.flame.base.dao.BaseDao;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.ExamQuestionDao;
import com.flame.dao.PaperDao;
import com.flame.dao.PaperQuestionDao;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.dto.PaperQuestionDto;
import com.flame.entity.ExamQuestion;
import com.flame.entity.Paper;
import com.flame.entity.PaperQuestion;
import com.flame.service.ExamQuestionService;
import com.flame.util.ExcelUtil;

@Service("examQuestionService")
public class ExamQuestionServiceImpl extends BaseServiceImpl<ExamQuestion, Long> implements ExamQuestionService {

    @Resource
    private ExamQuestionDao examQuestionDao;
    @Resource
    private PaperDao paperDao;
    @Resource
    private PaperQuestionDao paperQuestionDao;

    @Override
    protected BaseDao<ExamQuestion, Long> getBaseDao() {
        return this.examQuestionDao;
    }

    @Override
    public DataGrid<ExamQuestionDto> findQuestion(Params params, Page page) {
        return examQuestionDao.findQuestion(params, page);
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
        List<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>();
        // 遍历listob数据，把数据放到List中
        for (int i = 0; i < listob.size(); i++) {
            List<Object> ob = listob.get(i);
            ExamQuestion examQuestion = new ExamQuestion();
            // 通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
            if (ob.get(0) != null) {
                examQuestion.setQuestionType(String.valueOf(ob.get(0)));
            } else {
                throw new BusinessException("题目类型不能为空");
            }
            if (ob.get(1) != null) {
                examQuestion.setQuestionClaim(String.valueOf(ob.get(1)));
            } else {
                throw new BusinessException("题目要求不能为空");
            }
            if (ob.get(2) != null) {
                examQuestion.setQuestionContent(String.valueOf(ob.get(2)));
            } else {
                throw new BusinessException("题目内容不能为空");
            }
            if (ob.get(3) != null) {
                examQuestion.setOptionA(String.valueOf(ob.get(3)));
            }
            if (ob.get(4) != null) {
                examQuestion.setOptionB(String.valueOf(ob.get(4)));
            }
            if (ob.get(5) != null) {
                examQuestion.setOptionC(String.valueOf(ob.get(5)));
            }
            if (ob.get(6) != null) {
                examQuestion.setOptionD(String.valueOf(ob.get(6)));
            }
            if (ob.get(7) != null) {
                examQuestion.setOptionE(String.valueOf(ob.get(7)));
            }
            if (ob.get(8) != null) {
                examQuestion.setOptionF(String.valueOf(ob.get(8)));
            }
            if (ob.get(9) != null) {
                examQuestion.setAnswer(String.valueOf(ob.get(9)));
            } else {
                throw new BusinessException("题目答案不能为空");
            }
            if (ob.get(10) != null) {
                examQuestion.setSubject(String.valueOf(ob.get(10)));
            } else {
                throw new BusinessException("题目学科不能为空");
            }
            if (ob.get(11) != null) {
                // object类型转Double类型
                examQuestion.setScort(new BigDecimal(ob.get(11).toString()));
            }
            if (examQuestion.getOptionA() == "") {
                examQuestion.setOptionA(null);
            }
            if (examQuestion.getOptionB() == "") {
                examQuestion.setOptionB(null);
            }
            if (examQuestion.getOptionC() == "") {
                examQuestion.setOptionC(null);
            }
            if (examQuestion.getOptionD() == "") {
                examQuestion.setOptionD(null);
            }

            if (examQuestion.getOptionE() == "") {
                examQuestion.setOptionE(null);
            }
            if (examQuestion.getOptionF() == "") {
                examQuestion.setOptionF(null);
            }
            examQuestions.add(examQuestion);
        }
        // 批量插入
        examQuestionDao.importExcel(examQuestions);
    }

    @Override
    @Transactional
    public PaperDto addQuestFixedPaper(PaperQuestionDto paperQuestion) {
        Paper paper = paperDao.get(paperQuestion.getPaperQuestion().get(0).getPaperId());
        List<PaperQuestion> paperQuestions = paperQuestionDao
                .selectQuestIdByPaperId(paperQuestion.getPaperQuestion().get(0).getPaperId());
        // 已插入的题目
        List<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>();
        // 准备插入的题目
        List<ExamQuestion> examQuestions2 = new ArrayList<ExamQuestion>();
        for (PaperQuestion question : paperQuestions) {
            ExamQuestion q = examQuestionDao.get(question.getQuestionId());
            examQuestions.add(q);
        }
        for (PaperQuestion question : paperQuestion.getPaperQuestion()) {
            ExamQuestion q = examQuestionDao.get(question.getQuestionId());
            examQuestions2.add(q);
        }
        Long singleChoiceNum = new Long(0);
        Long multipleChoiceNum = new Long(0);
        Long trueOrFalseNum = new Long(0);
        Long blankQuestionNum = new Long(0);
        Long questionsAndAnswersNum = new Long(0);
        for (ExamQuestion question : examQuestions) {
            // 计算出当前已插入的题目数量
            if (question.getQuestionType().equals("单选题")) {
                singleChoiceNum++;
            }
            if (question.getQuestionType().equals("多选题")) {
                multipleChoiceNum++;
            }
            if (question.getQuestionType().equals("判断题")) {
                trueOrFalseNum++;
            }
            if (question.getQuestionType().equals("填空题")) {
                blankQuestionNum++;
            }
            if (question.getQuestionType().equals("问答题")) {
                questionsAndAnswersNum++;
            }

        }
        for (ExamQuestion question : examQuestions2) {
            if (!question.getSubject().equals(paper.getSubject())) {
                throw new BusinessException("您所插入的题目学科与试卷的学科不一致，请重新选择");
            }
            // 计算出当前已插入的题目数量
            if (question.getQuestionType().equals("单选题")) {
                singleChoiceNum++;
            }
            if (question.getQuestionType().equals("多选题")) {
                multipleChoiceNum++;
            }
            if (question.getQuestionType().equals("判断题")) {
                trueOrFalseNum++;
            }
            if (question.getQuestionType().equals("填空题")) {
                blankQuestionNum++;
            }
            if (question.getQuestionType().equals("问答题")) {
                questionsAndAnswersNum++;
            }
            if (singleChoiceNum > paper.getSingleChoiceNum()) {
                throw new BusinessException("该试卷单选题数量已满足，请添加其他题目类型");

            }
            if (multipleChoiceNum > paper.getMultipleChoiceNum()) {
                throw new BusinessException("该试卷多选题数量已满足，请添加其他题目类型");

            }
            if (trueOrFalseNum > paper.getTrueOrFalseNum()) {
                throw new BusinessException("该试卷判断题数量已满足，请添加其他题目类型");

            }
            if (blankQuestionNum > paper.getBlankQuestionNum()) {
                throw new BusinessException("该试卷填空题数量已满足，请添加其他题目类型");

            }
            if (questionsAndAnswersNum > paper.getQuestionsAndAnswersNum()) {
                throw new BusinessException("该试卷问答题数量已满足，请添加其他题目类型");
            }
            PaperQuestion paperQuestion2 = new PaperQuestion();
            paperQuestion2.setPaperId(paperQuestion.getPaperQuestion().get(0).getPaperId());
            paperQuestion2.setQuestionId(question.getId());
            paperQuestionDao.insert(paperQuestion2);
        }
        PaperDto paperDto = new PaperDto();
        paperDto.setSingleChoiceNumCurrent(singleChoiceNum);
        paperDto.setMultipleChoiceNumCurrent(multipleChoiceNum);
        paperDto.setBlankQuestionNumCurrent(blankQuestionNum);
        paperDto.setTrueOrFalseNumCurrent(trueOrFalseNum);
        paperDto.setQuestionsAndAnswersNumCurrent(questionsAndAnswersNum);
        return paperDto;
    }

    @Override
    public DataGrid<ExamQuestionDto> findQuestionForAdd(Params params, Page page) {
        return examQuestionDao.findQuestionForAdd(params, page);
    }

    @Override
    public PaperDto getCurrentNum(Paper paper) {
        Paper paper1 = paperDao.get(paper.getId());
        List<PaperQuestion> paperQuestions = paperQuestionDao
                .selectQuestIdByPaperId(paper.getId());
        // 已插入的题目
        List<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>();
        for (PaperQuestion question : paperQuestions) {
            ExamQuestion q = examQuestionDao.get(question.getQuestionId());
            examQuestions.add(q);
        }
        Long singleChoiceNum = new Long(0);
        Long multipleChoiceNum = new Long(0);
        Long trueOrFalseNum = new Long(0);
        Long blankQuestionNum = new Long(0);
        Long questionsAndAnswersNum = new Long(0);
        // 计算出当前已插入的题目数量
        for (ExamQuestion question : examQuestions) {
            if (question.getQuestionType().equals("单选题")) {
                singleChoiceNum++;
            }
            if (question.getQuestionType().equals("多选题")) {
                multipleChoiceNum++;
            }
            if (question.getQuestionType().equals("判断题")) {
                trueOrFalseNum++;
            }
            if (question.getQuestionType().equals("填空题")) {
                blankQuestionNum++;
            }
            if (question.getQuestionType().equals("问答题")) {
                questionsAndAnswersNum++;
            }
        }
        PaperDto paperDto = new PaperDto();
        paperDto.setSingleChoiceNumCurrent(singleChoiceNum);
        paperDto.setMultipleChoiceNumCurrent(multipleChoiceNum);
        paperDto.setBlankQuestionNumCurrent(blankQuestionNum);
        paperDto.setTrueOrFalseNumCurrent(trueOrFalseNum);
        paperDto.setQuestionsAndAnswersNumCurrent(questionsAndAnswersNum);
        paperDto.setSingleChoiceScort(paper1.getSingleChoiceScort().multiply(new BigDecimal(singleChoiceNum)));
        paperDto.setMultipleChoiceScort(paper1.getMultipleChoiceScort().multiply(new BigDecimal(multipleChoiceNum)));
        paperDto.setBlankQuestionScort(paper1.getBlankQuestionScort().multiply(new BigDecimal(blankQuestionNum)));
        paperDto.setTrueOrFalseScort(paper1.getTrueOrFalseScort().multiply(new BigDecimal(trueOrFalseNum)));
        Float f=0f;
        if (paper1.getQuestionsAndAnswersScorts()!=null&&!paper1.getQuestionsAndAnswersScorts().equals("")){
            String[] socre=paper1.getQuestionsAndAnswersScorts().split(",");
            for (String s:socre){
                f+= Float.parseFloat(s);
            }
        }
        paperDto.setQuestionsAndAnswersScorts(f+"");
        return paperDto;
    }
}
