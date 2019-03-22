package com.koala.metoo.foundation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.koala.buyer.domain.Result;
import com.koala.core.dao.IGenericDAO;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.GenericPageList;
import com.koala.core.query.PageObject;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.OrderForm;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.query.OrderFormQueryObject;
import com.koala.foundation.service.IAccessoryService;
import com.koala.foundation.service.IAlbumService;
import com.koala.foundation.service.IEvaluateService;
import com.koala.foundation.service.IExpressCompanyService;
import com.koala.foundation.service.IGoodsCartService;
import com.koala.foundation.service.IGoodsLogService;
import com.koala.foundation.service.IGoodsReturnService;
import com.koala.foundation.service.IGoodsService;
import com.koala.foundation.service.IGroupGoodsService;
import com.koala.foundation.service.IGroupInfoService;
import com.koala.foundation.service.IIntegralLogService;
import com.koala.foundation.service.IOrderFormLogService;
import com.koala.foundation.service.IOrderFormService;
import com.koala.foundation.service.IPaymentService;
import com.koala.foundation.service.IPayoffLogService;
import com.koala.foundation.service.IPredepositLogService;
import com.koala.foundation.service.IReturnGoodsLogService;
import com.koala.foundation.service.IStorePointService;
import com.koala.foundation.service.IStoreService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.ITemplateService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.kuaidi100.service.IExpressInfoService;
import com.koala.lucene.tools.LuceneVoTools;
import com.koala.manage.admin.tools.OrderFormTools;
import com.koala.manage.buyer.tools.ShipTools;
import com.koala.metoo.foundation.service.IGoodsMetooService;
import com.koala.metoo.foundation.service.IOrderFormMetooService;
import com.koala.metoo.foundation.service.IUserMetooService;
import com.koala.msg.MsgTools;
import com.koala.view.web.tools.GoodsViewTools;
@Service
@Transactional
public class OrderFormMetooServiceImpl implements IOrderFormMetooService{

	@Resource(name = "orderFormMetooDAO")
	private IGenericDAO<OrderForm> orderFormMetooDao;
	
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IOrderFormLogService orderFormLogService;
	@Autowired
	private IEvaluateService evaluateService;
	@Autowired 
	private IUserMetooService userMetooService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private ITemplateService templateService;
	@Autowired
	private IStorePointService storePointService;
	@Autowired
	private IPredepositLogService predepositLogService;
	@Autowired
	private IPaymentService paymentService;
	@Autowired
	private IGoodsCartService goodsCartService;
	@Autowired
	private IGroupInfoService groupinfoService;
	@Autowired
	private IGoodsReturnService goodsReturnService;
	@Autowired
	private IExpressCompanyService expressCompayService;
	@Autowired
	private IGroupGoodsService ggService;
	@Autowired
	private OrderFormTools orderFormTools;
	@Autowired
	private IPayoffLogService payoffLogservice;
	@Autowired
	private MsgTools msgTools;
	@Autowired
	private IGoodsMetooService goodsMetooService;
	@Autowired
	private IReturnGoodsLogService returnGoodsLogService;
	@Autowired
	private ShipTools shipTools;
	@Autowired
	private LuceneVoTools luceneVoTools;
	@Autowired
	private IGroupGoodsService groupGoodsService;
	@Autowired
	private IAlbumService albumService;
	@Autowired
	private IAccessoryService accessoryService;
	@Autowired
	private IExpressInfoService expressInfoService;
	@Autowired
	private GoodsViewTools goodsViewTools;
	@Autowired
	private IGoodsLogService goodsLogService;
	@Autowired
	private IIntegralLogService integralLogService;
	@Override
	public OrderForm getObjById(Long id) {
		// TODO Auto-generated method stub
		OrderForm orderForm = this.orderFormMetooDao.get(id);
		if(orderForm != null){
			return orderForm;
		}
		return null;
	}

	@Override
	public IPageList list(IQueryObject properties) {
		// TODO Auto-generated method stub
		if(properties == null){
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(OrderForm.class,construct, query,
				params, this.orderFormMetooDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
						.getCurrentPage(), pageObj.getPageSize() == null ? 0
						: pageObj.getPageSize());
		} else
			pList.doList(0, -1);
		return pList;
	}
	
