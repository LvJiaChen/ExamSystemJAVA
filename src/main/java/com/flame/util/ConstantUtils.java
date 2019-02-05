package com.flame.util;
/**
 * 常量配置
 * @author Administrator
 *
 */
public class ConstantUtils {
	private ConstantUtils() {
	}
	public interface QuestionType {
		String SINGLECHOICE = "singleChoice";
		String MULTIPLECHOICE = "multipleChoice";
		String TRUEORFALSE = "trueOrFalse";
		String BLANKQUESTION = "blankQuestion";
		String QUESTIONSANDANSWER = "questionsAndAnswer";
	}
}
