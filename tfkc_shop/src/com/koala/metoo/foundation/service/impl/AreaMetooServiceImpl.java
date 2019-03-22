package com.koala.metoo.foundation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.koala.buyer.domain.Result;
import com.koala.core.annotation.SecurityMapping;
import com.koala.core.dao.IGenericDAO;
import com.koala.core.mv.JModelAndView;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Area;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.metoo.foundation.service.IAddressMetooService;
import com.koala.metoo.foundation.service.IAreaMetooService;
@Service
@Transactional
public class AreaMetooServiceImpl implements IAreaMetooService{
	@Resource(name = "AreaMetooDao")
	private IGenericDAO<Area> areaMetooDao;
	
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	
	@Override
	public Area getObjById(Long id) {
		// TODO Auto-generated method stub
		Area area = this.areaMetooDao.get(id);
		if(area != null){
			return area;
		}
		return null;
	}

	@Override
	public List<Area> query(String query, Map params, int begin, int max) {
		// TODO Auto-generated method stub
		return this.areaMetooDao.query(query, params, begin, max);
	}
	public String areaParent(HttpServletRequest request,
			HttpServletResponse response, String currentPage){
		Result result= new Result();
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/addres_add.html",
				configService.getSysConfig(),
				
				this.userConfigService.getUserConfig(), 0, request, response);
		//查询area表中，父id为空的字段  [如：北京,天津,山西]
		List<Area> areas = this.query(
				"select obj from Area obj where obj.parent.id is null", null,
				-1, -1);
		Map currentPageMap = new HashMap();
		List<Map> aeraList = new ArrayList<Map>();
		for(Area area:areas){
			Map areaMap = new HashMap();
			areaMap.put("areaId", area.getId());
			areaMap.put("areaName", area.getAreaName());
			
			aeraList.add(areaMap);
			currentPageMap.put("areaMap", aeraList);
		}
		
		currentPageMap.put("currentPage", currentPage);
		/*mv.addObject("areas", areas);
		mv.addObject("currentPage", currentPage);*/
		
		if(currentPageMap.isEmpty()){
			result = new Result(0,"成功",currentPageMap);
		}else{
			result = new Result(1,"区域信息为空！");
		}
		
		
		String addressTemp = Json.toJson(result, JsonFormat.compact());
		
		 
		return addressTemp;
	}



	
	
	
}
