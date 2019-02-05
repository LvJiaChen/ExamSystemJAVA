package com.flame.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flame.base.dao.BaseDao;
import com.flame.base.service.BaseServiceImpl;
import com.flame.dao.PaperQuestionDao;
import com.flame.entity.PaperQuestion;
import com.flame.service.PaperQuestionService;

@Service("paperQuestionService")
public class PaperQuestionServiceImpl extends BaseServiceImpl<PaperQuestion, Long> implements PaperQuestionService {

	@Resource
	private PaperQuestionDao paperQuestionDao;
 
	@Override
	protected BaseDao<PaperQuestion, Long> getBaseDao() {
		return this.paperQuestionDao;
	}

	@Override
	public void deleteQuestFixedPaper(PaperQuestion paperQuestion) {
		this.paperQuestionDao.deleteQuestFixedPaper(paperQuestion);
	}
}
