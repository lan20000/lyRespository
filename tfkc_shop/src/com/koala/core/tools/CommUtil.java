package com.koala.core.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.query.support.IPageList;
import com.koala.foundation.domain.Accessory;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.Goods;
import com.koala.foundation.domain.GroupGoods;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.IGoodsService;
import com.koala.lucene.LuceneResult;
import com.koala.lucene.LuceneVo;
import com.koala.view.web.tools.GoodsViewTools;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * <p>
 * Title: CommUtil.java
 * </p>
 * 
 * <p>
 * Description:
 * 系统工具类，用来快速处理,系统默认将该工具类添加到ModelAndView中，前台可以使用$!CommUtil.xxx调用该工具类
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
public class CommUtil {
	private static final java.text.SimpleDateFormat dateFormat = new

	java.text.SimpleDateFormat("yyyy-MM-dd");

	public static String first2low(String str) {
		String s = "";
		s = str.substring(0, 1).toLowerCase() + str.substring(1);
		return s;
	}

	public static String first2upper(String str) {
		String s = "";
		s = str.substring(0, 1).toUpperCase() + str.substring(1);
		return s;
	}

	/**
	 * 用来处理一行一条数据
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static List<String> str2list(String s) throws IOException {
		List<String> list = new ArrayList<String>();
		s = CommUtil.null2String(s);
		if (!s.equals("")) {
			StringReader fr = new StringReader(s);
			BufferedReader br = new BufferedReader(fr);
			String aline = "";
			while ((aline = br.readLine()) != null) {
				list.add(aline);
			}
		}
		return list;
	}

	public static java.util.Date formatDate(String s) {
		java.util.Date d = null;
		try {
			d = dateFormat.parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	public static java.util.Date formatDate(String s, String format) {
		java.util.Date d = null;
		try {
			SimpleDateFormat dFormat = new java.text.SimpleDateFormat(format);
			d = dFormat.parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	public static String formatTime(String format, Object v) {
		if (v == null)
			return null;
		if (v.equals(""))
			return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	public static String formatLongDate(Object v) {
		if (v == null || v.equals(""))
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(v);
	}

	
	public static String formatShortDate(Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(v);
	}

	public static String decode(String s) {
		String ret = s;
		try {
			ret = URLDecoder.decode(s.trim(), "UTF-8");
		} catch (Exception e) {
		}
		return ret;
	}

	public static String encode(String s) {
		String ret = s;
		try {
			ret = URLEncoder.encode(s.trim(), "UTF-8");
		} catch (Exception e) {
		}
		return ret;
	}

	public static String convert(String str, String coding) {
		String newStr = "";
		if (str != null)
			try {
				newStr = new String(str.getBytes("ISO-8859-1"), coding);
			} catch (Exception e) {
				return newStr;
			}
		return newStr;
	}

	/**
	 * saveFileToServer 上传文件保存到服务器
	 * 
	 * @param filePath为上传文件的名称
	 *            ，
	 * @param saveFilePathName为文件保存全路径
	 * @param saveFileName为保存的文件
	 * @param extendes为允许的文件扩展名
	 *            , *
	 * @return 返回一个map，map中有4个值，第一个为保存的文件名fileName,第二个为保存的文件大小fileSize,,
	 *         第三个为保存文件时错误信息errors,如果生成缩略图则map中保存smallFileName，表示缩略图的全路径
	 */
	public static Map saveFileToServer(HttpServletRequest request,
			String filePath, String saveFilePathName, String saveFileName,
			String[] extendes) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile(filePath);
		Map map = new HashMap();
		if (file != null && !file.isEmpty()) {
			// System.out.println("文件名为：" + file.getOriginalFilename());
			String extend = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();
			if (saveFileName == null || saveFileName.trim().equals("")) {
				saveFileName = UUID.randomUUID().toString() + "." + extend;
			}
			if (saveFileName.lastIndexOf(".") < 0) {
				saveFileName = saveFileName + "." + extend;
			}
			float fileSize = Float.valueOf(file.getSize());// 返回文件大小，单位为k
			List<String> errors = new java.util.ArrayList<String>();
			boolean flag = true;
			if (extendes == null) {
				extendes = new String[] { "jpg", "jpeg", "gif", "bmp", "tbi",
						"png" };
			}
			for (String s : extendes) {
				if (extend.toLowerCase().equals(s))
					flag = true;
			}
			if (flag) {
				File path = new File(saveFilePathName);
				if (!path.exists()) {
					path.mkdir();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				DataOutputStream out = new DataOutputStream(
						new FileOutputStream(saveFilePathName + File.separator
								+ saveFileName));
				InputStream is = null;
				try {
					is = file.getInputStream();
					int size = (int) (fileSize);
					byte[] buffer = new byte[size];
					while (is.read(buffer) > 0) {
						out.write(buffer);
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				} finally {
					if (is != null) {
						is.close();
					}
					if (out != null) {
						out.close();
					}
				}
				if (isImg(extend)) {
					File img = new File(saveFilePathName + File.separator
							+ saveFileName);
					try {
						BufferedImage bis = ImageIO.read(img);
						int w = bis.getWidth();
						int h = bis.getHeight();
						map.put("width", w);
						map.put("height", h);
					} catch (Exception e) {
						// map.put("width", 200);
						// map.put("heigh", 100);
					}
				}
				map.put("mime", extend);
				map.put("fileName", saveFileName);
				map.put("fileSize", fileSize);
				map.put("error", errors);
				map.put("oldName", file.getOriginalFilename());
				// System.out.println("上传结束，生成的文件名为:" + fileName);
			} else {
				// System.out.println("不允许的扩展名");
				errors.add("不允许的扩展名");
			}
		} else {
			map.put("width", 0);
			map.put("height", 0);
			map.put("mime", "");
			map.put("fileName", "");
			map.put("fileSize", 0.0f);
			map.put("oldName", "");
		}
		return map;
	}

	public static boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = new java.util.ArrayList<String>();
		list.add("jpg");
		list.add("jpeg");
		list.add("bmp");
		list.add("gif");
		list.add("png");
		list.add("tif");
		list.add("tbi");
		for (String s : list) {
			if (s.equals(extend))
				ret = true;
		}
		return ret;
	}

