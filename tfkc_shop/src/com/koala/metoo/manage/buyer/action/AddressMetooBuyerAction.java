package com.koala.metoo.manage.buyer.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.query.AddressQueryObject;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.IIntegralGoodsOrderService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.metoo.foundation.service.IAddressMetooService;
import com.koala.metoo.foundation.service.IAreaMetooService;



@Controller
public class AddressMetooBuyerAction {
	@Autowired
	private IAddressMetooService  addressMetooService;
	@Autowired
	private IAreaMetooService areaMetooService;
	/**
	 * Address列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "收货地址列表", value = "/buyer/address.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo.htm")
	public void address(HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String address = this.addressMetooService.address_metoo_listimpl(request,response,currentPage);
		
		try {
			
			response.getWriter().print(address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@SecurityMapping(title = "新增收货地址", value = "/buyer/address_add.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_add.htm")
	public void address_Add(HttpServletRequest request,
			HttpServletResponse response, String currentPage){
		//addressMetooService.address_Metoo_Add();
		String areaParent = this.areaMetooService.areaParent(request, response, currentPage);
		try {
			response.getWriter().print(areaParent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@SecurityMapping(title = "编辑收货地址", value = "/buyer/address_edit.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_edit.htm")
	public void address_edit(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		/*ModelAndView mv = new JModelAndView(
				"user/default/usercenter/address_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);*/
		String address_edit = this.addressMetooService.address_metoo_editimpl(request, response, id, currentPage);
		try {
			response.getWriter().print(address_edit);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id 地址
	 * @param area_id 区域
	 * @param currentPage 当前页
	 * @return
	 */
	@SecurityMapping(title = "收货地址保存", value = "/buyer/address_save.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_save.htm")
	public String address_save(HttpServletRequest request,
			HttpServletResponse response, String id, String area_id,
			String currentPage) {
		String temp = this.addressMetooService.address_metoo_saveimpl(request, response, id, area_id, currentPage);
		return temp;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mulitId
	 * @param currentPage
	 */
	@SecurityMapping(title = "收货地址删除", value = "/buyer/address_del.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_Metoo_del.htm")
	public void address_del(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
	String deltetemp = this.addressMetooService.address_metoo_delimpl(request, response, mulitId, currentPage);
	try {
		response.getWriter().println(deltetemp);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mulitId
	 * @param currentPage
	 */
	@SecurityMapping(title = "收货地址默认设置", value = "/buyer/address_default.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_default.htm")
	public void address_default(HttpServletRequest request, HttpServletResponse response,String mulitId,String currentPage){
		
			String  default_result= this.addressMetooService.address_metoo_defaultimpl(request, response, mulitId, currentPage);
			try {
				response.getWriter().println(default_result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	@SecurityMapping(title = "收货地址默认取消", value = "/buyer/address_default_cancle.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_metoo_default_cancle.htm")
	public void address__default_cancle(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
		String default_cel_result=this.addressMetooService.address__default_cancleimpl(request, response, mulitId , currentPage);
		try {
			response.getWriter().println(default_cel_result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
