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
import com.koala.foundation.domain.query.CouponInfoQueryObject;
import com.koala.foundation.service.ICouponInfoService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;

/**
 * 
 * <p>
 * Title: CouponBuyerAction.java
 * </p>
 * 
 * <p>
 * Description:买家优惠券管理控制器，商场管理员赠送给买家优惠券后，买家可以在这里查看优惠券信息
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
 * @date 2015-3-16
 * 
 * @version koala_b2b2c 2015
 */ 
@Controller
public class CouponBuyerAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private ICouponInfoService couponInfoService;

	@SecurityMapping(title = "买家优惠券列表", value = "/buyer/coupon.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/coupon.htm")
	public ModelAndView coupon(HttpServletRequest request,
			HttpServletResponse response, String reply, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/buyer_coupon.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		CouponInfoQueryObject qo = new CouponInfoQueryObject(currentPage, mv,
				"addTime", "desc");
		qo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder
				.getCurrentUser().getId()), "=");
		IPageList pList = this.couponInfoService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		return mv;
	}
}