	/**
	 * 图片水印，一般使用gif png格式，其中png质量较好
	 * 
	 * @param pressImg
	 *            水印文件
	 * @param targetImg
	 *            目标文件
	 * @param pos
	 *            水印位置，使用九宫格控制
	 * @param alpha
	 *            水印图片透明度
	 */
	public final static void waterMarkWithImage(String pressImg,
			String targetImg, int pos, float alpha) {
		try {
			// 目标文件
			Image theImg = Toolkit.getDefaultToolkit().getImage(targetImg);
			theImg.flush();
			BufferedImage bis = toBufferedImage(theImg);
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			bis = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bis.createGraphics();
			g.drawImage(theImg, 0, 0, width, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha / 100));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			int x = 0;
			int y = 0;
			if (pos == 1) {

			}
			if (pos == 2) {
				x = (width - width_biao) / 2;
				y = 0;
			}
			if (pos == 3) {
				x = width - width_biao;
				y = 0;
			}
			if (pos == 4) {
				x = width - width_biao;
				y = (height - height_biao) / 2;
			}
			if (pos == 5) {
				x = width - width_biao;
				y = height - height_biao;
			}
			if (pos == 6) {
				x = (width - width_biao) / 2;
				y = height - height_biao;
			}
			if (pos == 7) {
				x = 0;
				y = height - height_biao;
			}
			if (pos == 8) {
				x = 0;
				y = (height - height_biao) / 2;
			}
			if (pos == 9) {
				x = (width - width_biao) / 2;
				y = (height - height_biao) / 2;
			}
			g.drawImage(src_biao, x, y, width_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			ImageIO.write(bis, "JPEG", out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建小图片 V1.3使用，改写算法，图片清晰度更好
	 * 
	 * @param source
	 *            原图片
	 * @param target
	 *            目标图片
	 * @param width
	 *            图片宽度，高度自动根据比例计算
	 * @return 创建图片是否成功
	 */
	public static boolean createSmall(String source, String target, int width,
			int height) {
		try {
			Image img = Toolkit.getDefaultToolkit().getImage(source);
			BufferedImage bis = toBufferedImage(img);
			int w = bis.getWidth();
			int h = bis.getHeight();
			int nw = width;
			int nh = (nw * h) / w;
			ImageCompress.ImageScale(source, target, width, height);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 读取图片为bufferedimage,修正图片读取ICC信息丢失而导致出现红色遮罩
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		// boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			/*
			 * if (hasAlpha) { transparency = Transparency.BITMASK; }
			 */

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	/**
	 * 创建小图片 V 1.3版前使用
	 * 
	 * @param source
	 *            原图片
	 * @param target
	 *            目标图片
	 * @param width
	 *            图片宽度，高度自动根据比例计算
	 * @return 创建图片是否成功
	 */
	public static boolean createSmall_old(String source, String target,
			int width) {
		try {
			File sourceFile = new File(source);
			File targetFile = new File(target);
			BufferedImage bis = ImageIO.read(sourceFile);
			int w = bis.getWidth();
			int h = bis.getHeight();
			int nw = width;
			int nh = (nw * h) / w;
			ImageScale is = new ImageScale();
			is.saveImageAsJpg(source, target, nw, nh);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param outPath
	 *            添加水印后的输出路径
	 * @param markContent
	 *            水印的文字
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param font
	 *            文字字体 大小等
	 * @param left
	 *            水印的位置，距离图片左上角位置
	 * @param top
	 *            水印的位置，距离图片顶部位置
	 * @param qualNum
	 *            图片质量
	 * @return
	 */
	public static boolean waterMarkWithText(String filePath, String outPath,
			String text, String markContentColor, Font font, int pos,
			float qualNum) {
		Image theImg = Toolkit.getDefaultToolkit().getImage(filePath);
		theImg.flush();
		BufferedImage bis = toBufferedImage(theImg);
		int width = bis.getWidth(null);
		int height = bis.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		if (font == null) {
			font = new Font("黑体", Font.BOLD, 30);
			g.setFont(font);
		} else {
			g.setFont(font);
		}
		g.setColor(getColor(markContentColor));
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		FontMetrics metrics = new FontMetrics(font) {
		};
		Rectangle2D bounds = metrics.getStringBounds(text, null);
		int widthInPixels = (int) bounds.getWidth();
		int heightInPixels = (int) bounds.getHeight();
		int left = 0;
		int top = heightInPixels;
		if (pos == 1) {

		}
		if (pos == 2) {
			left = width / 2;
			top = heightInPixels;
		}
		if (pos == 3) {
			left = width - widthInPixels;
			top = heightInPixels;
		}
		if (pos == 4) {
			left = width - widthInPixels;
			top = height / 2;
		}
		if (pos == 5) {
			left = width - widthInPixels;
			top = height - heightInPixels;
		}
		if (pos == 6) {
			left = width / 2;
			top = height - heightInPixels;
		}
		if (pos == 7) {
			left = 0;
			top = height - heightInPixels;
		}
		if (pos == 8) {
			left = 0;
			top = height / 2;
		}
		if (pos == 9) {
			left = width / 2;
			top = height / 2;
		}
		g.drawString(text, left, top); // 添加水印的文字和设置水印文字出现的内容
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(outPath);
			ImageIO.write(bimage, "JPEG", out);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean createFolder(String folderPath) {
		boolean ret = true;
		try {
			java.io.File myFilePath = new java.io.File(folderPath);
			if (!myFilePath.exists() && !myFilePath.isDirectory()) {
				ret = myFilePath.mkdirs();
				if (!ret) {
					System.out.println("创建文件夹出错");
				}
			}
		} catch (Exception e) {
			System.out.println("创建文件夹出错");
			ret = false;
		}
		return ret;
	}

	public static List toRowChildList(List list, int perNum) {
		// System.out.println("执行toRowChildList");
		List l = new java.util.ArrayList();
		if (list == null)
			return l;
		// System.out.println("照片："+list.size());
		// System.out.println("perNum:"+perNum);
		for (int i = 0; i < list.size(); i += perNum) {
			List cList = new ArrayList();
			for (int j = 0; j < perNum; j++)
				if (i + j < list.size())
					cList.add(list.get(i + j));
			l.add(cList);
		}
		return l;
	}

	public static List copyList(List list, int begin, int end) {
		List l = new ArrayList();
		if (list == null)
			return l;
		if (end > list.size())
			end = list.size();
		for (int i = begin; i < end; i++) {
			l.add(list.get(i));
		}
		return l;
	}

	public static boolean isNotNull(Object obj) {
		if (obj != null && !obj.toString().equals("")) {
			return true;
		} else
			return false;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			e.printStackTrace();
		}
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param path
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String path) {
		boolean flag = false;
		File file = new File(path);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(path);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(path);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param path
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param path
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String path) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 静态分页，结合urlwriter使用
	 * 
	 * @param url
	 * @param currentPage
	 * @param pages
	 * @return
	 */
	public static String showPageStaticHtml(String url, int currentPage,
			int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s += "<a href='" + url + "_1.htm'>首页</a> ";
				if (currentPage > 1)
					s += "<a href='" + url + "_" + (currentPage - 1)
							+ ".htm'>上一页</a> ";
			}

			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s += "第　";
				for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
					if (i == currentPage)
						s += "<a class='this' href='" + url + "_" + i
								+ ".htm'>" + i + "</a> ";
					else
						s += "<a href='" + url + "_" + i + ".htm'>" + i
								+ "</a> ";
				s += "页　";
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s += "<a href='" + url + "_" + (currentPage + 1)
							+ ".htm'>下一页</a> ";
				}
				s += "<a href='" + url + "_" + pages + ".htm'>末页</a> ";
			}
		}
		return s;
	}

	/**
	 * 常规的分页信息，使用get传递参数
	 * 
	 * @param url
	 * @param params
	 * @param currentPage
	 * @param pages
	 * @return
	 */
	public static String showPageHtml(String url, String params,
			int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s += "<a href='" + url + "?currentPage=1" + params
						+ "'>首页</a> ";
				if (currentPage > 1)
					s += "<a href='" + url + "?currentPage="
							+ (currentPage - 1) + params + "'>上一页</a> ";
			}

			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s += "第　";
				for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
					if (i == currentPage)
						s += "<a class='this' href='" + url + "?currentPage="
								+ i + params + "'>" + i + "</a> ";
					else
						s += "<a href='" + url + "?currentPage=" + i + params
								+ "'>" + i + "</a> ";
				s += "页　";
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s += "<a href='" + url + "?currentPage="
							+ (currentPage + 1) + params + "'>下一页</a> ";
				}
				s += "<a href='" + url + "?currentPage=" + pages + params
						+ "'>末页</a> ";
			}
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/**
	 * 使用表单分页，前台需要给数据放到form里，适合多参数查询分页
	 * 
	 * @param currentPage
	 * @param pages
	 * @return
	 */
	public static String showPageFormHtml(int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s += "<a class='btn_first' href='javascript:void(0);' title='首页' onclick='return gotoPage(1)'>首页</a> ";
				if (currentPage > 1)
					s += "<a class='btn_prev' href='javascript:void(0);' title='上一页' onclick='return gotoPage("
							+ (currentPage - 1) + ")'>上一页</a> ";
			}

			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
			
				for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
					if (i == currentPage)
						s += "<a class='this' href='javascript:void(0);' title='第" + i + "页' onclick='return gotoPage("
								+ i + ")'>" + i + "</a> ";
					else
						s += "<a href='javascript:void(0);' title='第" + i + "页' onclick='return gotoPage("
								+ i +

								")'>" + i + "</a> ";
				
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s += "<a class='btn_next' href='javascript:void(0);' title='下一页' onclick='return gotoPage("
							+ (currentPage + 1) + ")'>下一页</a> ";
				}
				s += "<a class='btn_last' href='javascript:void(0);' title='末页' onclick='return gotoPage("
						+ pages + ")'>末页</a> ";
			}
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/**
	 * ajax动态分页，使用json管理数据
	 * 
	 * @param url
	 * @param params
	 * @param currentPage
	 * @param pages
	 * @return
	 */
	public static String showPageAjaxHtml(String url, String params,
			int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			String address = url + "?1=1" + params;
			if (currentPage >= 1) {
				s += "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\",1,this)'>首页</a> ";
				s += "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address
						+ "\","
						+ (currentPage - 1)
						+ ",this)'>上一页</a> ";
			}

			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
			
				for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
					if (i == currentPage)
						s += "<a class='this' href='javascript:void(0);' onclick='return ajaxPage(\""
								+ address
								+ "\","
								+ i
								+ ",this)'>"
								+ i
								+ "</a> ";
					else
						s += "<a href='javascript:void(0);' onclick='return ajaxPage(\""
								+ address + "\"," + i +

								",this)'>" + i + "</a> ";
				
			}
			if (currentPage <= pages) {
				s += "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address
						+ "\","
						+ (currentPage + 1)
						+ ",this)'>下一页</a> ";
				s += "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\"," + pages + ",this)'>末页</a> ";
			}
			// s+=" 转到<input type=text size=2>页";
		}
		return s;
	}

	/**
	 * 将分页信息封装到ModelAndView中
	 * 
	 * @param url
	 *            分页url
	 * @param staticURL
	 *            静态分页URL，使用urlrewrite实现伪静态
	 * @param params
	 *            非静态URL的参数
	 * @param pList
	 *            分页数据查询结果c
	 * @param mv
	 *            输出的视图s
	 */
	public static void saveIPageList2ModelAndView(String url, String staticURL,
			String params, IPageList pList, ModelAndView mv) {
		if (pList != null) {
			mv.addObject("objs", pList.getResult());
			mv.addObject("totalPage", new Integer(pList.getPages()));//总页数
			mv.addObject("pageSize", pList.getPageSize());// 返回每页大小
			mv.addObject("rows", new Integer(pList.getRowCount()));// 返回查询总记录数
			mv.addObject("currentPage", new Integer(pList.getCurrentPage()));//返回有效的当前页
			
			mv.addObject("gotoPageHTML", CommUtil.showPageHtml(url, params,
					pList.getCurrentPage(), pList.getPages()));
			mv.addObject(
					"gotoPageFormHTML",
					CommUtil.showPageFormHtml(pList.getCurrentPage(),
						pList.getPages()));
			mv.addObject(
					"gotoPageStaticHTML",
					CommUtil.showPageStaticHtml(staticURL,
							pList.getCurrentPage(), pList.getPages()));
			mv.addObject(
					"gotoPageAjaxHTML",
					CommUtil.showPageAjaxHtml(url, params,
							pList.getCurrentPage(), pList.getPages()));
			
		}
	}
	
	
	public static List<Map> saveIPageList2ModelAndView2(IPageList pList){
		List<Address> address = pList.getResult();
		
		List<Map> addressList = new ArrayList<Map>();
		
		for (Address addresslist : address) {
			Map addressMap = new HashMap();
			Map  map1= new HashMap();
			addressMap.put("id", addresslist.getId());
			addressMap.put("trueName", addresslist.getTrueName());
			addressMap.put("area_info", addresslist.getArea_info());
			addressMap.put("zip", addresslist.getZip());
			addressMap.put("telephone", addresslist.getTelephone());
			addressMap.put("mobile", addresslist.getMobile());
			addressMap.put("areaName", addresslist.getArea()
					.getAreaName());
			addressMap.put("parentName",addresslist.getArea()
					.getParent()
					.getAreaName());
			addressMap.put("areaUserName",addresslist.getUser()
					.getTrueName());
			if(addresslist.getArea().getParent() !=null){
				addressMap.put("parentsName",addresslist.getArea()
						.getParent()
						.getParent()
						.getAreaName());
			}else{
				addressMap.put("ParentName","");
			}
		
			addressList.add(addressMap);
		}
		
		return addressList;
		
	}
	
	
	/**
	 * 遍历对象
	 
	public static String bianli(Object model){
		Field[] fields = model.getClass().getDeclaredFields();
		
		for(int i=0;i<fields.length;i++){
			Field f = fields[i];
			 f.setAccessible(true);
			f.getName();
			
			
		}
		String jsonStr = null;
		try {
		        JSONArray jsonObject = JSONArray.fromObject(fields);
		        jsonStr = jsonObject.toString();
		        System.out.println(jsonStr);
		    } catch (Exception e) {
		        System.out.println("数组转json失败");
		    }
		return jsonStr;
		
		
	}
	*/

	/**
	 * 将IPageList转换为ModelAndView的第二个方法，这里传递到前台的不一定是objs，可以自定义，用来同一个页面中多个分页查询
	 * 
	 * @param url
	 * @param staticURL
	 * @param params
	 * @param result_name
	 * @param pList
	 * @param mv
	 */
	public static void saveIPageList2ModelAndView2(String url,
			String staticURL, String params, String prefix, IPageList pList,
			ModelAndView mv) {
		if (pList != null) {
			mv.addObject(prefix + "_objs", pList.getResult());
			mv.addObject(prefix + "_totalPage", new Integer(pList.getPages()));
			mv.addObject(prefix + "_pageSize", pList.getPageSize());
			mv.addObject(prefix + "_rows", new Integer(pList.getRowCount()));
			mv.addObject(prefix + "_currentPage",
					new Integer(pList.getCurrentPage()));
			mv.addObject(prefix + "_gotoPageHTML", CommUtil.showPageHtml(url,
					params, pList.getCurrentPage(), pList.getPages()));
			mv.addObject(
					prefix + "_gotoPageFormHTML",
					CommUtil.showPageFormHtml(pList.getCurrentPage(),
							pList.getPages()));
			mv.addObject(
					prefix + "_gotoPageStaticHTML",
					CommUtil.showPageStaticHtml(staticURL,
							pList.getCurrentPage(), pList.getPages()));
			mv.addObject(
					prefix + "_gotoPageAjaxHTML",
					CommUtil.showPageAjaxHtml(url, params,
							pList.getCurrentPage(), pList.getPages()));
		}
	}

	/**
	 * 将lucene对象转换为分页对象
	 * 
	 * @param pList
	 * @param mv
	 */
	public static void saveLucene2ModelAndView(LuceneResult pList,
			ModelAndView mv) {
		if (pList != null) {
			mv.addObject("objs", pList.getVo_list());
			mv.addObject("totalPage", pList.getPages());
			mv.addObject("pageSize", pList.getPageSize());
			mv.addObject("rows", pList.getRows());
			mv.addObject("currentPage", new Integer(pList.getCurrentPage()));
			mv.addObject(
					"gotoPageFormHTML",
					CommUtil.showPageFormHtml(pList.getCurrentPage(),
							pList.getPages()));
		}
	}

	public static char randomChar() {
		char[] chars = new char[] { 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D',
				'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J',
				'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P',
				'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V',
				'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z' };
		int index = (int) (Math.random() * 52) - 1;
		if (index < 0) {
			index = 0;
		}
		return chars[index];
	}

	public static String[] splitByChar(String s, String c) {
		String[] list = s.split(c);
		return list;
	}

	public static Object requestByParam(HttpServletRequest request, String param) {
		if (!request.getParameter(param).equals("")) {
			return request.getParameter(param);
		} else
			return null;

	}

	public static String substring(String s, int maxLength) {
		if (!StringUtils.hasLength(s))
			return s;
		if (s.length() <= maxLength) {
			return s;
		} else
			return s.substring(0, maxLength) + "...";
	}

	public static String substringfrom(String s, String from) {
		if (s.indexOf(from) < 0)
			return "";
		return s.substring(s.indexOf(from) + from.length());
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
			}
		return v;
	}
	
	public static int null2Int_1(Object s) {
		int v = -1;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
			}
		return v;
	}

	public static float null2Float(Object s) {
		float v = 0.0f;
		if (s != null)
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception e) {
			}
		return v;
	}

	public static double null2Double(Object s) {
		double v = 0.0;
		if (s != null)
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception e) {
			}
		return v;
	}

	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null)
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception e) {
			}
		return v;
	}
	/**
	 *[ 如果传入参数为null等于空，否则移除两侧空白]
	 * @param s
	 * @return
	 */
	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	public static Long null2Long(Object s) {
		Long v = -1l;
		if (s != null)
			try {
				v = Long.parseLong(s.toString());
			} catch (Exception e) {
			}
		return v;
	}

	public static String getTimeInfo(long time) {
		int hour = (int) time / (1000 * 60 * 60);
		long balance = time - hour * 1000 * 60 * 60;
		int minute = (int) balance / (1000 * 60);
		balance = balance - minute * 1000 * 60;
		int seconds = (int) balance / 1000;
		String ret = "";
		if (hour > 0)
			ret += hour + "小时";
		if (minute > 0)
			ret += minute + "分";
		else if (minute <= 0 && seconds > 0)
			ret += "零";
		if (seconds > 0)
			ret += seconds + "秒";
		return ret;
	}
	
	/**
	 * 获取本机IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			java.net.InetAddress addr = null;
			try {
				addr = java.net.InetAddress.getLocalHost();
			} catch (java.net.UnknownHostException e) {
				e.printStackTrace();
			}
			ip = CommUtil.null2String(addr.getHostAddress());// 获得本机IP
		}
		return ip;
	}

	public static int indexOf(String s, String sub) {
		return s.trim().indexOf(sub.trim());
	}

	public static Map cal_time_space(Date begin, Date end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = end.getTime() - begin.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long second = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		Map map = new HashMap();
		map.put("day", day);
		map.put("hour", hour);
		map.put("min", min);
		map.put("second", second);
		return map;
	}

	public static final String randomString(int length) {
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
				+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		if (length < 1) {
			return "";
		}
		Random randGen = new Random();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = ("0123456789").toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	/**
	 * 计算两个时间间隔
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getDateDistance(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * 浮点数除法运算，保证数据的精确度
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double div(Object a, Object b) {
		double ret = 0.0;
		if (!null2String(a).equals("") && !null2String(b).equals("")) {
			BigDecimal e = new BigDecimal(null2String(a));
			BigDecimal f = new BigDecimal(null2String(b));
			if (null2Double(f) > 0)
				ret = e.divide(f, 3, BigDecimal.ROUND_DOWN).doubleValue();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret));
	}

	/**
	 * 浮点数据减法运算，保证数据的精确度
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double subtract(Object a, Object b) {
		double ret = 0.0;
		BigDecimal e = new BigDecimal(CommUtil.null2Double(a));
		BigDecimal f = new BigDecimal(CommUtil.null2Double(b));
		ret = e.subtract(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret));
	}

	/**
	 * 浮点数据加法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double add(Object a, Object b) {
		double ret = 0.0;
		BigDecimal e = new BigDecimal(CommUtil.null2Double(a));
		BigDecimal f = new BigDecimal(CommUtil.null2Double(b));
		ret = e.add(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret));
	}

	/**
	 * 浮点数乘法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double mul(Object a, Object b) {// 乘法
		BigDecimal e = new BigDecimal(CommUtil.null2Double(a));
		BigDecimal f = new BigDecimal(CommUtil.null2Double(b));
		double ret = e.multiply(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret));
	}

	public static String formatMoney(Object money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(money);
	}

	public static int M2byte(float m) {
		float a = m * 1024 * 1024;
		return (int) a;
	}

	public static boolean convertIntToBoolean(int intValue) {
		return (intValue != 0);
	}

	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String url = "http://" + request.getServerName();
		if (null2Int(request.getServerPort()) != 80) {
			url = url + ":" + null2Int(request.getServerPort()) + contextPath;
		} else {
			url = url + contextPath;
		}
		return url;
	}

	/**
	 * 定义过滤信息 使用Jsoup过滤数据，保护网站安全
	 */
	private final static Whitelist user_content_filter = Whitelist.relaxed();
	static {
		user_content_filter.addTags("embed", "object", "param", "span", "div",
				"font");
		user_content_filter.addAttributes("div", "width", "height", "style");
		user_content_filter.addAttributes("span", "width", "height", "style");
		user_content_filter.addAttributes("ul", "width", "height", "style");
		user_content_filter.addAttributes("li", "width", "height", "style");
		user_content_filter.addAttributes("table", "width", "height", "style");
		user_content_filter.addAttributes("tr", "width", "height", "style");
		user_content_filter.addAttributes("td", "width", "height", "style");
		user_content_filter.addAttributes(":all", "style", "class", "id",
				"name");
		user_content_filter.addAttributes("object", "classid", "codebase");
		user_content_filter.addAttributes("param", "name", "value");
		user_content_filter.addAttributes("embed", "src", "quality", "width",
				"height", "allowFullScreen", "allowScriptAccess", "flashvars",
				"name", "type", "pluginspage");
	}

	public static String filterHTML(String content) {
		String s = Jsoup.clean(content, user_content_filter);
		return s;
	}

	public static int parseDate(String type, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("y")) {
			return cal.get(Calendar.YEAR);
		}
		if (type.equals("M")) {
			return cal.get(Calendar.MONTH) + 1;
		}
		if (type.equals("d")) {
			return cal.get(Calendar.DAY_OF_MONTH);
		}
		if (type.equals("H")) {
			return cal.get(Calendar.HOUR_OF_DAY);
		}
		if (type.equals("m")) {
			return cal.get(Calendar.MINUTE);
		}
		if (type.equals("s")) {
			return cal.get(Calendar.SECOND);
		}
		return 0;
	}

	// 读取远程url图片,得到宽高
	public static int[] readImgWH(String imgurl) {
		boolean b = false;
		try {
			// 实例化url
			URL url = new URL(imgurl);
			// 载入图片到输入流
			java.io.BufferedInputStream bis = new BufferedInputStream(
					url.openStream());
			// 实例化存储字节数组
			byte[] bytes = new byte[100];
			// 设置写入路径以及图片名称
			OutputStream bos = new FileOutputStream(new File(
					"C:\\thetempimg.gif"));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.flush();
			bos.close();
			// 关闭输出流
			b = true;
		} catch (Exception e) {
			// 如果图片未找到
			b = false;
		}
		int[] a = new int[2];
		if (b) {// 图片存在
			// 得到文件
			java.io.File file = new java.io.File("C:\\thetempimg.gif");
			BufferedImage bi = null;
			boolean imgwrong = false;
			try {
				// 读取图片
				bi = javax.imageio.ImageIO.read(file);
				try {
					// 判断文件图片是否能正常显示,有些图片编码不正确
					int i = bi.getType();
					imgwrong = true;
				} catch (Exception e) {
					imgwrong = false;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (imgwrong) {
				a[0] = bi.getWidth(); // 获得 宽度
				a[1] = bi.getHeight(); // 获得 高度
			} else {
				a = null;
			}
			// 删除文件
			file.delete();
		} else {// 图片不存在
			a = null;
		}
		return a;
	}

	/**
	 * 物理删除附件方法
	 * 
	 * @param request
	 * @param acc
	 * @return
	 */
	public static boolean del_acc(HttpServletRequest request, Accessory acc) {
		boolean ret = true;
		boolean ret1 = true;
		if (acc != null) {
			String path = request.getSession().getServletContext()
					.getRealPath("/")
					+ acc.getPath() + File.separator + acc.getName();
			String small_path = request.getSession().getServletContext()
					.getRealPath("/")
					+ acc.getPath()
					+ File.separator
					+ acc.getName()
					+ "_small." + acc.getExt();
			ret = deleteFile(path);
			ret1 = deleteFile(small_path);
		}
		return ret && ret1;
	}

	/**
	 * 前台判定是否存在文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 计算分割后的数组长度
	 * 
	 * @param s
	 * @param c
	 * @return
	 */
	public static int splitLength(String s, String c) {
		int v = 0;
		if (!s.trim().equals("")) {
			v = s.split(c).length;
		}
		return v;
	}

	/**
	 * 计算file文件大小，可以是单个文件也可以是文件夹
	 * 
	 * @param file
	 * @return
	 */
	static int totalFolder = 0;
	static int totalFile = 0;

	public static double fileSize(File folder) {
		if (folder.exists()) {
			totalFolder++;
			// System.out.println("Folder: " + folder.getName());
			long foldersize = 0;
			File[] filelist = folder.listFiles();
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isDirectory()) {
					foldersize += fileSize(filelist[i]);
				} else {
					totalFile++;
					foldersize += filelist[i].length();
				}
			}
			return div(foldersize, 1024);
		} else
			return 0;
	}

	/**
	 * 计算文件夹下文件数量
	 * 
	 * @param file
	 * @return
	 */
	public static int fileCount(File file) {
		if (file == null) {
			return 0;
		}
		if (!file.isDirectory()) {
			return 1;
		}
		int fileCount = 0;
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				fileCount++;
			} else if (f.isDirectory()) {
				fileCount++;
				fileCount += fileCount(file); // 如果遇到目录则通过递归调用继续统计
			}
		}
		return fileCount;
	}

	/**
	 * 获取当前请求完整的URL
	 * 
	 * @param request
	 */
	public static String get_all_url(HttpServletRequest request) {
		String query_url = request.getRequestURI();
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			query_url = query_url + "?" + request.getQueryString();
		}
		return query_url;
	}

	/**
	 * 根据html颜色代码返回java Color
	 * 
	 * @param color
	 * @return
	 */
	public static Color getColor(String color) {
		if (color.charAt(0) == '#') {
			color = color.substring(1);
		}
		if (color.length() != 6) {
			return null;
		}
		try {
			int r = Integer.parseInt(color.substring(0, 2), 16);
			int g = Integer.parseInt(color.substring(2, 4), 16);
			int b = Integer.parseInt(color.substring(4), 16);
			return new Color(r, g, b);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * 根据种子a随机出一组长度为length不重复的整型数组
	 * 
	 * @param a
	 * @param length
	 * @return
	 */
	public static Set<Integer> randomInt(int a, int length) {
		Set<Integer> list = new TreeSet<Integer>();
		int size = length;
		if (length > a) {
			size = a;
		}
		while (list.size() < size) {
			Random random = new Random();
			int b = random.nextInt(a);
			list.add(b);
		}
		return list;
	}

	/**
	 * 格式化数字，保留对应的小数位
	 * 
	 * @param obj
	 * @param len
	 * @return
	 */
	public static Double formatDouble(Object obj, int len) {
		Double ret = 0.0;
		String format = "0.0";
		for (int i = 1; i < len; i++) {
			format = format + "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return Double.valueOf(df.format(obj));
	}

	/**
	 * 判断字符是否为中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为乱码
	 * 
	 * @param strName
	 * @return
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {

				if (!isChinese(c)) {
					count = count + 1;
					System.out.print(c);
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 去掉IP字符串前后所有的空格
	 * 
	 * @param IP
	 * @return
	 */
	public static String trimSpaces(String IP) {//
		while (IP.startsWith(" ")) {
			IP = IP.substring(1, IP.length()).trim();
		}
		while (IP.endsWith(" ")) {
			IP = IP.substring(0, IP.length() - 1).trim();
		}
		return IP;
	}

	/**
	 * 判断是否是一个IP
	 * 
	 * @param IP
	 * @return
	 */
	public static boolean isIp(String IP) {//
		boolean b = false;
		IP = trimSpaces(IP);
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = IP.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							b = true;
		}
		return b;
	}

	/**
	 * 计算当前域名，不含www的顶级域名
	 * 
	 * @param request
	 *            输入请求
	 * @return 顶级域名信息
	 */
	public static String generic_domain(HttpServletRequest request) {
		// System.out.println(request.getServerName());
		String system_domain = "localhost";
		String serverName = request.getServerName();
		if (isIp(serverName)) {
			system_domain = serverName;
		} else {
			if (serverName.indexOf(".") == serverName.lastIndexOf(".")) {
				system_domain = serverName;
			} else {
				system_domain = serverName
						.substring(serverName.indexOf(".") + 1);
			}
		}
		// System.out.println(system_domain);
		return system_domain;
	}

	/**
	 * 判断是否是手机打开网页
	 */
	public boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile",
				"wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",
				"windows ce", "symbian", "series", "webos", "sony",
				"blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",
				"pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin",
				"huawei", "novarra", "coolpad", "webos", "techfaith",
				"palmsource", "alcatel", "amoi", "ktouch", "nexian",
				"ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",
				"smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop",
				"benq", "haier", "^lct", "320x320", "240x320", "176x220",
				"w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
				"bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
				"doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",
				"keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
				"midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
				"newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",
				"port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",
				"send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",
				"sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-",
				"upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",
				"wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase()
						.indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}



	/**
	 * 
	 * @param str
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String generic_star(String str, int begin, int end) {
		if (str.length() > begin && str.length() >= end) {
			return str.replaceAll(str.substring(begin, end), "********");
		} else {
			return str;
		}
	}

	/**
	 * 将json转为为List<Map>
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map> Json2List(String json) {
		List<Map> list = new ArrayList<Map>();
		if (json != null && !json.equals("")) {
			list = Json.fromJson(List.class, json);
		}
		return list;
	}

	/**
	 * 检测字符串是否包含数字、字母、大写字母、特殊符号
	 * 
	 * @param str
	 * @return 
	 *         返回1表示含有数字，返回2表示含有数字+小写字母，返回3表示含有数字+小写字母+大写字母，返回4表示含有数字+小写字母+大写字母+特殊符号
	 */
	public static int checkInput(String str) {
		int num = 0;
		num = Pattern.compile("\\d").matcher(str).find() ? num + 1 : num;
		num = Pattern.compile("[a-z]").matcher(str).find() ? num + 1 : num;
		num = Pattern.compile("[A-Z]").matcher(str).find() ? num + 1 : num;
		num = Pattern.compile("[-.!@#$%^&*()+?><]").matcher(str).find() ? num + 1
				: num;
		return num;
	}
	
	
	/**
	 * 用于读取测试模拟数据
	 * @param fileName
	 * @return
	 */
	public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
                //line++;
            	sb.append(tempString);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

	public static void saveIPageList2ModelAndView(String url, String staticURL,
			String params, int currentPage,int pageCount, int pageSize,ModelAndView mv) {
		
			
			mv.addObject("totalPage", new Integer(pageCount));
			mv.addObject("pageSize", pageSize);
			mv.addObject("currentPage", new Integer(currentPage));
			mv.addObject("gotoPageHTML", CommUtil.showPageHtml(url, params,
					currentPage, pageCount));
			mv.addObject(
					"gotoPageFormHTML",
					CommUtil.showPageFormHtml(currentPage,
							pageCount));
			mv.addObject(
					"gotoPageStaticHTML",
					CommUtil.showPageStaticHtml(staticURL,
							currentPage, pageCount));
			mv.addObject(
					"gotoPageAjaxHTML",
					CommUtil.showPageAjaxHtml(url, params,
							currentPage, pageCount));
		
	}
	
	
	

	
	public static String fourHomesfive (String arg) {
			
			//BigDecimal bd = new BigDecimal(Double.parseDouble(arg));
			//DecimalFormat df = new DecimalFormat("#.00"); 
			NumberFormat nf = NumberFormat.getNumberInstance(); 
			nf.setMaximumFractionDigits(2); 
			return  nf.format(Double.parseDouble(arg)); 
	}
	
	
	public static Area getAreaById(String id){
		Area _area = new Area();
		IAreaService areaService = (IAreaService)SpringUtil.getObject(IAreaService.class);
		_area = areaService.getObjById(null2Long(id));
		return _area;
	
	}
	
	public static Area getAreaByName(String name){
		IAreaService areaService = (IAreaService)SpringUtil.getObject(IAreaService.class);
		
		List<Area> areas = areaService
				.query("select obj from Area obj where obj.areaName = '"+name+"' and obj.parent.id is null order by obj.sequence asc",
						null, -1, -1);
		if(areas.size()>0) return areas.get(0);
		return null;
	}
	
	
	public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }  
    //获得指定数量的UUID  
    public static String[] getUUID(int number) {  
        if (number < 1) {  
            return null;  
        }  
        String[] ss = new String[number];  
        for (int i = 0; i < number; i++) {  
            ss[i] = getUUID();  
        }  
        return ss;  
    }  
    
    
    public static Long null2LongNew(Object s) {
		Long v = 0l;
		if (s != null)
			try {
				v = Long.parseLong(s.toString());
			} catch (Exception e) {
			}
		return v;
	}
    
    

    /**
	 * 将lucene对象转换为分页对象
	 * 
	 * @param pList
	 * @param mv
	 */
	public static void saveLucene2ModelAndView(LuceneResult pList,
			ModelAndView mv, GoodsViewTools vt, IGoodsService is) {
		if (pList != null) {
			
			List<LuceneVo> newList = new ArrayList<LuceneVo>();
			List<LuceneVo> list = pList.getVo_list();
			for (LuceneVo vo : list) {
				String ggStatus = vt.query_activity_status(String.valueOf(vo.getVo_id()), "group_buy");
				if ("true".equals(ggStatus)) {
					
					Goods goods = is.getObjById(vo.getVo_id());
					List<GroupGoods> ggList = goods.getGroup_goods_list();
					GroupGoods gg = null;
					if (null != ggList && 0 < ggList.size()) {
						
						for (GroupGoods ggs : ggList) {
							
							if (ggs.getGg_status() == 1) {
								
								gg = ggs;
								
							}
						}
					}
					if (null != gg) {
						
						vo.setVo_store_price(gg.getGg_price().doubleValue());
					}
				}
				
				newList.add(vo);
			}
			
			mv.addObject("objs", newList);
			mv.addObject("totalPage", pList.getPages());
			mv.addObject("pageSize", pList.getPageSize());
			mv.addObject("rows", pList.getRows());
			mv.addObject("currentPage", new Integer(pList.getCurrentPage()));
			mv.addObject(
					"gotoPageFormHTML",
					CommUtil.showPageFormHtml(pList.getCurrentPage(),
							pList.getPages()));
		}
	}
	
    public static void main(String[] args) throws Exception {
    	
    	System.out.println(getUUID(2)[1]);
    }
}
