package com.koala.foundation.domain.virtual;

/**
 * 
 * <p>
 * Title: TransContent.java
 * </p>
 * 
 * <p>
 * Description: 快递返回值的详细信息，该类不对应任何数据表，用在解析快递接口数据使用
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * 
 * <p>
 * Company: 沈阳网之商科技有限公司 www.koala.com
 * </p>
 * 
 * @author erikzhang
 * 
 * @date 2014-5-26
 * 
 * @version koala_b2b2c v2.0 2015版 
 */
public class TransContent {
	private String time;// 快递处理时间
	private String context;// 快递处理的详细信息

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