	public List<OrderForm> query(String query, Map params, int begin, int max) {
		return this.orderFormMetooDao.query(query, params, begin, max);

	}

	
	//多条件查询
	public String order(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String order_id,
			String beginTime, String endTime, String order_status) {
		Map map = new HashMap();
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/buyer_order.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		/*ModelAndView: reference to view with name
		'WEB-INF/templates/zh_cn/system/user/default/usercenter/buyer_order.html';
		model is {CommUtil=com.koala.core.tools.CommUtil@564786ad,
		current_webPath=http://localhost:8080/tfkc_shop,
		domainPath=localhost,
		webPath=http://localhost:8080/tfkc_shop,
		imageWebServer=http://localhost:8080/tfkc_shop,
		config=com.koala.foundation.domain.SysConfig@4f76bd13,
		uconfig=null,
		user=com.koala.foundation.domain.User@6ca4bc0c,
		httpInclude=com.koala.core.tools.HttpInclude@71daf41b,
		current_url=/tfkc_shop/buyer/order_metoo.htm,
		second_domain_view=false,
		orderBy=addTime, orderType=desc,
		orderFormTools=com.koala.manage.admin.tools.OrderFormTools@5dd05302}
		}*/
		OrderFormQueryObject ofqo = new OrderFormQueryObject(currentPage, mv,
				"addTime", "desc");
		User user = this.userMetooService.getObjById(SecurityUserHolder
				.getCurrentUser().getId());
		ofqo.addQuery("obj.user_id", new SysMap("user_id", SecurityUserHolder
				.getCurrentUser().getId().toString()), "=");
		ofqo.addQuery("obj.order_main", new SysMap("order_main", 1), "=");// 只显示主订单,通过主订单完成子订单的加载[是否为主订单，1为主订单，主订单用在买家用户中心显示订单内容]
		ofqo.addQuery("obj.order_cat", new SysMap("order_cat", 2), "!=");//[ 订单分类，0为购物订单，1为手机充值订单 2为生活类团购订单  3为商品类团购订单 4旅游报名订单]
		if (!CommUtil.null2String(order_id).equals("")) {
			ofqo.addQuery("obj.order_id", new SysMap("order_id", "%" + order_id
					+ "%"), "like");
			mv.addObject("order_id", order_id);
		}
		if (!CommUtil.null2String(beginTime).equals("")) {
			ofqo.addQuery("obj.addTime",
					new SysMap("beginTime", CommUtil.formatDate(beginTime)),
					">=");
			mv.addObject("beginTime", beginTime);
		}
		if (!CommUtil.null2String(endTime).equals("")) {
			String ends = endTime + " 23:59:59";
			ofqo.addQuery(
					"obj.addTime",
					new SysMap("endTime", CommUtil.formatDate(ends,
							"yyyy-MM-dd hh:mm:ss")), "<=");
			mv.addObject("endTime", endTime);
		}
		if (!CommUtil.null2String(order_status).equals("")) {
			if (order_status.equals("order_submit")) {// 已经提交
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 10), "=");
			}
			if (order_status.equals("order_pay")) {// 已经付款
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 20), "=");
			}
			if (order_status.equals("order_shipping")) {// 已经发货
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 30), "=");
			}
			if (order_status.equals("order_receive")) {// 已经收货
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 40), "=");
			}
			if (order_status.equals("order_finish")) {// 已经完成
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 50), "=");
			}
			if (order_status.equals("order_cancel")) {// 已经取消
				ofqo.addQuery("obj.order_status",
						new SysMap("order_status", 0), "=");
			}
		}
		/*mv.addObject("orderFormTools", orderFormTools);//订单解析工具，解析订单中json数据 [解析订单商品信息json数据 根据订单id查询该订单中所有商品的价格总和]
		mv.addObject("order_status", order_status);*/
		//map.put("orderFormTools", orderFormTools);
		//map.put("order_status_3", order_status);
		IPageList pList = this.list(ofqo);
		List<OrderForm> orderFormList = pList.getResult();
		List<Map> OrderFormMapList = new ArrayList<Map>();
		for(OrderForm orderForm:orderFormList){
			Map orderFormMap = new HashMap();
		    orderFormMap.put("Order_id", orderForm.getOrder_id());
			orderFormMap.put("receiver_Name", orderForm.getReceiver_Name());
			orderFormMap.put("totalPrice", orderForm.getTotalPrice());
			orderFormMap.put("shipCode", orderForm.getShipCode());
			orderFormMap.put("Coupon_info", orderForm.getCoupon_info());
			//[获取josn格式的优惠券信息，通过tools转换为map集合，通过get取值]
			Map info = orderFormTools.queryCouponInfo(orderForm.getCoupon_info());
			Object coupon_amount = info.get("coupon_amount");
			orderFormMap.put("coupon_amount", coupon_amount);
			orderFormMap.put("payment", orderForm.getPayment());
			OrderFormMapList.add(orderFormMap);
		}
		map.put("orderFormList", OrderFormMapList);
		//CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		// 查询订单信息 统计代付款，待收货，已完成数量
		int[] status = new int[] { 10, 30, 50 }; // 已提交 已发货 已完成 [订单状态，0为订单取消，10为已提交，待付款(代发货)，15为线下付款提交申请(已经取消该付款方式)，16为货到付款，20为已付款待发货（团购为已成团），30为已发货待收货，
		// 35,自提点已经收货，40为已收货, 50买家评价完毕 ,65订单不可评价，到达设定时间，系统自动关闭订单相互评价功能    66：未成团 （已付款）  70:退款申请成功   75:退款申请失败  80:退款成功  85：退款失败  ]
		String[] string_status = new String[] { "order_submit",
				"order_shipping", "order_finish" };
		Map orders_status = new LinkedHashMap();
		for (int i = 0; i < status.length; i++) {
			int size = this.query(
					"select obj.id from OrderForm obj where obj.order_main=1 and obj.user_id="
							+ user.getId().toString()
							+ " and obj.order_status =" + status[i] + "", null,
					-1, -1).size();
			mv.addObject("order_size_" + status[i], size);
			//[计算订单数量，并输出状态码,便于设置旗帜变量]
			orders_status.put(string_status[i], size);
		}
		/*mv.addObject("orders_status", orders_status);
		mv.addObject("orderFormTools", this.orderFormTools);*/
		map.put("orders_status", orders_status);
	//	map.put("orderFormTools", orderFormTools);
		// 猜您喜欢 根据cookie商品的分类 销量查询 如果没有cookie则按销量查询
		List<Goods> your_like_goods = new ArrayList<Goods>();
		Long your_like_GoodsClass = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("goodscookie")) {
					String[] like_gcid = cookie.getValue().split(",", 2);
					Goods goods = this.goodsMetooService.getObjById(CommUtil
							.null2Long(like_gcid[0]));
					if (goods == null)
						break;
					your_like_GoodsClass = goods.getGc().getId();
					your_like_goods = this.goodsMetooService.query(
							"select obj from Goods obj where obj.goods_status=0 and obj.gc.id = "
									+ your_like_GoodsClass
									+ " and obj.id is not " + goods.getId()
									+ " order by obj.goods_salenum desc", null,
							0, 20);
					int gcs_size = your_like_goods.size();
					if (gcs_size < 20) {
						List<Goods> like_goods = this.goodsMetooService.query(
								"select obj from Goods obj where obj.goods_status=0 and obj.id is not "
										+ goods.getId()
										+ " order by obj.goods_salenum desc",
								null, 0, 20 - gcs_size);
						for (int i = 0; i < like_goods.size(); i++) {
							// 去除重复商品
							int k = 0;
							for (int j = 0; j < your_like_goods.size(); j++) {
								if (like_goods.get(i).getId()
										.equals(your_like_goods.get(j).getId())) {
									k++;
								}
							}
							if (k == 0) {
								your_like_goods.add(like_goods.get(i));
							}
						}
					}
					break;
				} else {
					your_like_goods = this.goodsMetooService
							.query("select obj from Goods obj where obj.goods_status=0 order by obj.goods_salenum desc",
									null, 0, 20);
				}
			}
		} else {
			your_like_goods = this.goodsMetooService
					.query("select obj from Goods obj where obj.goods_status=0 order by obj.goods_salenum desc",
							null, 0, 20);
		}
		List<Map> goodsMapList = new ArrayList<Map>();
		for(Goods goods:your_like_goods){
			Map goodsMap = new HashMap();
			goodsMap.put("Id", goods.getId());
			goodsMap.put("Name", goods.getGoods_main_photo().getName());
			goodsMap.put("Path", goods.getGoods_main_photo().getPath());
			goodsMap.put("Ext", goods.getGoods_main_photo().getExt());
			goodsMap.put("Id", goods.getGoods_current_price());
			goodsMapList.add(goodsMap);
		}
		//mv.addObject("your_like_goods", your_like_goods);
		map.put("goodsMapList", goodsMapList);
		Result result = new Result(0,"测试数据",map);
		String OderTemp = Json.toJson(result, JsonFormat.compact());
		return OderTemp;
	}


}
