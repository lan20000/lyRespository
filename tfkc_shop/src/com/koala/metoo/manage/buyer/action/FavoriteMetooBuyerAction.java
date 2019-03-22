package com.koala.metoo.manage.buyer.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.koala.foundation.domain.Favorite;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.Store;
import com.koala.foundation.domain.query.FavoriteQueryObject;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.lucene.LuceneUtil;
import com.koala.manage.admin.tools.UserTools;
import com.koala.metoo.foundation.service.IFavoriteMetooService;
import com.koala.view.web.tools.GoodsViewTools;
@Controller
public class FavoriteMetooBuyerAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IFavoriteMetooService favoriteMetooService;
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
	@RequestMapping("/buyer/favorite_goods_metoo.htm")
	public void favorite_goods(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		String favorite_goods = this.favoriteMetooService.favorite_goodsimpl(request, response, currentPage, orderBy, orderType);
		try {
			response.getWriter().println(favorite_goods);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SecurityMapping(title = "用户店铺收藏", value = "/buyer/favorite_goods.htm*", rtype="buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/favorite_store_metoo.htm")
	public void favorite_store(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType){

		String favoriteStore = this.favoriteMetooService.favorite_storeimpl(request, response, currentPage, orderBy, orderType);
		try {
			response.getWriter().println(favoriteStore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SecurityMapping(title = "用户收藏删除", value = "/buyer/favorite_del.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/favorite_del_metoo.htm")
	public void favoriteDel(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage){

		String favoritedel = this.favoriteMetooService.favorite_delimpl(request, response, mulitId, currentPage);
		try {
			response.getWriter().print(favoritedel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
 