package com.koala.manage.admin.action;

import java.text.ParseException;
import java.util.Date;

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
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.ComplaintSubject;
import com.koala.foundation.domain.query.ComplaintSubjectQueryObject;
import com.koala.foundation.service.IComplaintSubjectService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;

/**
 * 
 * <p>
 * Title: ComplaintSubjectManageAction.java
 * </p>
 * 
 * <p>
 * Description: 投诉主题管理控制器，用来管理投诉主题，用户在投诉商品时候需要选择一个投诉主题，便于管理员快速分类处理投诉信息
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
 * @date 2014-10-16
 * 
 * @version koala_b2b2c 2015
 */
@Controller
public class ComplaintSubjectManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IComplaintSubjectService complaintsubjectService;

	/**
	 * ComplaintSubject列表页
	 * 
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param request
	 * @return
	 */
	@SecurityMapping(title = "投诉主题列表", value = "/admin/complaintsubject_list.htm*", rtype = "admin", rname = "投诉管理", rcode = "complaint_manage", rgroup = "交易")
	@RequestMapping("/admin/complaintsubject_list.htm")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType,String q_type) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/complaintsubject_list.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if (url == null || url.equals("")) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		ComplaintSubjectQueryObject qo = new ComplaintSubjectQueryObject(
				currentPage, mv, orderBy, orderType);
		WebForm wf = new WebForm();
		wf.toQueryPo(request, qo, ComplaintSubject.class, mv);
		if(!CommUtil.null2String(q_type).equals("")){
			qo.addQuery("obj.type", new SysMap("type", q_type), "=");
			mv.addObject("type", q_type);
		}
		IPageList pList = this.complaintsubjectService.list(qo);
		CommUtil.saveIPageList2ModelAndView(url
				+ "/admin/complaintsubject_list.htm", "", params, pList, mv);
		return mv;
	}

	/**
	 * complaintsubject添加管理
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@SecurityMapping(title = "投诉主题添加", value = "/admin/complaintsubject_add.htm*", rtype = "admin", rname = "投诉管理", rcode = "complaint_manage", rgroup = "交易")
	@RequestMapping("/admin/complaintsubject_add.htm")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/complaintsubject_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("currentPage", currentPage);
		return mv;
	}

	/**
	 * complaintsubject编辑管理
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@SecurityMapping(title = "投诉主题编辑", value = "/admin/complaintsubject_edit.htm*", rtype = "admin", rname = "投诉管理", rcode = "complaint_manage", rgroup = "交易")
	@RequestMapping("/admin/complaintsubject_edit.htm")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/complaintsubject_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		if (id != null && !id.equals("")) {
			ComplaintSubject complaintsubject = this.complaintsubjectService
					.getObjById(Long.parseLong(id));
			mv.addObject("obj", complaintsubject);
			mv.addObject("currentPage", currentPage);
			mv.addObject("edit", true);
		}
		return mv;
	}

	/**
	 * complaintsubject保存管理
	 * 
	 * @param id
	 * @return
	 */
	@SecurityMapping(title = "投诉主题保存", value = "/admin/complaintsubject_save.htm*", rtype = "admin", rname = "投诉管理", rcode = "complaint_manage", rgroup = "交易")
	@RequestMapping("/admin/complaintsubject_save.htm")
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage,
			String cmd, String list_url, String add_url) {
		WebForm wf = new WebForm();
		ComplaintSubject complaintsubject = null;
		if (id.equals("")) {
			complaintsubject = wf.toPo(request, ComplaintSubject.class);
			complaintsubject.setAddTime(new Date());
		} else {
			ComplaintSubject obj = this.complaintsubjectService.getObjById(Long
					.parseLong(id));
			complaintsubject = (ComplaintSubject) wf.toPo(request, obj);
		}

		if (id.equals("")) {
			this.complaintsubjectService.save(complaintsubject);
		} else
			this.complaintsubjectService.update(complaintsubject);
		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("list_url", list_url);
		mv.addObject("op_title", "保存投诉主题成功");
		if (add_url != null) {
			mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
		}
		return mv;
	}

	@SecurityMapping(title = "投诉主题删除", value = "/admin/complaintsubject_del.htm*", rtype = "admin", rname = "投诉管理", rcode = "complaint_manage", rgroup = "交易")
	@RequestMapping("/admin/complaintsubject_del.htm")
	public String delete(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				ComplaintSubject complaintsubject = this.complaintsubjectService
						.getObjById(Long.parseLong(id));
				this.complaintsubjectService.delete(Long.parseLong(id));
			}
		}
		return "redirect:complaintsubject_list.htm?currentPage=" + currentPage;
	}
}