package com.flame.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flame.base.dao.BaseDao;
import com.flame.base.exception.BusinessException;
import com.flame.base.model.DataGrid;
import com.flame.base.model.Page;
import com.flame.base.model.Params;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.ExamQuestionDao;
import com.flame.dao.PaperDao;
import com.flame.dao.PaperQuestionDao;
import com.flame.dao.StudentPaperDao;
import com.flame.dao.StudentScortDao;
import com.flame.dto.ExamQuestionDto;
import com.flame.dto.PaperDto;
import com.flame.dto.QuestionSumDto;
import com.flame.dto.StudentPaperDto;
import com.flame.entity.ExamQuestion;
import com.flame.entity.Paper;
import com.flame.entity.PaperQuestion;
import com.flame.entity.Student;
import com.flame.entity.StudentPaper;
import com.flame.entity.StudentScort;
import com.flame.service.PaperService;
import com.flame.util.ConstantUtils.QuestionType;

@Service("paperService")
public class PaperServiceImpl extends BaseServiceImpl<Paper, Long> implements PaperService {

    @Resource
    private PaperDao paperDao;
    @Resource
    private PaperQuestionDao paperQuestionDao;
    @Resource
    private ExamQuestionDao examQuestionDao;
    @Resource
    private StudentPaperDao studentPaperDao;
    @Resource
    private StudentScortDao studentScortDao;

    @Override
    protected BaseDao<Paper, Long> getBaseDao() {
        return this.paperDao;
    }

    // 查询题目
    @Override
    public DataGrid<PaperDto> findPaper(Params params, Page page) {
        return paperDao.findPaper(params, page);
    }

