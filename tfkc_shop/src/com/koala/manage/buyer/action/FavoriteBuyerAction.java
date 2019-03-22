package com.koala.manage.buyer.action;

import java.io.File;

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
import com.koala.foundation.domain.Favorite;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.Store;
import com.koala.foundation.domain.query.FavoriteQueryObject;
import com.koala.foundation.service.IFavoriteService;
import com.koala.foundation.service.IGoodsService;
import com.koala.foundation.service.IMessageService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.foundation.service.IUserService;
import com.koala.lucene.LuceneUtil;
import com.koala.lucene.tools.LuceneVoTools;
import com.koala.manage.admin.tools.UserTools;
import com.koala.view.web.tools.GoodsViewTools;

/**
 * 
 * <p>
 * Title: FavoriteBuyerAction.java
 * </p>
 * 
 * <p>
 * Description: 收藏管理控制器，用来显示买家收藏的商品信息、店铺信息
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
 * @date 2014-8-8
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Controller
public class FavoriteBuyerAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IFavoriteService favoriteService;
	@Autowired
	private LuceneVoTools luceneVoTools;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private UserTools userTools;
	@Autowired
	private GoodsViewTools goodsViewTools;

	/**
	 * Favorite列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "用户商品收藏", value = "/buyer/favorite_goods.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/favorite_goods.htm")
	public ModelAndView favorite_goods(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/favorite_goods.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if (url == null || url.equals("")) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv,
				orderBy, orderType);
		qo.addQuery("obj.type", new SysMap("type", 0), "=");
		qo.addQuery("obj.user_id", new SysMap("user_id", SecurityUserHolder
				.getCurrentUser().getId()), "=");
		IPageList pList = this.favoriteService.list(qo);
		CommUtil.saveIPageList2ModelAndView(url + "/buyer/favorite_goods.htm",
				"", params, pList, mv);
		mv.addObject("userTools", userTools);
		mv.addObject("goodsViewTools", goodsViewTools);
		return mv;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	@SecurityMapping(title = "用户店铺收藏", value = "/buyer/favorite_store.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/favorite_store.htm")
	public ModelAndView favorite_store(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/favorite_store.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if (url == null || url.equals("")) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv,
				orderBy, orderType);
		qo.addQuery("obj.type", new SysMap("type", 1), "=");
		qo.addQuery("obj.user_id", new SysMap("user_id", SecurityUserHolder
				.getCurrentUser().getId()), "=");
		IPageList pList = this.favoriteService.list(qo);
		CommUtil.saveIPageList2ModelAndView(url + "/buyer/favorite_store.htm",
				"", params, pList, mv);
		return mv;
	}

	@SecurityMapping(title = "用户收藏删除", value = "/buyer/favorite_del.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/favorite_del.htm")
	public String favorite_del(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage,
			Integer type) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Favorite favorite = this.favoriteService.getObjById(CommUtil.null2Long(id));
				if (favorite.getGoods_id() != null) {
					// 更新lucene索引
					String goods_lucene_path = System.getProperty("koalab2b2c.root")
							+ File.separator + "luence" + File.separator
							+ "goods";
					File file = new File(goods_lucene_path);
					if (!file.exists()) {
						CommUtil.createFolder(goods_lucene_path);
					}
					LuceneUtil lucene = LuceneUtil.instance();
					lucene.setIndex_path(goods_lucene_path);
					Goods goods = this.goodsService.getObjById(favorite
							.getGoods_id());
					lucene.update(CommUtil.null2String(favorite.getGoods_id()),
							luceneVoTools.updateGoodsIndex(goods));
				}
				if (favorite.getType() == 0) {
					Goods goods = this.goodsService.getObjById(favorite
							.getGoods_id());
					goods.setGoods_collect(goods.getGoods_collect() - 1);
					this.goodsService.update(goods);
				}
				if (favorite.getType() == 1) {
					Store store = this.storeService.getObjById(favorite
							.getStore_id());
					store.setFavorite_count(store.getFavorite_count() - 1);
					this.storeService.update(store);
				}
				this.favoriteService.delete(Long.parseLong(id));
			}
		}
		if (type == 0) {
			return "redirect:favorite_goods.htm?currentPage=" + currentPage;
		} else {
			return "redirect:favorite_store.htm?currentPage=" + currentPage;
		}
	}
}