package com.koala.kuaidi100.pojo;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * <p>
 * Title: TaskResponse.java
 * </p>
 * 
 * <p>
 * Description: 该实体类来自快递100提供的接口 
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
 * @date 2014-12-9
 * 
 * @version koala_b2b2c 2015
 */
public class TaskResponse {
	private static XStream xstream;
	private Boolean result;
	private String returnCode;
	private String message;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream();
			xstream.autodetectAnnotations(true);
			xstream.alias("orderResponse", TaskResponse.class);
		}
		return xstream;
	}

	public String toXml() {
		return "<?xml version='1.0' encoding='UTF-8'?>\r\n"
				+ getXStream().toXML(this);
	}

	public static TaskResponse fromXml(String sXml) {
		return (TaskResponse) getXStream().fromXML(sXml);
	}

	public static void main(String[] args) {
		TaskResponse req = new TaskResponse();
		req.setMessage("订阅成功");
		req.setResult(true);
		req.setReturnCode("200");
		System.out.print(req.toXml());
	}

}
