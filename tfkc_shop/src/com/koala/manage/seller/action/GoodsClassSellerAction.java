package com.koala.manage.seller.action;

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
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.UserGoodsClass;
import com.koala.foundation.domain.query.UserGoodsClassQueryObject;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.foundation.service.IUserGoodsClassService;
import com.koala.foundation.service.IUserService;

/**
 * 
 * <p>
 * Title: GoodsClassSellerAction.java
 * </p>
 * 
 * <p>
 * Description: 商家后台商品分类管理
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
 * @author wangyujue、erikzhang
 * 
 * @date 2014-10-19
 * 
 * @version koala_b2b2c 2015
 */
@Controller
public class GoodsClassSellerAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IUserGoodsClassService usergoodsclassService;
	@Autowired
	private IUserService userService;

	/**
	 * UserGoodsClass列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "卖家商品分类列表", value = "/seller/goods_class_list.htm*", rtype = "seller", rname = "商品分类", rcode = "goods_class_seller", rgroup = "商品管理")
	@RequestMapping("/seller/goods_class_list.htm")
	public ModelAndView goods_class_list(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"user/default/sellercenter/goods_class_list.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		User user = this.userService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		user = user.getParent() == null ? user : user.getParent();
		UserGoodsClassQueryObject qo = new UserGoodsClassQueryObject(
				currentPage, mv, orderBy, orderType);
		qo.setPageSize(20);
		WebForm wf = new WebForm();
		wf.toQueryPo(request, qo, UserGoodsClass.class, mv);
		qo.addQuery("obj.parent.id is null", null);
		qo.addQuery("obj.user_id", new SysMap("user_id", user.getId()), "=");
		qo.setOrderBy("sequence");
		qo.setOrderType("asc");
		IPageList pList = this.usergoodsclassService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		return mv;
	}

	/**
	 * 商家商品分类保存
	 * 
	 * @param id
	 * @return
	 */
	@SecurityMapping(title = "卖家商品分类保存", value = "/seller/goods_class_save.htm*", rtype = "seller", rname = "商品分类", rcode = "goods_class_seller", rgroup = "商品管理")
	@RequestMapping("/seller/goods_class_save.htm")
	public String goods_class_save(HttpServletRequest request,
			HttpServletResponse response, String id, String pid) {
		WebForm wf = new WebForm();
		UserGoodsClass usergoodsclass = null;
		if (id.equals("")) {
			usergoodsclass = wf.toPo(request, UserGoodsClass.class);
			usergoodsclass.setAddTime(new Date());
		} else {
			UserGoodsClass obj = this.usergoodsclassService.getObjById(Long
					.parseLong(id));
			usergoodsclass = (UserGoodsClass) wf.toPo(request, obj);
		}
		User user = this.userService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		user = user.getParent() == null ? user : user.getParent();
		usergoodsclass.setUser_id(user.getId());
		if (!pid.equals("")) {
			UserGoodsClass parent = this.usergoodsclassService.getObjById(Long
					.parseLong(pid));
			usergoodsclass.setParent(parent);
		}
		if (id.equals("")) {
			this.usergoodsclassService.save(usergoodsclass);
		} else
			this.usergoodsclassService.update(usergoodsclass);
		return "redirect:/seller/goods_class_list.htm";
	}

	@SecurityMapping(title = "卖家商品分类删除", value = "/seller/goods_class_del.htm*", rtype = "seller", rname = "商品分类", rcode = "goods_class_seller", rgroup = "商品管理")
	@RequestMapping("/seller/goods_class_del.htm")
	public String goods_class_del(HttpServletRequest request, String mulitId) {
		String[] ids = mulitId.split(",");
		User user = this.userService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		user = user.getParent() == null ? user : user.getParent();
		for (String id : ids) {
			if (!id.equals("")) {
				UserGoodsClass uc = this.usergoodsclassService.getObjById(Long
						.parseLong(id));
				if (uc != null) {
					if (uc.getUser_id() == user.getId()) {
						clean_ugc(uc);
						this.usergoodsclassService.delete(uc.getId());
					}

				}
			}
		}
		return "redirect:goods_class_list.htm";
	}

	private void clean_ugc(UserGoodsClass ugc) {
		for (UserGoodsClass child : ugc.getChilds()) {
			clean_ugc(child);
			this.usergoodsclassService.delete(child.getId());
		}
		ugc.getChilds().removeAll(ugc.getChilds());
		for (Goods goods : ugc.getGoods_list()) {
			goods.getGoods_ugcs().remove(ugc);
		}
		ugc.getGoods_list().removeAll(ugc.getGoods_list());
	}

	@SecurityMapping(title = "新增卖家商品分类", value = "/seller/goods_class_add.htm*", rtype = "seller", rname = "商品分类", rcode = "goods_class_seller", rgroup = "商品管理")
	@RequestMapping("/seller/goods_class_add.htm")
	public ModelAndView goods_class_add(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String pid) {
		ModelAndView mv = new JModelAndView(
				"user/default/sellercenter/goods_class_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		User user = this.userService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		user = user.getParent() == null ? user : user.getParent();
		Map map = new HashMap();
		map.put("user_id", user.getId());
		List<UserGoodsClass> ugcs = this.usergoodsclassService
				.query("select obj from UserGoodsClass obj where obj.parent.id is null and obj.user_id = :user_id order by obj.sequence asc",
						map, -1, -1);
		if (!CommUtil.null2String(pid).equals("")) {
			UserGoodsClass parent = this.usergoodsclassService
					.getObjById(CommUtil.null2Long(pid));
			UserGoodsClass obj = new UserGoodsClass();
			obj.setParent(parent);
			mv.addObject("obj", obj);
		}
		mv.addObject("ugcs", ugcs);
		mv.addObject("currentPage", currentPage);
		return mv;
	}

	@SecurityMapping(title = "编辑卖家商品分类", value = "/seller/goods_class_edit.htm*", rtype = "seller", rname = "商品分类", rcode = "goods_class_seller", rgroup = "商品管理")
	@RequestMapping("/seller/goods_class_edit.htm")
	public ModelAndView goods_class_edit(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String id) {
		ModelAndView mv = new JModelAndView(
				"user/default/sellercenter/goods_class_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		User user = this.userService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		user = user.getParent() == null ? user : user.getParent();
		Map map = new HashMap();
		map.put("user_id", user.getId());
		List<UserGoodsClass> ugcs = this.usergoodsclassService
				.query("select obj from UserGoodsClass obj where obj.parent.id is null and obj.user_id = :user_id order by obj.sequence asc",
						map, -1, -1);
		UserGoodsClass obj = this.usergoodsclassService.getObjById(CommUtil
				.null2Long(id));
		mv.addObject("obj", obj);
		mv.addObject("ugcs", ugcs);
		mv.addObject("currentPage", currentPage);
		return mv;
	}
}