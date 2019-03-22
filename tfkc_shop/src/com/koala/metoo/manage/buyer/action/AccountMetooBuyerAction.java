package com.koala.metoo.manage.buyer.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.security.MD5Encoder;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.buyer.domain.Result;
import com.koala.core.annotation.SecurityMapping;
import com.koala.core.mv.JModelAndView;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.Md5Encrypt;
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.User;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.metoo.foundation.service.IAreaMetooService;
import com.koala.metoo.foundation.service.IUserMetooService;
import com.koala.msg.MsgTools;
@Controller
public class AccountMetooBuyerAction {
	
	@Autowired 
	private IUserMetooService userMetooService;
		
	@SecurityMapping(title = "个人信息", value = "/buyer/account.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/account_metoo.htm")
	public void account(HttpServletRequest request,
			HttpServletResponse response) {
		String accountMap = this.userMetooService.accountMetooimpl(request, response);
		try {
			response.getWriter().println(accountMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return addressTemp;
	}
	
	@SecurityMapping(title = "个人信息保存", value = "/buyer/account_save.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/account_metoo_save.htm")
	public void account_save(HttpServletRequest request,
			HttpServletResponse response, String area_id, String birthday) {
		String account_save = this.userMetooService.account_metoo_saveimpl(request, response, area_id, birthday);
		try {
			response.getWriter().println(account_save);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 此方法省略 仅用作跳转页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	/*@SecurityMapping(title = "密码修改", value = "/buyer/account_password.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/account_save_password.htm")
	public ModelAndView account_password(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/account_password.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
	} */
	@SecurityMapping(title = "密码修改保存", value = "/buyer/account_password_save.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/account_save_password_save.htm")
	public void account_password_save(HttpServletRequest request,
			HttpServletResponse response, String old_password,
			String new_password) throws Exception{
	        String accountSavePwdTemp = this.userMetooService.account_metoo_password_saveimpl(request, response, old_password, new_password);
		
		    response.getWriter().println(accountSavePwdTemp);
	}
	@SecurityMapping(title = "邮箱修改保存", value = "/buyer/account_email_save.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/account_metoo_email_save.htm")
	public void account_email_save(HttpServletRequest request,
			HttpServletResponse response, String password, String email) {
			      String account_saveEmail = this.userMetooService.account_email_saveimpl(request, response, password, email);
	      try {
			response.getWriter().println(account_saveEmail);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 绑定手机号
	 */
}
