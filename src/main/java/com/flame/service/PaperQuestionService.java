package com.flame.service;

import com.flame.base.service.BaseService;
import com.flame.entity.PaperQuestion;

/**
 * 服务接口。
 */
public interface PaperQuestionService extends BaseService<PaperQuestion, Long> {

	void deleteQuestFixedPaper(PaperQuestion paperQuestion);

}
