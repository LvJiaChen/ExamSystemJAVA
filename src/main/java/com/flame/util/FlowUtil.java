package com.flame.util;

import java.util.Date;

import com.flame.base.util.DateUtil;


public final class FlowUtil {
	
	/**
	 * 生成指定长度编码
	 * @param lenth 标识
	 * @param no 序列
	 * @return 编码
	 */
	public static String getConstantCode(int lenth , Long no) {
		return  String.format("%1$0"+lenth+"d", no);
	}
	
	/**
	 * 生成日期编码
	 * @param format 日期格式
	 * @return  编码
	 */
	public static String getDateCode(String format) {
		return  DateUtil.format(new Date(), format);
	}
	
	/**
	 * 生成默认编码
	 * @param no 号码
	 */
	public static String getDefaultCode(Long no) {
		return  DateUtil.format(new Date(), DateUtil.yyyyMMdd) + String.format("%1$08d", no);
	}
}
