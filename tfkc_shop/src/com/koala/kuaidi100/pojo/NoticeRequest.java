package com.koala.kuaidi100.pojo;

import com.koala.kuaidi100.post.JacksonHelper;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * <p>
 * Title: NoticeRequest.java
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
public class NoticeRequest {
	private static XStream xstream;

	private String status = "";
	private String billstatus = "";
	private String message = "";
	private Result lastResult = new Result();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result getLastResult() {
		return lastResult;
	}

	public void setLastResult(Result lastResult) {
		this.lastResult = lastResult;
	}

	private static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream();
			xstream.autodetectAnnotations(true);
			xstream.alias("pushRequest", NoticeRequest.class);
			xstream.alias("item", ResultItem.class);

		}
		return xstream;
	}

	public String toXml() {
		return "<?xml version='1.0' encoding='UTF-8'?>\r\n"
				+ getXStream().toXML(this);
	}

	public static NoticeRequest fromXml(String sXml) {
		return (NoticeRequest) getXStream().fromXML(sXml);
	}

	public static void main(String[] args) {
		NoticeRequest req = new NoticeRequest();
		req.setBillstatus("polling");
		req.setMessage("到达");
		req.setStatus("check");
		req.getLastResult().setCom("yauntong");
		req.getLastResult().setCondition("F00");
		req.getLastResult().setIscheck("0");
		req.getLastResult().setNu("V030344422");
		req.getLastResult().setState("0");
		req.getLastResult().setStatus("200");
		req.getLastResult().setMessage("ok");
		ResultItem item = new ResultItem();
		item.setContext("上海分拨中心/装件入车扫描 ");
		item.setFtime("2012-08-28 16:33:19");
		item.setTime("2012-08-28 16:33:19");
		req.getLastResult().getData().add(item);
		item = new ResultItem();
		item.setContext("上海分拨中心/下车扫描");
		item.setFtime("2012-08-27 23:22:42");
		item.setTime("2012-08-27 23:22:42");
		req.getLastResult().getData().add(item);
		System.out.println(JacksonHelper.toJSON(req));
	}

}
