package com.koala.foundation.test;

import java.io.IOException;

import com.koala.core.tools.CommUtil;

public class ImageIOTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String source = "D://test.jpg";
		String target = "D://test1.jpg";
		CommUtil.createSmall(source, target, 160, 160);

	}
}
