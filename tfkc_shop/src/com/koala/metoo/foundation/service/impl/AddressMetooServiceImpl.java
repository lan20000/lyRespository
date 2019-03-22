package com.koala.metoo.foundation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.GenericPageList;
import com.koala.core.query.PageObject;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;
import com.koala.core.tools.WebForm;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.query.AddressQueryObject;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;
import com.koala.metoo.foundation.service.IAddressMetooService;
import com.koala.metoo.foundation.service.IAreaMetooService;
@Service
@Transactional
public class AddressMetooServiceImpl implements IAddressMetooService{

	@Resource(name = "AddressMetooDao")
	private IGenericDAO<Address>  addressMetooDao;
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IAreaMetooService areaMetooService;
	
	public boolean save(Address address) {
		try {
			this.addressMetooDao.save(address);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
	   }	
	}
	
	public boolean update(Address address) {
		// TODO Auto-generated method stub
		try {
			this.addressMetooDao.update(address);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
	     try {
			this.addressMetooDao.remove(id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Address getObjById(Long id) {
		// TODO Auto-generated method stub
		Address address = this.addressMetooDao.get(id);
		if(address != null){
			return address;
		}
		return null;
	}

	public List<Address> query(String query, Map params, int begin, int max) {
		// TODO Auto-generated method stub
		return this.addressMetooDao.query(query, params, begin, max);
	}

	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(Address.class, construct,
				query, params, this.addressMetooDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(
						pageObj.getCurrentPage() == null ? 0 : pageObj
								.getCurrentPage(),
						pageObj.getPageSize() == null ? 0 : pageObj
								.getPageSize());
		} else
			pList.doList(0, -1);
		return pList;
	}
	
	public String address_metoo_listimpl(HttpServletRequest request, HttpServletResponse response, String currentPage) {
		        //第一个参数可省略
		Result result = null;
		Map addressMap = new HashMap();
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/address.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		
		String url = this.configService.getSysConfig().getAddress();//http://ebuyair.metoo-souq.com/
		if (url == null || url.equals("")) {
			url = CommUtil.getURL(request);//http://ebuyair.metoo-souq.com/
		}
        String params = "";
		
		AddressQueryObject qo = new AddressQueryObject(currentPage, mv,
				"default_val desc,obj.addTime", "desc");
		qo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder //[未登录空指针异常]
				.getCurrentUser().getId()), "=");
		IPageList pList = this.list(qo);
		List<Map> addressList = CommUtil.saveIPageList2ModelAndView2(pList);
		addressMap.put("addresList", addressList);
		List<Area> areas = this.areaMetooService.query(
				"select obj from Area obj where obj.parent.id is null", null,
				-1, -1);
		mv.addObject("areas", areas);
		List<Map> arealist = new ArrayList<Map>();
		for (Area area : areas) {
			Map areaMap = new HashMap();
			areaMap.put("id", area.getId());
			areaMap.put("areaName", area.getAreaName());
			//无法使用这种方式得到Childs Parent
			//map.put("Childs", area.getChilds());
			//map.put("Parent", area.getParent());
			
			arealist.add(areaMap);
			addressMap.put("areaMap", arealist);
		}
		if(!addressMap.isEmpty()){
			result = new Result(0,"查询成功",addressMap);
		}else{
			result = new Result(1,"您目前没有收货地址！");
		}
		String addressTemp = Json.toJson(result, JsonFormat.compact());
		return addressTemp;
	}

	@SecurityMapping(title = "编辑收货地址", value = "/buyer/address_edit.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping("/buyer/address_Metoo_edit.htm")
	public String address_metoo_editimpl(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		/*ModelAndView mv = new JModelAndView(
				"user/default/usercenter/address_add.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);*/
		List<Area> areas = this.areaMetooService.query(
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
		
		Address obj = this.getObjById(CommUtil.null2Long(id));
		currentPageMap.put("TrueName",obj.getTrueName());
		currentPageMap.put("AreaName",obj.getArea().getAreaName());
		currentPageMap.put("parentName",obj.getArea().getParent().getAreaName());
		currentPageMap.put("parentParentName",obj.getArea().getParent().getParent().getAreaName());
		currentPageMap.put("Area_info",obj.getArea_info());
		currentPageMap.put("Area_zip",obj.getZip());
		String addressTemp=null;
		//比较当前用户id与地址对应得id是否相同
		if (obj.getUser().getId()
				.equals(SecurityUserHolder.getCurrentUser().getId())) {// 只允许修改自己的地址信息
		}
		Result result = new Result(0,"输出成功",currentPageMap);
		 addressTemp = Json.toJson(result, JsonFormat.compact());
		try {
			response.getWriter().println(addressTemp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addressTemp;
	}

	/**
	 * address保存管理
	 * 
	 * @param id
	 * @return
	 */
	
	public String address_metoo_saveimpl(HttpServletRequest request,
			HttpServletResponse response, String id, String area_id,
			String currentPage) {
		boolean saveboolean = false;
		Result result = new Result();
		WebForm wf = new WebForm();//封装添加表单对象
		Address address = null;
		//[封装form表单到pojo]
		//[如果id为空为添加新增，否则为更新保存]
		if (id.equals("")) {
			address = wf.toPo(request, Address.class);
			address.setAddTime(new Date());
		} else {
			//[获取当前地址对应用户与当前登录用户比较]
			Address obj = this.getObjById(Long.parseLong(id));
			if (obj.getUser().getId()
					.equals(SecurityUserHolder.getCurrentUser().getId())) {// 只允许修改自己的地址信息
				address = (Address) wf.toPo(request, obj);
			}
		}
		//[添加地址管关联对象为当前用户]
		address.setUser(SecurityUserHolder.getCurrentUser());
		Area area = this.areaService.getObjById(CommUtil.null2Long(area_id));
		address.setArea(area);
		if (id.equals("")) {
			
		 saveboolean= this.save(address);
			if(saveboolean) {
				result = new Result(0,"添加成功");
			}else{
				result = new Result(0,"添加失败");
			}
		} else{
			saveboolean = this.update(address);
			if(saveboolean) {
				result = new Result(0,"更新成功");
			}else{
				result = new Result(0,"更新失败");
			}
		}
			try {
				response.getWriter().print(result);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String addressTemp = Json.toJson(result, JsonFormat.compact());
			return addressTemp;
	}
	
	/**
	 * 删除用户地址(仅用户自己)
	 * @param request
	 * @param response
	 * @param mulitId
	 * @param currentPage
	 * @return
	 */
	public String address_metoo_delimpl(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
		Result result = new Result();
		boolean del = false;
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Address address = this.getObjById(Long
						.parseLong(id));
				if (address.getUser().getId()
						.equals(SecurityUserHolder.getCurrentUser().getId())) {// 只允许删除自己的地址信息
					 del= this.delete(Long.parseLong(id));
				}
			}
		}
		Map currentPageMap = new HashMap();
		currentPageMap.put("currentPage", currentPage);
		if(del == true){
			 result = new Result(0,"删除成功",currentPageMap);
		}else{
			result = new Result(0,"删除失败");
		}
		String deleteTemp = Json.toJson(result, JsonFormat.compact());
		return deleteTemp;
	}
	/**
	 * 设置地址默认设置
	 * @param request
	 * @param response
	 * @param mulitId
	 * @param currentPage
	 * @return
	 */
	public String address_metoo_defaultimpl(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
		Result result = new Result();
		boolean defaultbol = false;
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Address address = this.getObjById(Long
						.parseLong(id));
				if (address.getUser().getId()
						.equals(SecurityUserHolder.getCurrentUser().getId())) {// 只允许修改自己的地址信息
					//[设置查询条件]
					Map<String, Number> params = new HashMap<String, Number>();
					params.put("user_id", SecurityUserHolder.getCurrentUser()
							.getId());
					params.put("id", CommUtil.null2Long(id));
					params.put("default_val", 1);//[是否为默认收货地址，1为默认地址]
					//[获取当前用户的默认地址 ]
					List<Address> addrs = this
							.query("select obj from Address obj where obj.user.id=:user_id and obj.id!=:id and obj.default_val=:default_val",
									params, -1, -1);
					Map currentPageMap = new HashMap();
					currentPageMap.put("currentPage", currentPage);
					//[循环设值为0并更新 ]
					for (Address addr1 : addrs) {
						addr1.setDefault_val(0);
						this.update(addr1);
					}
					 address.setDefault_val(1);
					 defaultbol = this.update(address);
						if(defaultbol){
							result = new Result(0,"设置默认地址成功",currentPageMap);
						}else{
							result = new Result(0,"设置默认地址失败");
						}
				}
			}
		}
		String defaultTemp = Json.toJson(result, JsonFormat.compact());
		return defaultTemp;
	}
	
	public String address__default_cancleimpl(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String currentPage) {
		Result result = new Result();
		boolean defaultbol = false;
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Address address = this.getObjById(Long.parseLong(id));
				if (address.getUser().getId()
						.equals(SecurityUserHolder.getCurrentUser().getId())) {// 只允许修改自己的地址信息
					address.setDefault_val(0);
					defaultbol = this.update(address);
				}
			}
		}
		Map currentPageMap = new HashMap();
		currentPageMap.put("currentPage", currentPage);
		if(defaultbol){
			result = new Result(0,"取消默认地址成功",currentPageMap);
		}else{
			result = new Result(0,"取消默认地址失败");
		}
		String defaultTemp = Json.toJson(result, JsonFormat.compact());
		return defaultTemp;
	}
}
