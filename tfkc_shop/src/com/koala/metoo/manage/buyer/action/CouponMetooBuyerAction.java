package com.koala.metoo.manage.buyer.action;

import java.io.IOException;
import java.util.ArrayList;
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

import com.koala.buyer.domain.Result;
import com.koala.core.annotation.SecurityMapping;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Coupon;
import com.koala.foundation.domain.CouponInfo;
import com.koala.foundation.domain.query.CouponInfoQueryObject;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.metoo.foundation.service.ICouponInfoMetooService;

@Controller
public class CouponMetooBuyerAction {
	
	@Autowired
	private ICouponInfoMetooService couponInfoMetooService;

	@SecurityMapping(title = "买家优惠券列表", value = "/buyer/coupon.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/coupon_metoo.htm")
	public void coupon(HttpServletRequest request,
			HttpServletResponse response, String reply, String currentPage) {
		String couponResult =  this.couponInfoMetooService.coupon_listimpl(request, response, reply, currentPage);
		try {
			response.getWriter().println(couponResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
