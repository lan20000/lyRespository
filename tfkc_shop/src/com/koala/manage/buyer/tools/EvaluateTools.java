package com.koala.manage.buyer.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Evaluate;
import com.koala.foundation.domain.ExpressCompany;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.GoodsSpecProperty;
import com.koala.foundation.domain.IntegralGoodsCart;
import com.koala.foundation.domain.IntegralGoodsOrder;
import com.koala.foundation.domain.OrderForm;
import com.koala.foundation.domain.Store;
import com.koala.foundation.domain.SysConfig;
import com.koala.foundation.domain.virtual.TransContent;
import com.koala.foundation.domain.virtual.TransInfo;
import com.koala.foundation.service.IEvaluateService;
import com.koala.foundation.service.IExpressCompanyService;
import com.koala.foundation.service.IGoodsService;
import com.koala.foundation.service.IGoodsSpecPropertyService;
import com.koala.foundation.service.IIntegralGoodsOrderService;
import com.koala.foundation.service.IOrderFormService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserService;
import com.koala.kuaidi100.domain.ExpressInfo;
import com.koala.kuaidi100.service.IExpressInfoService;

/**
 * 
 * <p>
 * Title: MsgTools.java
 * </p>
 * 
 * <p>
 * Description: 评价工具类
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
 * @author hezeng
 * 
 * @date 2014-5-4
 * 
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Component
public class EvaluateTools {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderFormService orderFormService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsSpecPropertyService gspService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IExpressCompanyService expressCompanyService;
	@Autowired
	private IExpressInfoService expressInfoService;
	@Autowired
	private IIntegralGoodsOrderService integralGoodsOrderService;
	@Autowired
	private IEvaluateService evaluateService;

	/**
	 * 查询订单中某件是否评价
	 * 
	 * @param order_id
	 * @param goods_id
	 * @return
	 */
	public Evaluate query_order_evaluate(Object order_id, Object goods_id) {
		Map para = new HashMap();
		para.put("order_id", CommUtil.null2Long(order_id));
		para.put("goods_id", CommUtil.null2Long(goods_id));
		List<Evaluate> list = this.evaluateService
				.query("select obj from Evaluate obj where obj.evaluate_goods.id=:goods_id and obj.of.id=:order_id",
						para, -1, -1);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 判断是否可修改评价
	 * 
	 * @param date
	 * @return
	 */
	public int evaluate_able(Date date) {
		if (date != null) {
			long begin = date.getTime();
			long end = new Date().getTime();
			SysConfig config = this.configService.getSysConfig();
			long day = (end - begin) / 86400000;
			if (day <= config.getEvaluate_edit_deadline()) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 判断是否可追加评价
	 * 
	 * @param date
	 * @return
	 */
	public int evaluate_add_able(Date date) {
		if (date != null) {
			long begin = date.getTime();
			long end = new Date().getTime();
			SysConfig config = this.configService.getSysConfig();
			long day = (end - begin) / 86400000;
			if (day <= config.getEvaluate_add_deadline()) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 计算今天到指定时间天数
	 * 
	 * @param date
	 * @return
	 */
	public int how_soon(Date date) {
		if (date != null) {
			long begin = date.getTime();
			long end = new Date().getTime();
			long day = (end - begin) / 86400000;
			return CommUtil.null2Int(day);
		}
		return 999;
	}

}
