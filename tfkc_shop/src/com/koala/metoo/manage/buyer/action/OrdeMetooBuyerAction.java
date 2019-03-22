package com.koala.metoo.manage.buyer.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koala.core.annotation.SecurityMapping;
import com.koala.metoo.foundation.service.IOrderFormMetooService;
@Controller
public class OrdeMetooBuyerAction {
	@Autowired 
	private IOrderFormMetooService orderFormMetooService;
	
	@SecurityMapping(title = "买家订单列表", value = "/buyer/order.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/order_metoo.htm")
	public void order(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String order_id,
			String beginTime, String endTime, String order_status){
		String orderTemp = orderFormMetooService.order(request, response, currentPage, order_id, beginTime, endTime, order_status);
		try {
			response.getWriter().println(orderTemp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
