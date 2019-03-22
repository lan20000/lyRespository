package com.koala.metoo.foundation.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
@Service
@Transactional
public class UserMetooServiceImpl implements IUserMetooService{

	@Resource(name = "userMetooDAO")
	private IGenericDAO<User> userMetooDao;
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IAreaMetooService areaMetooService;
	@Autowired
	private MsgTools msgTools;
	@Override
	public User getObjById(Long id) {
		// TODO Auto-generated method stub
		
		User user = this.userMetooDao.get(id);
		if(user != null){
			return user;
		}
		return null;
	}

	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		try {
			this.userMetooDao.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	   //个人信息
		public String accountMetooimpl(HttpServletRequest request,
				HttpServletResponse response) {
			Result result = new Result();
			Map map = new HashMap();
			Map userMap = new HashMap();
			Map addressMap = new HashMap();
			ModelAndView mv = new JModelAndView(
					"user/default/usercenter/account.html",
					configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request, response);
			/*mv.addObject("user", this.userMetooService.getObjById(SecurityUserHolder
					.getCurrentUser().getId()));*/
			User user = this.getObjById(SecurityUserHolder
					.getCurrentUser().getId());
			userMap.put("HeadImgUrl", user.getHeadImgUrl());
			userMap.put("TrueName", user.getTrueName());
			userMap.put("Sex", user.getSex());
			userMap.put("Email", user.getEmail());
			userMap.put("Telephone", user.getTelephone());
			userMap.put("Birthday", user.getBirthday());
			
			List<Area> areas = this.areaMetooService.query(
					"select obj from Area obj where obj.parent.id is null", null,
					-1, -1);
			mv.addObject("areas", areas);
			List<Map> arealist = new ArrayList<Map>();
			for (Area area : areas) {
				Map areaMap = new HashMap();
				areaMap.put("id", area.getId());
				areaMap.put("areaName", area.getAreaName());
				arealist.add(areaMap);
				map.put("areaMap", arealist);
			}
			map.put("userMap", userMap);
			
			if(map != null){
				result = new Result(0,"输出成功",map);
			}else{
				result = new Result(1,"参数错误");
			}
			
			String accountTemp = Json.toJson(result, JsonFormat.compact());
			try {
				response.getWriter().println(accountTemp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return accountTemp;
		}
		//信息保存
		public String account_metoo_saveimpl(HttpServletRequest request,
				HttpServletResponse response, String area_id, String birthday) {
			/*ModelAndView mv = new JModelAndView("success.html",
					configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);*/
			//[封装表单信息]
			Result result = new Result();
			boolean account_save=false;
			WebForm wf = new WebForm();
			//获取当前登录用户对象
			User user = (User) wf.toPo(request, this
					.getObjById(SecurityUserHolder.getCurrentUser().getId()));
			
			if (area_id != null && !area_id.equals("")) {
				Area area = this.areaMetooService
						.getObjById(CommUtil.null2Long(area_id));
			}
			if (birthday != null && !birthday.equals("")) {
				String y[] = birthday.split("-");
				Calendar calendar = new GregorianCalendar();
				int years = calendar.get(Calendar.YEAR) - CommUtil.null2Int(y[0]);
				user.setYears(years);
			}
			account_save = this.update(user);
			/*
			mv.addObject("op_title", "个人信息修改成功");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/account.htm");
			*/
			if(account_save){
				result = new Result(0,"个人信息修改chenggong",account_save);
			}else{
				result = new Result(1,"个人信息修改失败");
			}
			 
			String accountSaveTemp = Json.toJson(result, JsonFormat.compact());
			try {
				response.getWriter().println(accountSaveTemp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return accountSaveTemp;
		}
		//密码保存
		public String account_metoo_password_saveimpl(HttpServletRequest request,
				HttpServletResponse response, String old_password,
				String new_password){
			Result result = new Result();
			User user = this.getObjById(SecurityUserHolder
					.getCurrentUser().getId());
			//获取当前登录用户数据库密码，比较用户输入密码加密 (md5)if-true 对新的密码加密
			if(user.getPassword().equals(
					Md5Encrypt.md5(old_password).toLowerCase())){
				user.setPassword(Md5Encrypt.md5(new_password).toLowerCase());
				boolean pwd = this.update(user);
				if(pwd){
					String content = "尊敬的"
							+ SecurityUserHolder.getCurrentUser().getUserName()
							+ "您好，您于" + CommUtil.formatLongDate(new Date())
							+ "修改密码成功，新密码为：" + new_password + ",请妥善保管。["
							+ this.configService.getSysConfig().getTitle() + "]";
					try {
						this.msgTools.sendSMS(user.getMobile(), content);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result = new Result(0,"密码修改成功",pwd);
				}
			}else{
				result = new Result(1,"原始密码错误");
			}
			String accountSavePwdTemp = Json.toJson(result, JsonFormat.compact());
			try {
				response.getWriter().println(accountSavePwdTemp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return accountSavePwdTemp;
		}
		//修改密码
		public String  account_email_saveimpl(HttpServletRequest request,
				HttpServletResponse response, String password, String email) {
			Result result = new Result(); boolean emailUP =false;
			//[根据当前已登录用户id查询用户密码,对当前密码加密，并比较用户密码]
		      User user = this.getObjById(SecurityUserHolder
		    		  .getCurrentUser().getId());
		      if(user.getPassword().equals(Md5Encrypt.md5(password).toLowerCase())){
		    	  user.setEmail(email);
		    	  emailUP =  this.update(user);
		    	 if(emailUP){
		    		 result = new Result(0,"邮箱修改成功",emailUP);
		    	 }else{
		    		 result = new Result(0,"未知错误！请确认身份",emailUP);
		    	 }
		      }else{
		    	  result = new Result(1,"密码输入错误,邮箱修改失败");
		      }
		      String accountSaveEmailTemp = Json.toJson(result, JsonFormat.compact());
		      return accountSaveEmailTemp;
		}
}
