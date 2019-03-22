package com.koala.manage.buyer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.koala.foundation.domain.query.ConsultQueryObject;
import com.koala.foundation.service.IConsultService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.manage.admin.tools.OrderFormTools;

/**
 * 
 * <p>
 * Title: ConsultBuyerAction.java
 * </p>
 * 
 * <p>
 * Description: 买家咨询管理器,显示所有买家发布的商品咨询信息及回复信息
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
 * @date 2014-9-29
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Controller
public class ConsultBuyerAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IConsultService consultService;
	@Autowired
	private OrderFormTools orderFormTools;

	@SecurityMapping(title = "买家咨询列表", value = "/buyer/consult.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/consult.htm")
	public ModelAndView consult(HttpServletRequest request,
			HttpServletResponse response, String reply, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/buyer_consult.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		ConsultQueryObject qo = new ConsultQueryObject(currentPage, mv,
				"addTime", "desc");
		if (!CommUtil.null2String(reply).equals("")) {
			qo.addQuery("obj.reply",
					new SysMap("reply", CommUtil.null2Boolean(reply)), "=");
		}
		qo.addQuery("obj.consult_user_id", new SysMap("consult_user",
				SecurityUserHolder.getCurrentUser().getId()), "=");
		qo.setPageSize(3);
		IPageList pList = this.consultService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		mv.addObject("reply", CommUtil.null2String(reply));
		mv.addObject("orderFormTools", orderFormTools);
		return mv;
	}
}
