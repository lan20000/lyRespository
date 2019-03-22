package com.koala.module.weixin.manage.buyer.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.annotation.SecurityMapping;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Evaluate;
import com.koala.foundation.domain.ExpressCompany;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.GoodsSpecProperty;
import com.koala.foundation.domain.GroupGoods;
import com.koala.foundation.domain.IntegralGoods;
import com.koala.foundation.domain.IntegralGoodsCart;
import com.koala.foundation.domain.IntegralGoodsOrder;
import com.koala.foundation.domain.IntegralLog;
import com.koala.foundation.domain.OrderForm;
import com.koala.foundation.domain.OrderFormLog;
import com.koala.foundation.domain.Payment;
import com.koala.foundation.domain.PayoffLog;
import com.koala.foundation.domain.PredepositLog;
import com.koala.foundation.domain.ReturnGoodsLog;
import com.koala.foundation.domain.Store;
import com.koala.foundation.domain.StorePoint;
import com.koala.foundation.domain.SysConfig;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.query.IntegralGoodsOrderQueryObject;
import com.koala.foundation.domain.virtual.TransInfo;
import com.koala.foundation.service.IEvaluateService;
import com.koala.foundation.service.IExpressCompanyService;
import com.koala.foundation.service.IGoodsService;
import com.koala.foundation.service.IGroupGoodsService;
import com.koala.foundation.service.IIntegralGoodsOrderService;
import com.koala.foundation.service.IIntegralGoodsService;
import com.koala.foundation.service.IIntegralLogService;
import com.koala.foundation.service.IOrderFormLogService;
import com.koala.foundation.service.IOrderFormService;
import com.koala.foundation.service.IPaymentService;
import com.koala.foundation.service.IPayoffLogService;
import com.koala.foundation.service.IPredepositLogService;
import com.koala.foundation.service.IReturnGoodsLogService;
import com.koala.foundation.service.IStorePointService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.foundation.service.IUserService;
import com.koala.lucene.LuceneUtil;
import com.koala.lucene.tools.LuceneVoTools;
import com.koala.manage.admin.tools.AreaManageTools;
import com.koala.manage.admin.tools.OrderFormTools;
import com.koala.manage.admin.tools.PaymentTools;
import com.koala.msg.MsgTools;
import com.koala.pay.tools.PayTools;
import com.koala.view.web.tools.IntegralViewTools;

/**
 * 
 * 
 * <p>
 * Title:MobileUserOrderAction.java
 * </p>
 * 
 * <p>
 * Description: 手机端买家中心积分订单控制器
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
 * @author jy
 * 
 * @date 2014年8月20日
 * 
 * @version koala_b2b2c_2015
 */
