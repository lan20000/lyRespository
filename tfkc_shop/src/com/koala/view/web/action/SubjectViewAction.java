package com.koala.view.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.support.IPageList;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Subject;
import com.koala.foundation.domain.query.SubjectQueryObject;
import com.koala.foundation.service.ISubjectService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.manage.admin.tools.SubjectTools;

/**
 * 
 * <p>
 * Title: SubjectViewAction.java
 * </p>
 * 
 * <p>
 * Description: 专题控制器
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
 * @author hezeng
 * 
 * @date 2014-11-11
 * 
 * @version koala_b2b2c 2.0
 */

@Controller
public class SubjectViewAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private ISubjectService subjectService;
	@Autowired
	private SubjectTools SubjectTools;

	/**
	 * 专题首页,专题列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/subject/index.htm")
	public ModelAndView subject(HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		ModelAndView mv = new JModelAndView("subject.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		SubjectQueryObject qo = new SubjectQueryObject(currentPage, mv,
				"sequence", "asc");
		qo.setPageSize(5);
		qo.addQuery("obj.display", new SysMap("display", 1), "=");
		IPageList pList = (IPageList) this.subjectService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", null, pList, mv);
		return mv;
	}

	/**
	 * 专题详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/subject/view.htm")
	public ModelAndView subject_view(HttpServletRequest request,
			HttpServletResponse response, String id) {
		ModelAndView mv = new JModelAndView("subject_view.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Subject obj = this.subjectService.getObjById(CommUtil.null2Long(id));
		if (obj != null && obj.getSubject_detail() != null) {
			List<Map> objs = (List<Map>) Json.fromJson(obj.getSubject_detail());
			mv.addObject("objs", objs);
		}
		mv.addObject("obj", obj);
		mv.addObject("SubjectTools", SubjectTools);
		return mv;
	}
}
