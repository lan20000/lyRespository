package com.koala.view.web.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Article;
import com.koala.foundation.domain.ArticleClass;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.query.ArticleQueryObject;
import com.koala.foundation.service.IArticleClassService;
import com.koala.foundation.service.IArticleService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.foundation.service.IUserService;
import com.koala.view.web.tools.ArticleViewTools;

/**
 * 
 * <p>
 * Title: ArticleViewAction.java
 * </p>
 * 
 * <p>
 * Description: 前台文章控制器，主要功能: 1、分类列表文章 2、显示文章
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
 * @date 2014-4-30
 * 
 * @version koala_b2b2c v2.0 2015版 
 */
@Controller
public class ArticleViewAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IArticleClassService articleClassService;
	@Autowired
	private ArticleViewTools articleTools;

	@RequestMapping("/articlelist_help.htm")
	public ModelAndView article_list(HttpServletRequest request,
			HttpServletResponse response, String param, String currentPage) {
		ModelAndView mv = new JModelAndView("article_list.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		ArticleClass ac = null;
		ArticleQueryObject aqo = new ArticleQueryObject();
		aqo.setCurrentPage(CommUtil.null2Int(currentPage));
		Long id = CommUtil.null2Long(param);
		String mark = "";
		if (id == -1l) {
			mark = param;
		}
		if (mark != null && !("").equals(mark)) {
			ac = this.articleClassService.getObjByPropertyName(null,"mark", mark);
		}
		if (id != -1l) {
			ac = this.articleClassService.getObjById(id);
		}
		if (ac != null) {
			Set<Long> ids = this.genericIds(ac);
			Map paras = new HashMap();
			paras.put("ids", ids);
			aqo.addQuery("obj.articleClass.id in (:ids)", paras);
		}
		aqo.addQuery("obj.display", new SysMap("display", true), "=");
		aqo.addQuery("obj.type", new SysMap("type", "user"), "=");
		aqo.setOrderBy("addTime");
		aqo.setOrderType("desc");
		IPageList pList = this.articleService.list(aqo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		List<ArticleClass> acs = this.articleClassService
				.query("select obj from ArticleClass obj where obj.parent.id is null order by obj.sequence asc",
						null, -1, -1);
		List<Article> articles = this.articleService.query(
				"select obj from Article obj order by obj.addTime desc", null,
				0, 6);
		mv.addObject("ac", ac);
		mv.addObject("articles", articles);
		mv.addObject("acs", acs);
		return mv;
	}

	private Set<Long> genericIds(ArticleClass ac) {
		Set<Long> ids = new HashSet<Long>();
		if (ac != null) {
			ids.add(ac.getId());
			for (ArticleClass child : ac.getChilds()) {
				Set<Long> cids = genericIds(child);
				for (Long cid : cids) {
					ids.add(cid);
				}
				ids.add(child.getId());
			}
		}
		return ids;
	}

	@RequestMapping("/article.htm")
	public ModelAndView article(HttpServletRequest request,
			HttpServletResponse response, String param) {
		ModelAndView mv = new JModelAndView("article.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Article obj = null;
		Long id = CommUtil.null2Long(param);
		String mark = "";
		if (id == -1l) {
			mark = param;
		}
		if (id != -1l) {
			obj = this.articleService.getObjById(id);
		}
		if (!mark.equals("")) {
			obj = this.articleService.getObjByProperty(null,"mark", mark);
		}
		User user = SecurityUserHolder.getCurrentUser();
		if (user != null) {
			if (user.getUserRole().equals("BUYER")) {
				if (obj != null && obj.getType().equals("user")) {
					List<ArticleClass> acs = this.articleClassService
							.query("select obj from ArticleClass obj where obj.parent.id is null order by obj.sequence asc",
									null, -1, -1);
					Map params = new HashMap();
					params.put("type", "user");
					List<Article> articles = this.articleService
							.query("select obj from Article obj where obj.type=:type order by obj.addTime desc",
									params, 0, 6);
					mv.addObject("articles", articles);
					mv.addObject("acs", acs);
					mv.addObject("obj", obj);
					mv.addObject("articleTools", articleTools);
				} else {
					mv = new JModelAndView("error.html",
							configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request,
							response);
					mv.addObject("op_title", "商家公告，您无权查看！");
				}
			}
			if (user.getUserRole().equals("SELLER") || user.getUserRole().equals("ADMIN")) {
				if (obj != null) {
					List<ArticleClass> acs = this.articleClassService
							.query("select obj from ArticleClass obj where obj.parent.id is null order by obj.sequence asc",
									null, -1, -1);
					Map params = new HashMap();
					params.put("type", "user");
					List<Article> articles = this.articleService
							.query("select obj from Article obj where obj.type=:type order by obj.addTime desc",
									params, 0, 6);
					mv.addObject("articles", articles);
					mv.addObject("acs", acs);
					mv.addObject("obj", obj);
					mv.addObject("articleTools", articleTools);
				} else {
					mv = new JModelAndView("error.html",
							configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request,
							response);
					mv.addObject("op_title", "商家公告，您无权查看！");
				}
			}
		} else {
			if (obj != null && obj.getType().equals("user")) {
				List<ArticleClass> acs = this.articleClassService
						.query("select obj from ArticleClass obj where obj.parent.id is null order by obj.sequence asc",
								null, -1, -1);
				Map params = new HashMap();
				params.put("type", "user");
				List<Article> articles = this.articleService
						.query("select obj from Article obj where obj.type=:type order by obj.addTime desc",
								params, 0, 6);
				mv.addObject("articles", articles);
				mv.addObject("acs", acs);
				mv.addObject("obj", obj);
				mv.addObject("articleTools", articleTools);
			} else {
				mv = new JModelAndView("error.html",
						configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request,
						response);
				mv.addObject("op_title", "商家公告，您无权查看！");
			}
		}
		return mv;
	}
}