    @Override
    public Map<String, Object> generatePapers(PaperDto paperDto) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Paper paper = paperDao.get(paperDto.getPaperId());
        if (paper != null) {
            if (paper.getIsRandom()) {
                if (paper.getSingleChoiceNum() != 0 && paper.getSingleChoiceNum() != null) {
                    List<ExamQuestionDto> singleChoicesSum = paperDao.selestSingleChoices(paper);
                    for (ExamQuestionDto examQuestion : singleChoicesSum) {
                        examQuestion.setScort(paper.getSingleChoiceScort());
                        List<String> options = new ArrayList<String>();
                        if (examQuestion.getOptionA() != null) {
                            options.add(examQuestion.getOptionA());
                        }
                        if (examQuestion.getOptionB() != null) {
                            options.add(examQuestion.getOptionB());
                        }
                        if (examQuestion.getOptionC() != null) {
                            options.add(examQuestion.getOptionC());
                        }
                        if (examQuestion.getOptionD() != null) {
                            options.add(examQuestion.getOptionD());
                        }
                        if (examQuestion.getOptionE() != null) {
                            options.add(examQuestion.getOptionE());
                        }
                        if (examQuestion.getOptionF() != null) {
                            options.add(examQuestion.getOptionF());
                        }
                        examQuestion.setOptions(options);
                    }
                    List<ExamQuestion> singleChoices = new ArrayList<ExamQuestion>();
                    Integer[] indexs = new Integer[paper.getSingleChoiceNum().intValue()];
                    int flag = 0;
                    for (Long i = 0L; i < paper.getSingleChoiceNum(); i++) {
                        int index = (int) (Math.random() * singleChoicesSum.size());
                        int j = 0;
                        for (j = 0; j < i; j++) {
                            if (indexs[j] == index) {
                                i--;
                            } else {
                                indexs[flag] = index;
                                flag++;
                                if (flag == paper.getSingleChoiceNum()) {
                                    i = paper.getSingleChoiceNum();
                                    j = paper.getSingleChoiceNum().intValue();
                                }
                            }
                        }
                        if (i == 0 && j == 0) {
                            indexs[i.intValue()] = index;
                            flag++;
                        }
                    }
                    for (Integer i : indexs) {
                        if (singleChoicesSum.size() > 0) {
                            singleChoices.add(singleChoicesSum.get(i));
                        }
                    }
                    dataMap.put(QuestionType.SINGLECHOICE, singleChoices);
                }
                if (paper.getMultipleChoiceNum() != 0 && paper.getMultipleChoiceNum() != null) {
                    List<ExamQuestionDto> multipleChoicesSum = paperDao.selestMultipleChoices(paper);
                    for (ExamQuestionDto examQuestion : multipleChoicesSum) {
                        examQuestion.setScort(paper.getMultipleChoiceScort());
                        List<String> options = new ArrayList<String>();
                        if (examQuestion.getOptionA() != null) {
                            options.add(examQuestion.getOptionA());
                        }
                        if (examQuestion.getOptionB() != null) {
                            options.add(examQuestion.getOptionB());
                        }
                        if (examQuestion.getOptionC() != null) {
                            options.add(examQuestion.getOptionC());
                        }
                        if (examQuestion.getOptionD() != null) {
                            options.add(examQuestion.getOptionD());
                        }
                        if (examQuestion.getOptionE() != null) {
                            options.add(examQuestion.getOptionE());
                        }
                        if (examQuestion.getOptionF() != null) {
                            options.add(examQuestion.getOptionF());
                        }
                        examQuestion.setOptions(options);
                    }
                    List<ExamQuestion> multipleChoices = new ArrayList<ExamQuestion>();
                    Integer[] indexs = new Integer[paper.getMultipleChoiceNum().intValue()];
                    int flag = 0;
                    for (Long i = 0L; i < paper.getMultipleChoiceNum(); i++) {
                        int index = (int) (Math.random() * multipleChoicesSum.size());
                        int j = 0;
                        for (j = 0; j < i; j++) {
                            if (indexs[j] == index) {
                                i--;
                            } else {
                                indexs[flag] = index;
                                flag++;
                                if (flag == paper.getMultipleChoiceNum()) {
                                    i = paper.getMultipleChoiceNum();
                                    j = paper.getMultipleChoiceNum().intValue();
                                }
                            }
                        }
                        if (i == 0 && j == 0) {
                            indexs[i.intValue()] = index;
                            flag++;
                        }
                    }
                    for (Integer i : indexs) {
                        if (multipleChoicesSum.size() > 0) {
                            multipleChoices.add(multipleChoicesSum.get(i));
                        }
                    }
                    dataMap.put(QuestionType.MULTIPLECHOICE, multipleChoices);

                }
                if (paper.getTrueOrFalseNum() != 0 && paper.getTrueOrFalseNum() != null) {
                    List<ExamQuestion> trueOrFalsesSum = paperDao.selestTrueOrFalses(paper);
                    for (ExamQuestion examQuestion : trueOrFalsesSum) {
                        examQuestion.setScort(paper.getTrueOrFalseScort());
                    }
                    List<ExamQuestion> trueOrFalses = new ArrayList<ExamQuestion>();
                    Integer[] indexs = new Integer[paper.getTrueOrFalseNum().intValue()];
                    int flag = 0;
                    for (Long i = 0L; i < paper.getTrueOrFalseNum(); i++) {
                        int index = (int) (Math.random() * trueOrFalsesSum.size());
                        int j = 0;
                        for (j = 0; j < i; j++) {
                            if (indexs[j] == index) {
                                i--;
                            } else {
                                indexs[flag] = index;
                                flag++;
                                if (flag == paper.getTrueOrFalseNum()) {
                                    i = paper.getTrueOrFalseNum();
                                    j = paper.getTrueOrFalseNum().intValue();
                                }
                            }
                        }
                        if (i == 0 && j == 0) {
                            indexs[i.intValue()] = index;
                            flag++;
                        }
                    }
                    for (Integer i : indexs) {
                        if (trueOrFalsesSum.size() > 0) {
                            trueOrFalses.add(trueOrFalsesSum.get(i));
                        }
                    }
                    dataMap.put(QuestionType.TRUEORFALSE, trueOrFalses);
                }
                if (paper.getBlankQuestionNum() != 0 && paper.getBlankQuestionNum() != null) {
                    List<ExamQuestion> blankQuestionsSum = paperDao.selestBlankQuestions(paper);
                    for (ExamQuestion examQuestion : blankQuestionsSum) {
                        examQuestion.setScort(paper.getBlankQuestionScort());
                    }
                    List<ExamQuestion> blankQuestions = new ArrayList<ExamQuestion>();
                    Integer[] indexs = new Integer[paper.getBlankQuestionNum().intValue()];
                    int flag = 0;
                    for (Long i = 0L; i < paper.getBlankQuestionNum(); i++) {
                        int index = (int) (Math.random() * blankQuestionsSum.size());
                        int j = 0;
                        for (j = 0; j < i; j++) {
                            if (indexs[j] == index) {
                                i--;
                            } else {
                                indexs[flag] = index;
                                flag++;
                                if (flag == paper.getBlankQuestionNum()) {
                                    i = paper.getBlankQuestionNum();
                                    j = paper.getBlankQuestionNum().intValue();
                                }
                            }
                        }
                        if (i == 0 && j == 0) {
                            indexs[i.intValue()] = index;
                            flag++;
                        }
                    }
                    for (Integer i : indexs) {
                        if (blankQuestionsSum.size() > 0) {
                            blankQuestions.add(blankQuestionsSum.get(i));
                        }
                    }
                    dataMap.put(QuestionType.BLANKQUESTION, blankQuestions);
                }
                if (paper.getQuestionsAndAnswersNum() != 0 && paper.getQuestionsAndAnswersNum() != null) {
                    List<ExamQuestion> questionsAndAnswersSum = paperDao.selestQuestionsAndAnswers(paper);
                    List<ExamQuestion> questionsAndAnswers = new ArrayList<ExamQuestion>();
                    Integer[] indexs = new Integer[paper.getQuestionsAndAnswersNum().intValue()];
                    int flag = 0;
                    for (Long i = 0L; i < paper.getQuestionsAndAnswersNum(); i++) {
                        int index = (int) (Math.random() * questionsAndAnswersSum.size());
                        int j = 0;
                        for (j = 0; j < i; j++) {
                            if (indexs[j] == index) {
                                i--;
                            } else {
                                indexs[flag] = index;
                                flag++;
                                if (flag == paper.getQuestionsAndAnswersNum()) {
                                    i = paper.getQuestionsAndAnswersNum();
                                    j = paper.getQuestionsAndAnswersNum().intValue();
                                }
                            }
                        }
                        if (i == 0 && j == 0) {
                            indexs[i.intValue()] = index;
                            flag++;
                        }
                    }
                    String[] scort = paper.getQuestionsAndAnswersScorts().split(",");
                    for (Integer i : indexs) {
                        if (questionsAndAnswersSum.size() > 0) {
                            questionsAndAnswers.add(questionsAndAnswersSum.get(i));
                        }
                    }
                    for (int i = 0; i < questionsAndAnswers.size(); i++) {
                        questionsAndAnswers.get(i).setScort(new BigDecimal(scort[i]));
                    }
                    dataMap.put(QuestionType.QUESTIONSANDANSWER, questionsAndAnswers);
                }
            } else {
                List<ExamQuestionDto> examQuestions = paperQuestionDao.getQuestionIds(paperDto.getPaperId());
                List<ExamQuestionDto> singleChoices = new ArrayList<ExamQuestionDto>();
                List<ExamQuestionDto> multipleChoices = new ArrayList<ExamQuestionDto>();
                List<ExamQuestionDto> trueOrFalses = new ArrayList<ExamQuestionDto>();
                List<ExamQuestionDto> blankQuestions = new ArrayList<ExamQuestionDto>();
                List<ExamQuestionDto> questionsAndAnswers = new ArrayList<ExamQuestionDto>();
                for (ExamQuestionDto examQuestion : examQuestions) {
                    switch (examQuestion.getQuestionType()) {
                        case "单选题":
                            List<String> options = new ArrayList<String>();
                            if (examQuestion.getOptionA() != null) {
                                options.add(examQuestion.getOptionA());
                            }
                            if (examQuestion.getOptionB() != null) {
                                options.add(examQuestion.getOptionB());
                            }
                            if (examQuestion.getOptionC() != null) {
                                options.add(examQuestion.getOptionC());
                            }
                            if (examQuestion.getOptionD() != null) {
                                options.add(examQuestion.getOptionD());
                            }
                            if (examQuestion.getOptionE() != null) {
                                options.add(examQuestion.getOptionE());
                            }
                            if (examQuestion.getOptionF() != null) {
                                options.add(examQuestion.getOptionF());
                            }
                            examQuestion.setOptions(options);
                            singleChoices.add(examQuestion);
                            break;
                        case "多选题":
                            List<String> options1 = new ArrayList<String>();
                            if (examQuestion.getOptionA() != null) {
                                options1.add(examQuestion.getOptionA());
                            }
                            if (examQuestion.getOptionB() != null) {
                                options1.add(examQuestion.getOptionB());
                            }
                            if (examQuestion.getOptionC() != null) {
                                options1.add(examQuestion.getOptionC());
                            }
                            if (examQuestion.getOptionD() != null) {
                                options1.add(examQuestion.getOptionD());
                            }
                            if (examQuestion.getOptionE() != null) {
                                options1.add(examQuestion.getOptionE());
                            }
                            if (examQuestion.getOptionF() != null) {
                                options1.add(examQuestion.getOptionF());
                            }
                            examQuestion.setOptions(options1);
                            multipleChoices.add(examQuestion);
                            break;
                        case "填空题":
                            blankQuestions.add(examQuestion);
                            break;
                        case "判断题":
                            trueOrFalses.add(examQuestion);
                            break;
                        case "问答题":
                            questionsAndAnswers.add(examQuestion);
                            break;
                    }
                }
                for (ExamQuestionDto questionDto : singleChoices) {
                    questionDto.setScort(paper.getSingleChoiceScort());
                }
                for (ExamQuestionDto questionDto : multipleChoices) {
                    questionDto.setScort(paper.getMultipleChoiceScort());
                }
                for (ExamQuestionDto questionDto : trueOrFalses) {
                    questionDto.setScort(paper.getTrueOrFalseScort());
                }
                for (ExamQuestionDto questionDto : blankQuestions) {
                    questionDto.setScort(paper.getBlankQuestionScort());
                }
                String[] s= paper.getQuestionsAndAnswersScorts().split(",");
                for (int i=0;i<questionsAndAnswers.size();i++){
                    questionsAndAnswers.get(i).setScort(new BigDecimal(s[i]));
                }
                dataMap.put(QuestionType.SINGLECHOICE, singleChoices);

                dataMap.put(QuestionType.MULTIPLECHOICE, multipleChoices);

                dataMap.put(QuestionType.TRUEORFALSE, trueOrFalses);

                dataMap.put(QuestionType.BLANKQUESTION, blankQuestions);

                dataMap.put(QuestionType.QUESTIONSANDANSWER, questionsAndAnswers);

            }
        } else {
            throw new BusinessException("试卷Id不存在");
        }
        return dataMap;

    }

    @Override
    public List<PaperDto> selectParer(Params params) {
        return paperDao.selectParer(params);
    }

    @Override
    public List<PaperDto> selectFixedPaper(Params params) {
        List<PaperDto> papers = paperDao.selectParer(params);
        for (PaperDto paper : papers) {
            List<PaperQuestion> paperQuestions = paperQuestionDao.selectQuestIdByPaperId(paper.getId());
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
            paper.setSingleChoiceNumCurrent(singleChoiceNum);
            paper.setMultipleChoiceNumCurrent(multipleChoiceNum);
            paper.setTrueOrFalseNumCurrent(trueOrFalseNum);
            paper.setBlankQuestionNumCurrent(blankQuestionNum);
            paper.setQuestionsAndAnswersNumCurrent(questionsAndAnswersNum);
        }
        return papers;
    }

    @Override
    public void submitPapers(StudentPaperDto studentPaperDto) {
        BigDecimal sumScort = new BigDecimal(0);
        StudentScort studentScort = new StudentScort();
        for (StudentPaper studentPaper : studentPaperDto.getStudentPaper()) {
            ExamQuestion examQuestion = examQuestionDao.selectObjectiveQuestions(studentPaper.getQuestionId());
            if (examQuestion != null) {
                if (examQuestion.getAnswer().equals(studentPaper.getAnswer())) {
                    studentPaper.setSumScort(studentPaper.getQuestionScort());
                } else {
                    studentPaper.setSumScort(new BigDecimal("0"));
                }
                studentPaper.setIsCheck(true);
                sumScort = sumScort.add(studentPaper.getSumScort());
            } else {
                studentPaper.setIsCheck(false);
            }
            studentPaperDao.insert(studentPaper);
        }
        studentScort.setPaperId(studentPaperDto.getStudentPaper().get(0).getPaperId());
        studentScort.setStudentId(studentPaperDto.getStudentPaper().get(0).getStudentId());
        studentScort.setSumScort(sumScort);
        studentScort.setIsComplete(false);
        studentScortDao.insert(studentScort);
    }

    @Override
    public List<Paper> selectReadPaper(Params params) {
        return paperDao.selectReadPaper(params);
    }

    @Override
    public List<ExamQuestionDto> selectReadQuest(Paper paper) {
        List<Student> students = studentPaperDao.selectStudentId(paper.getId());
        if (students.size() > 0) {
            Params params = Params.create();
            params.add("paperId", paper.getId());
            params.add("studentId", students.get(0).getId());
            List<ExamQuestionDto> questions = studentPaperDao.selectQuestion(params);
            for (ExamQuestionDto q : questions) {
                q.setStudentId(students.get(0).getId());
            }
            return questions;
        } else {
            throw new BusinessException("该试卷已经批阅完成");
        }
    }

    @Override
    @Transactional
    public void getSumScort(QuestionSumDto questionSumDto) {
        //通过学生Id，试卷Id查询学生分数表
        StudentScort studentScort = studentScortDao.selectSumScort(questionSumDto);
        studentScort.setIsComplete(true);
        studentScort.setSumScort(studentScort.getSumScort().add(questionSumDto.getSumScort()));
        //通过学生ID试卷Id查询学生试卷表
        List<StudentPaper> studentPaper = studentPaperDao.selectByPaperIdStudentId(questionSumDto);
        for (StudentPaper sPaper : studentPaper) {
            sPaper.setIsCheck(true);
            studentPaperDao.update(sPaper);
        }
        studentScortDao.update(studentScort);
    }

    @Override
    public Paper selectPaperByName(String paperName) {
        return paperDao.selectPaperByName(paperName);
    }

}
