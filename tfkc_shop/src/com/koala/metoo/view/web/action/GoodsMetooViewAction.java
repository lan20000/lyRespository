package com.koala.metoo.view.web.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.buyer.domain.Result;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.ip.IPSeeker;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Accessory;
import com.koala.foundation.domain.ActivityGoods;
import com.koala.foundation.domain.BuyGift;
import com.koala.foundation.domain.CombinPlan;
import com.koala.foundation.domain.Consult;
import com.koala.foundation.domain.EnoughReduce;
import com.koala.foundation.domain.Evaluate;
import com.koala.foundation.domain.FootPoint;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.GoodsClass;
import com.koala.foundation.domain.GoodsLog;
import com.koala.foundation.domain.Group;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.query.ConsultQueryObject;
import com.koala.foundation.domain.query.EvaluateQueryObject;



import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Parameter;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import com.koala.buyer.domain.Result;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.ip.IPSeeker;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.JsonUtil;
import com.koala.foundation.domain.Accessory;
import com.koala.foundation.domain.ActivityGoods;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Album;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.BuyGift;
import com.koala.foundation.domain.CombinPlan;
import com.koala.foundation.domain.Consult;
import com.koala.foundation.domain.ConsultSatis;
import com.koala.foundation.domain.EnoughReduce;
import com.koala.foundation.domain.Evaluate;
import com.koala.foundation.domain.FootPoint;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.GoodsBrand;
import com.koala.foundation.domain.GoodsClass;
import com.koala.foundation.domain.GoodsLog;
import com.koala.foundation.domain.GoodsSpecProperty;
import com.koala.foundation.domain.GoodsTest;
import com.koala.foundation.domain.GoodsTypeProperty;
import com.koala.foundation.domain.Group;
import com.koala.foundation.domain.GroupGoods;
import com.koala.foundation.domain.OrderFormLog;
import com.koala.foundation.domain.Store;

import com.koala.foundation.domain.User;
import com.koala.foundation.domain.UserGoodsClass;
import com.koala.foundation.domain.query.ConsultQueryObject;
import com.koala.foundation.domain.query.EvaluateQueryObject;
import com.koala.foundation.domain.query.GoodsQueryObject;
import com.koala.foundation.domain.virtual.GoodsCompareView;
import com.koala.foundation.service.IActivityGoodsService;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.IBuyGiftService;
import com.koala.foundation.service.ICombinPlanService;
import com.koala.foundation.service.IConsultSatisService;
import com.koala.foundation.service.IConsultService;
import com.koala.foundation.service.IEnoughReduceService;
import com.koala.foundation.service.IEvaluateService;
import com.koala.foundation.service.IFootPointService;
import com.koala.foundation.service.IGoodsBrandService;
import com.koala.foundation.service.IGoodsCartService;
import com.koala.foundation.service.IGoodsClassService;
import com.koala.foundation.service.IGoodsLogService;
import com.koala.foundation.service.IGoodsService;
import com.koala.foundation.service.IGoodsSpecPropertyService;
import com.koala.foundation.service.IGoodsTestService;
import com.koala.foundation.service.IGoodsTypePropertyService;
import com.koala.foundation.service.IOrderFormService;
import com.koala.foundation.service.IStoreNavigationService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;

import com.koala.foundation.service.IUserConfigService;
import com.koala.foundation.service.IUserGoodsClassService;
import com.koala.foundation.service.IUserService;

import com.koala.manage.admin.tools.OrderFormTools;
import com.koala.manage.admin.tools.UserTools;
import com.koala.manage.seller.tools.TransportTools;
import com.koala.metoo.foundation.service.IGoodsMetooService;
import com.koala.view.web.tools.ActivityViewTools;
import com.koala.view.web.tools.AreaViewTools;
import com.koala.view.web.tools.ConsultViewTools;
import com.koala.view.web.tools.EvaluateViewTools;
import com.koala.view.web.tools.GoodsViewTools;
import com.koala.view.web.tools.IntegralViewTools;
import com.koala.view.web.tools.StoreViewTools;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * <p>
 * Title: GoodsViewAction.java
 * </p>
 * 
 * <p>
 * Description: 商品前台控制器,用来显示商品列表、商品详情、商品其他信息
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012-2014
 * </p>
 * 
 * <p>
 * Company: 沈阳网之商科技有限公司 www.koala.com
 * </p>
 * 
 * @author erikzhang
 * 
 * @date 2014-4-28
 * 
 * @version koala_b2b2c v2.0 2015版 
 */
@Controller
public class GoodsMetooViewAction {
	@Autowired
	private IGoodsMetooService tGoodsService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IEvaluateService evaluateService;
	@Autowired
	private IOrderFormService orderFormService;
	@Autowired
	private IGoodsCartService goodsCartService;
	@Autowired
	private IConsultService consultService;
	@Autowired
	private IGoodsBrandService brandService;
	@Autowired
	private IGoodsSpecPropertyService goodsSpecPropertyService;
	@Autowired
	private IGoodsTypePropertyService goodsTypePropertyService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private AreaViewTools areaViewTools;
	@Autowired
	private GoodsViewTools goodsViewTools;
	@Autowired
	private StoreViewTools storeViewTools;
	@Autowired
	private UserTools userTools;
	@Autowired
	private TransportTools transportTools;
	@Autowired
	private ConsultViewTools consultViewTools;
	@Autowired
	private EvaluateViewTools evaluateViewTools;
	@Autowired
	private IUserService userService;
	@Autowired
	private IStoreNavigationService storenavigationService;
	@Autowired
	private IConsultSatisService consultsatisService;
	@Autowired
	private IntegralViewTools integralViewTools;
	@Autowired
	private IEnoughReduceService enoughReduceService;
	@Autowired
	private IFootPointService footPointService;
	@Autowired
	private IActivityGoodsService actgoodsService;
	@Autowired
	private ActivityViewTools activityViewTools;
	@Autowired
	private IGoodsLogService goodsLogService;
	@Autowired
	private ICombinPlanService combinplanService;
	@Autowired
	private OrderFormTools orderFormTools;
	@Autowired
	private IBuyGiftService buyGiftService;
	
	
		
	@RequestMapping("/goodsMetoo.htm")
	public void goods2(HttpServletRequest request,
			HttpServletResponse response,String id) {
		
	String goodsResult = tGoodsService.goods_List(request, response, id);	
	
		try {
			response.getWriter().print(goodsResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
}