package com.koala.manage.seller.action;

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
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.Article;
import com.koala.foundation.domain.query.ArticleQueryObject;
import com.koala.foundation.service.IArticleService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;

/**
 * 
 * <p>
 * Title: StoreNoticeAction.java
 * </p>
 * 
 * <p>
 * Description: 商家后台店铺公告管理控制器
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
 * @date 2014-4-2
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Controller
public class StoreNoticeAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IArticleService articleService;

	/**
	 * 商家店铺公告列表
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityMapping(title = "商家店铺公告列表", value = "/seller/store_notice.htm*", rtype = "seller", rname = "店内公告", rcode = "store_notice", rgroup = "我的店铺")
	@RequestMapping("/seller/store_notice.htm")
	public ModelAndView store_notice(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"user/default/sellercenter/store_notice.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		ArticleQueryObject qo = new ArticleQueryObject(currentPage, mv,
				orderBy, orderType);
		WebForm wf = new WebForm();
		wf.toQueryPo(request, qo, Article.class, mv);
		qo.setOrderBy("addTime");
		qo.setOrderType("desc");
		qo.setPageSize(10);
		qo.addQuery("obj.type", new SysMap("type", "store"), "=");
		IPageList pList = this.articleService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", null, pList, mv);
		return mv;
	}

	/**
	 * 商家店铺公告详情
	 * 
	 * @param id
	 * @return mv
	 */
	@SecurityMapping(title = "商家店铺公告详情", value = "/seller/store_notice_detail.htm*", rtype = "seller", rname = "店内公告", rcode = "store_notice", rgroup = "我的店铺")
	@RequestMapping("/seller/store_notice_detail.htm")
	public ModelAndView store_notice_detail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		ModelAndView mv = new JModelAndView(
				"user/default/sellercenter/store_notice_detail.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		Article article = this.articleService
				.getObjById(CommUtil.null2Long(id));
		mv.addObject("obj", article);
		return mv;
	}

}