@Controller
public class WeixinUserIntegralAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IIntegralGoodsService integralGoodsService;
	@Autowired
	private IIntegralGoodsOrderService integralGoodsOrderService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IIntegralLogService integralLogService;
	@Autowired
	private IntegralViewTools integralViewTools;
	@Autowired 
	private AreaManageTools areaManageTools;
	@Autowired
	private IExpressCompanyService expressCompanyService;
	@Autowired
	private OrderFormTools orderFormTools;
	@Autowired
	private PaymentTools paymentTools;
	@Autowired
	private PayTools payTools;
	@Autowired
	private IPaymentService paymentService;
	@Autowired
	private IPredepositLogService predepositLogService;

	@SecurityMapping(title = "买家积分积分订单列表", value = "/wap/buyer/integral_order_list.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_order_list.htm")
	public ModelAndView integral_order_list(HttpServletRequest request,
			HttpServletResponse response, String currentPage,String igo_status) {
		ModelAndView mv = new JModelAndView(
				"user/wap/usercenter/integral_order_list.html",
				configService.getSysConfig(), this.userConfigService
						.getUserConfig(), 0, request, response);
		if(CommUtil.null2Int(currentPage)>1){
			mv = new JModelAndView(
					"user/wap/usercenter/integral_order_data.html",
					configService.getSysConfig(), this.userConfigService
							.getUserConfig(), 0, request, response);
		}
		if (this.configService.getSysConfig().isIntegralStore()) {
			IntegralGoodsOrderQueryObject qo = new IntegralGoodsOrderQueryObject(
					currentPage, mv, "addTime", "desc");
			qo.addQuery("obj.igo_user.id", new SysMap("user_id",
					SecurityUserHolder.getCurrentUser().getId()), "=");
			if(!CommUtil.null2String(igo_status).equals("")){
				qo.addQuery("obj.igo_status", new SysMap("igo_status", CommUtil.null2Int(igo_status)), "=");
				mv.addObject("igo_status", igo_status);
			}
			IPageList pList = this.integralGoodsOrderService.list(qo);
			CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
			mv.addObject("orderFormTools", orderFormTools);
		} else {
			mv = new JModelAndView("wap/error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "系统未开启积分商城");
			mv.addObject("url", CommUtil.getURL(request) + "/wap/buyer/index.htm");
		}
		mv.addObject("orderFormTools", orderFormTools);
		return mv;
	}

	@SecurityMapping(title = "取消积分订单", value = "/wap/buyer/integral_order_cancel.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_order_cancel.htm")
	public ModelAndView integral_order_cancel(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView("wap/success.html", configService
				.getSysConfig(), this.userConfigService.getUserConfig(), 0,
				request, response);
		IntegralGoodsOrder obj = this.integralGoodsOrderService
				.getObjById(CommUtil.null2Long(id));
		if (obj != null
				&& obj.getIgo_user().getId().equals(
						SecurityUserHolder.getCurrentUser().getId())) {
			obj.setIgo_status(-1);
			this.integralGoodsOrderService.update(obj);
			List<IntegralGoods> igs = this.orderFormTools
					.query_integral_all_goods(CommUtil.null2String(obj.getId()));
			for (IntegralGoods ig : igs) {
				IntegralGoods goods = ig;
				int sale_count = this.orderFormTools
						.query_integral_one_goods_count(obj,
								CommUtil.null2String(ig.getId()));
				goods.setIg_goods_count(goods.getIg_goods_count() + sale_count);
				this.integralGoodsService.update(goods);
			}
			User user = obj.getIgo_user();
			user.setIntegral(user.getIntegral() + obj.getIgo_total_integral());
			this.userService.update(user);
			IntegralLog log = new IntegralLog();
			log.setAddTime(new Date());
			log.setContent("取消" + obj.getIgo_order_sn() + "积分兑换，返还积分");
			log.setIntegral(obj.getIgo_total_integral());
			log.setIntegral_user(obj.getIgo_user());
			log.setType("integral_order");
			this.integralLogService.save(log);
			mv.addObject("op_title", "积分兑换取消成功");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm");
		} else {
			mv = new JModelAndView("wap/error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "参数错误，无该积分订单");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm");
		}
		return mv;
	}

	@SecurityMapping(title = "积分积分订单详情", value = "/wap/buyer/integral_order_view.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_order_view.htm")
	public ModelAndView integral_order_view(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"user/wap/usercenter/integral_order_view.html",
				configService.getSysConfig(), this.userConfigService
						.getUserConfig(), 0, request, response);
		IntegralGoodsOrder obj = this.integralGoodsOrderService
				.getObjById(CommUtil.null2Long(id));
		if (obj != null
				&& obj.getIgo_user().getId().equals(
						SecurityUserHolder.getCurrentUser().getId())) {
			mv.addObject("obj", obj);
			mv.addObject("currentPage", currentPage);
			mv.addObject("integralViewTools",integralViewTools);
			mv.addObject("areaManageTools",areaManageTools);
			mv.addObject("orderFormTools", orderFormTools);
			boolean query_ship = false;// 是否查询物流
			if (!CommUtil.null2String(obj.getIgo_ship_code()).equals("")) {
				query_ship = true;
			}
			mv.addObject("query_ship", query_ship);
		} else {
			mv = new JModelAndView("wap/error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "参数错误，无该积分订单");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm");
		}
		return mv;
	}

	@SecurityMapping(title = "确认收货", value = "/wap/buyer/integral_order_cofirm.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_order_cofirm.htm")
	public ModelAndView integral_order_cofirm(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"user/wap/usercenter/integral_order_cofirm.html",
				configService.getSysConfig(), this.userConfigService
						.getUserConfig(), 0, request, response);
		IntegralGoodsOrder obj = this.integralGoodsOrderService
				.getObjById(CommUtil.null2Long(id));
		if (obj != null
				&& obj.getIgo_user().getId().equals(
						SecurityUserHolder.getCurrentUser().getId())) {
			mv.addObject("obj", obj);
		} else {
			mv = new JModelAndView("wap/error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "参数错误，无该积分订单");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm");
		}
		return mv;
	}

	@SecurityMapping(title = "确认收货保存", value = "/wap/buyer/integral_order_cofirm_save.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_order_cofirm_save.htm")
	public ModelAndView integral_order_cofirm_save(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView("wap/success.html", configService
				.getSysConfig(), this.userConfigService.getUserConfig(), 1,
				request, response);
		IntegralGoodsOrder obj = this.integralGoodsOrderService
				.getObjById(CommUtil.null2Long(id));
		if (obj != null
				&& obj.getIgo_user().getId().equals(
						SecurityUserHolder.getCurrentUser().getId())) {
			obj.setIgo_status(40);
			this.integralGoodsOrderService.update(obj);
			mv.addObject("op_title", "确认收货成功");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm");
		} else {
			mv = new JModelAndView("wap/error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "参数错误，无该积分订单");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/wap/buyer/integral_order_list.htm?currentPage="
					+ currentPage);
		}
		return mv;
	}
	
	@SecurityMapping(title = "物流信息", value = "/wap/buyer/integral_ship_detail.htm*", rtype = "buyer", rname = "积分订单", rcode = "wap_user_center", rgroup = "用户中心")
	@RequestMapping("/wap/buyer/integral_ship_detail.htm")
	public ModelAndView integral_ship_detail(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String order_id,String type) {
		ModelAndView mv = new JModelAndView(
				"user/wap/usercenter/integral_ship_detail.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		IntegralGoodsOrder order = this.integralGoodsOrderService.getObjById(CommUtil
				.null2Long(order_id));
		TransInfo transInfo = this.query_ship_getData(CommUtil
				.null2String(order_id));
		if (transInfo != null) {
			transInfo.setExpress_company_name(this.orderFormTools.queryExInfo(
					order.getIgo_express_info(), "express_company_name"));
			transInfo.setExpress_ship_code(order.getIgo_ship_code());
		}
		if(!CommUtil.null2String(type).equals("")){
			mv.addObject("type", type);
		}
		mv.addObject("order", order);
		mv.addObject("transInfo", transInfo);
		return mv;
	}
	
	private TransInfo query_ship_getData(String id) {
		TransInfo info = null;
		IntegralGoodsOrder obj = this.integralGoodsOrderService
				.getObjById(CommUtil.null2Long(id));
		if (obj != null && !CommUtil.null2String(obj.getIgo_ship_code()).equals("")) {
			info = new TransInfo();
			try {
				ExpressCompany ec = this.queryExpressCompany(obj
						.getIgo_express_info());
				String query_url = "http://api.kuaidi100.com/api?id="
						+ this.configService.getSysConfig().getKuaidi_id()
						+ "&com=" + (ec != null ? ec.getCompany_mark() : "")
						+ "&nu=" + obj.getIgo_ship_code()
						+ "&show=0&muti=1&order=asc";
				URL url = new URL(query_url);
				URLConnection con = url.openConnection();
				con.setAllowUserInteraction(false);
				InputStream urlStream = url.openStream();
				String type = con.guessContentTypeFromStream(urlStream);
				String charSet = null;
				if (type == null)
					type = con.getContentType();
				if (type == null || type.trim().length() == 0
						|| type.trim().indexOf("text/html") < 0)
					return info;
				if (type.indexOf("charset=") > 0)
					charSet = type.substring(type.indexOf("charset=") + 8);
				byte b[] = new byte[10000];
				int numRead = urlStream.read(b);
				String content = new String(b, 0, numRead, charSet);
				while (numRead != -1) {
					numRead = urlStream.read(b);
					if (numRead != -1) {
						// String newContent = new String(b, 0, numRead);
						String newContent = new String(b, 0, numRead, charSet);
						content += newContent;
					}
				}
				info = Json.fromJson(TransInfo.class, content);
				urlStream.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	private ExpressCompany queryExpressCompany(String json) {
		ExpressCompany ec = null;
		if (json != null && !json.equals("")) {
			HashMap map = Json.fromJson(HashMap.class, json);
			ec = this.expressCompanyService.getObjById(CommUtil.null2Long(map
					.get("express_company_id")));
		}
		return ec;
	}
	
}
