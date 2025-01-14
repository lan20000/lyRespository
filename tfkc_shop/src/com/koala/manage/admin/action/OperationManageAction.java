package com.koala.manage.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.annotation.SecurityMapping;
import com.koala.core.mv.JModelAndView;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Navigation;
import com.koala.foundation.domain.SysConfig;
import com.koala.foundation.service.INavigationService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;

/**
 * 
 * <p>
 * Title: OperationManageAction.java
 * </p>
 * 
 * <p>
 * Description: 超级后台运营基础功能管理器,主要功能: 1、运营基本设置 2、积分规则管理
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
 * @date 2014年5月27日
 * 
 * @version koala_b2b2c 2.0
 */

@Controller
public class OperationManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private INavigationService navigationService;

	/**
	 * 系统积分设置
	 * 
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "基本设置", value = "/admin/operation_base_set.htm*", rtype = "admin", rname = "基本设置", rcode = "operation_base", rgroup = "运营")
	@RequestMapping("/admin/operation_base_set.htm")
	public ModelAndView operation_base_set(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/operation_base_setting.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		return mv;
	}

	/**
	 * 系统积分设置保存
	 * 
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "基本设置保存", value = "/admin/base_set_save.htm*", rtype = "admin", rname = "基本设置", rcode = "operation_base", rgroup = "运营")
	@RequestMapping("/admin/base_set_save.htm")
	public ModelAndView base_set_save(HttpServletRequest request,
			HttpServletResponse response, String id, String integral,
			String integralStore, String voucher, String deposit, String gold,
			String goldMarketValue, String groupBuy, String group_meal_gold,
			String buygift_status, String buygift_meal_gold,
			String enoughreduce_status, String enoughreduce_meal_gold,
			String enoughreduce_max_count, String combin_amount,
			String combin_scheme_count, String combin_count,
			String combin_status, String whether_free) {
		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		SysConfig config = this.configService.getSysConfig();
		config.setIntegral(CommUtil.null2Boolean(integral));
		config.setIntegralStore(CommUtil.null2Boolean(integralStore));
		config.setVoucher(CommUtil.null2Boolean(voucher));
		config.setDeposit(CommUtil.null2Boolean(deposit));
		config.setGold(CommUtil.null2Boolean(gold));
		config.setGoldMarketValue(CommUtil.null2Int(goldMarketValue));
		config.setGroupBuy(CommUtil.null2Boolean(groupBuy));
		config.setGroup_meal_gold(CommUtil.null2Int(group_meal_gold));
		config.setBuygift_status(CommUtil.null2Int(buygift_status));
		config.setBuygift_meal_gold(CommUtil.null2Int(buygift_meal_gold));
		config.setEnoughreduce_status(CommUtil.null2Int(enoughreduce_status));
		config.setEnoughreduce_meal_gold(CommUtil
				.null2Int(enoughreduce_meal_gold));
		config.setEnoughreduce_max_count(CommUtil
				.null2Int(enoughreduce_max_count));
		config.setCombin_amount(CommUtil.null2Int(combin_amount));
		config.setCombin_scheme_count(CommUtil.null2Int(combin_scheme_count));
		config.setCombin_count(CommUtil.null2Int(combin_count));
		config.setWhether_free(CommUtil.null2Int(whether_free));		
		System.out.println(CommUtil.null2Int(combin_status));
		config.setCombin_status(CommUtil.null2Int(combin_status));
		if (id.equals("")) {
			this.configService.save(config);
		} else {
			this.configService.update(config);
		}
		if (config.isIntegralStore()) {
			Map params = new HashMap();
			params.put("url", "integral/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			if (navs.size() == 0) {
				Navigation nav = new Navigation();
				nav.setAddTime(new Date());
				nav.setDisplay(true);
				nav.setLocation(0);
				nav.setNew_win(1);
				nav.setSequence(2);
				nav.setSysNav(true);
				nav.setTitle("积分商城");
				nav.setType("diy");
				nav.setUrl("integral/index.htm");
				nav.setOriginal_url("integral/index.htm");
				this.navigationService.save(nav);
			}
		} else {
			Map params = new HashMap();
			params.put("url", "integral/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			for (Navigation nav : navs) {
				this.navigationService.delete(nav.getId());
			}
		}
		if (config.getWhether_free() == 1) {
			Map params = new HashMap();
			params.put("url", "free/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			if (navs.size() == 0) {
				Navigation nav = new Navigation();
				nav.setAddTime(new Date());
				nav.setDisplay(true);
				nav.setLocation(0);
				nav.setNew_win(1);
				nav.setSequence(7);
				nav.setSysNav(true);
				nav.setTitle("0元试用");
				nav.setType("diy");
				nav.setUrl("free/index.htm");
				nav.setOriginal_url("free/index.htm");
				this.navigationService.save(nav);
			}
		} else {
			Map params = new HashMap();
			params.put("url", "free/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			for (Navigation nav : navs) {
				this.navigationService.delete(nav.getId());
			}
		}
		if (config.isGroupBuy()) {
			Map params = new HashMap();
			params.put("url", "group/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			if (navs.size() == 0) {
				Navigation nav = new Navigation();
				nav.setAddTime(new Date());
				nav.setDisplay(true);
				nav.setLocation(0);
				nav.setNew_win(1);
				nav.setSequence(3);
				nav.setSysNav(true);
				nav.setTitle("团购");
				nav.setType("diy");
				nav.setUrl("group/index.htm");
				nav.setOriginal_url("group/index.htm");
				this.navigationService.save(nav);
			}
		} else {
			Map params = new HashMap();
			params.put("url", "group/index.htm");
			List<Navigation> navs = this.navigationService.query(
					"select obj from Navigation obj where obj.url=:url",
					params, -1, -1);
			for (Navigation nav : navs) {
				this.navigationService.delete(nav.getId());
			}
		}
		mv.addObject("op_title", "保存基本设置成功");
		mv.addObject("list_url", CommUtil.getURL(request)
				+ "/admin/operation_base_set.htm");
		return mv;
	}

	/**
	 * 系统积分规则设置
	 * 
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "积分规则", value = "/admin/operation_integral_rule.htm*", rtype = "admin", rname = "积分规则", rcode = "integral_rule", rgroup = "运营")
	@RequestMapping("/admin/operation_integral_rule.htm")
	public ModelAndView integral_rule(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/operation_integral_rule.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		return mv;
	}

	/**
	 * 系统积分规则保存
	 * 
	 * @param request
	 * @param id
	 * @param memberRegister
	 * @param memberDayLogin
	 * @param indentComment
	 * @param consumptionRatio
	 * @param everyIndentLimit
	 * @return
	 */
	@SecurityMapping(title = "积分规则保存", value = "/admin/integral_rule_save.htm*", rtype = "admin", rname = "积分规则", rcode = "integral_rule", rgroup = "运营")
	@RequestMapping("/admin/integral_rule_save.htm")
	public ModelAndView integral_rule_save(HttpServletRequest request,
			HttpServletResponse response, String id, String memberRegister,
			String memberDayLogin, String indentComment,
			String consumptionRatio, String everyIndentLimit) {
		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		SysConfig config = this.configService.getSysConfig();
		config.setMemberRegister(CommUtil.null2Int(memberRegister));
		config.setMemberDayLogin(CommUtil.null2Int(memberDayLogin));
		config.setIndentComment(CommUtil.null2Int(indentComment));
		config.setConsumptionRatio(CommUtil.null2Int(consumptionRatio));
		config.setEveryIndentLimit(CommUtil.null2Int(everyIndentLimit));
		if (id.equals("")) {
			this.configService.save(config);
		} else {
			this.configService.update(config);
		}
		mv.addObject("op_title", "保存积分设置成功");
		mv.addObject("list_url", CommUtil.getURL(request)
				+ "/admin/operation_integral_rule.htm");
		return mv;
	}
}
