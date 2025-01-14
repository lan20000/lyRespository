package com.koala.core.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>
 * Title: HttpInclude.java
 * </p>
 * 
 * <p>
 * Description:完成于jsp:include page 相同的功能,用于include其它页面以用于布局,可以用于在freemarker,
 * velocity的servlet环境应用中直接include其它http请求
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 湖南创发科技有限公司 www.koala.com
 * </p>
 * 
 * @author erikzhang
 * 
 * @date 2014-4-24
 * 
 * @version koala_b2b2c v2.0 2015版
 */
public class HttpInclude {
	private final static Log log = LogFactory.getLog(HttpInclude.class);

	public static String sessionIdKey = "JSESSIONID";

	private HttpServletRequest request;
	private HttpServletResponse response;

	public HttpInclude(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

	}

	public String include(String includePath) {
		// System.out.println(new Date()+"加载"+includePath);
		StringWriter sw = new StringWriter(8192);
		include(includePath, sw);
		return sw.toString();
	}

	public void include(String includePath, Writer writer) {
		try {
			if (isRemoteHttpRequest(includePath)) {
				getRemoteContent(includePath, writer);
			} else {
				getLocalContent(includePath, writer);
			}
		} catch (ServletException e) {
			throw new RuntimeException("include error,path:" + includePath
					+ " cause:" + e, e);
		} catch (IOException e) {
			throw new RuntimeException("include error,path:" + includePath
					+ " cause:" + e, e);
		}
	}

	private boolean isRemoteHttpRequest(String includePath) {
		String serverName = this.request.getServerName();
		boolean flag = false;
//		System.out.println(!includePath.toLowerCase().startsWith(
//				"http://" + serverName));
//		System.out.println(!includePath.toLowerCase().startsWith(
//				"https://" + serverName));
		if (includePath.toLowerCase().startsWith("http://")
				&& includePath.toLowerCase().startsWith("https://")
				&& !includePath.toLowerCase()
						.startsWith("http://" + serverName)
				&& !includePath.toLowerCase().startsWith(
						"https://" + serverName)) {
			flag = true;
		}
		return includePath != null && flag;
	}

	private void getLocalContent(String includePath, Writer writer)
			throws ServletException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);
		CustomOutputHttpServletResponseWrapper customResponse = new CustomOutputHttpServletResponseWrapper(
				response, writer, outputStream);
		String url_path = includePath.indexOf("?") > 0 ? includePath.substring(
				0, includePath.indexOf("?")) : includePath;
		String query = includePath.indexOf("?") > 0 ? includePath
				.substring(includePath.indexOf("?") + 1) : "";
		String[] params = query.split("&");
		for (String param : params) {
			if (param != null && !param.equals("")) {
				String[] list = param.split("=");
				if (list.length == 2)
					request.setAttribute(list[0], list[1]);
			}
		}
		request.getRequestDispatcher(url_path).include(request, customResponse);
		customResponse.flushBuffer();
		if (customResponse.useOutputStream) {
			writer.write(outputStream.toString(response.getCharacterEncoding())); // TODO:
			// response.getCharacterEncoding()有可能为null
		}
		writer.flush();
	}

	// TODO handle cookies and http query parameters encoding
	// TODO set inheritParams from request
	private void getRemoteContent(String urlString, Writer writer)
			throws MalformedURLException, IOException {
		URL url = new URL(getWithSessionIdUrl(urlString));
		URLConnection conn = url.openConnection();
		setConnectionHeaders(urlString, conn);
		InputStream input = conn.getInputStream();
		try {
			Reader reader = new InputStreamReader(input,
					Utils.getContentEncoding(conn, response));
			Utils.copy(reader, writer);
		} finally {
			if (input != null)
				input.close();
		}
		writer.flush();
	}

	private void setConnectionHeaders(String urlString, URLConnection conn) {
		conn.setReadTimeout(6000);
		conn.setConnectTimeout(6000);
		String cookie = getCookieString();
		conn.setRequestProperty("Cookie", cookie);
		// TODO: 用于支持 httpinclude_header.properties
		// conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U;
		// Windows NT 5.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3");
		// conn.setRequestProperty("Host", url.getHost());
		if (log.isDebugEnabled()) {
			log.debug("request properties:" + conn.getRequestProperties()
					+ " for url:" + urlString);
		}
	}

	// TODO add session id with url
	private String getWithSessionIdUrl(String url) {
		return url;
	}

	private static final String SET_COOKIE_SEPARATOR = "; ";

	private String getCookieString() {
		StringBuffer sb = new StringBuffer(64);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (!sessionIdKey.equals(c.getName())) {
					sb.append(c.getName()).append("=").append(c.getValue())
							.append(SET_COOKIE_SEPARATOR);
				}
			}
		}

		String sessionId = Utils.getSessionId(request);
		if (sessionId != null) {
			sb.append(sessionIdKey).append("=").append(sessionId)
					.append(SET_COOKIE_SEPARATOR);
		}
		return sb.toString();
	}

	public static class CustomOutputHttpServletResponseWrapper extends
			HttpServletResponseWrapper {
		public boolean useWriter = false;
		public boolean useOutputStream = false;
		//
		private PrintWriter printWriter;
		private ServletOutputStream servletOutputStream;

		public CustomOutputHttpServletResponseWrapper(
				HttpServletResponse response, final Writer customWriter,
				final OutputStream customOutputStream) {
			super(response);
			this.printWriter = new PrintWriter(customWriter);
			this.servletOutputStream = new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					customOutputStream.write(b);
				}

				@Override
				public void write(byte[] b) throws IOException {
					customOutputStream.write(b);
				}

				@Override
				public void write(byte[] b, int off, int len)
						throws IOException {
					customOutputStream.write(b, off, len);
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setWriteListener(WriteListener arg0) {
					// TODO Auto-generated method stub
					
				}
			};
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (useOutputStream)
				throw new IllegalStateException(
						"getOutputStream() has already been called for this response");
			useWriter = true;
			return printWriter;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (useWriter)
				throw new IllegalStateException(
						"getWriter() has already been called for this response");
			useOutputStream = true;
			return servletOutputStream;
		}

		@Override
		public void flushBuffer() throws IOException {
			if (useWriter)
				printWriter.flush();
			if (useOutputStream)
				servletOutputStream.flush();
		}

	}

	static class Utils {
		static String getContentEncoding(URLConnection conn,
				HttpServletResponse response) {
			String contentEncoding = conn.getContentEncoding();
			if (conn.getContentEncoding() == null) {
				contentEncoding = parseContentTypeForCharset(conn
						.getContentType());
				if (contentEncoding == null) {
					contentEncoding = response.getCharacterEncoding();
				}
			} else {
				contentEncoding = conn.getContentEncoding();
			}
			return contentEncoding;
		}

		static Pattern p = Pattern.compile("(charset=)(.*)",
				Pattern.CASE_INSENSITIVE);

		private static String parseContentTypeForCharset(String contentType) {
			if (contentType == null)
				return null;
			Matcher m = p.matcher(contentType);
			if (m.find()) {
				return m.group(2).trim();
			}
			return null;
		}

		private static void copy(Reader in, Writer out) throws IOException {
			char[] buff = new char[8192];
			while (in.read(buff) >= 0) {
				out.write(buff);
			}
		}

		private static String getSessionId(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return null;
			}
			return session.getId();
		}
	}
}
