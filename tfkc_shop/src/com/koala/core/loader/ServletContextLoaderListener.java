package com.koala.core.loader;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.koala.core.security.SecurityManager;
import com.koala.core.tools.SpringUtil;

/**
 * 
* <p>Title: ServletContextLoaderListener.java</p>

* <p>Description:系统基础信息加载监听器，目前用来加载系统权限数据，也可以将系统语言包在这里加载进来，该监听器会在系统系统的时候进行一次数据加载 </p>

* <p>Copyright: Copyright (c) 2014</p>

* <p>Company: 湖南创发科技有限公司 www.koala.com</p>

* @author erikzhang

* @date 2014-4-24

* @version koala_b2b2c v2.0 2015版 
 */
public class ServletContextLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		SecurityManager securityManager = this
				.getSecurityManager(servletContext);
		Map<String, String> urlAuthorities = securityManager
				.loadUrlAuthorities();
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
		
		
		SpringUtil.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(servletContext));
		
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		servletContextEvent.getServletContext().removeAttribute(
				"urlAuthorities");
	}

	protected SecurityManager getSecurityManager(ServletContext servletContext) {
		return (SecurityManager) WebApplicationContextUtils
				.getWebApplicationContext(servletContext).getBean(
						"securityManager");
	}
}
