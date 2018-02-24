package com.yysc.otherlibs.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 文件名：Base64Utils.java 描述：base64编码解密工具包 作者：王承 日期：2016年4月6日下午3:21:11
 */
public class Base64Util {

	/**
	 * 将 s 进行 BASE64 编码
	 * 
	 * @param s
	 * @return 作者：王承 日期：2016年4月6日下午3:21:37
	 */
	public static String encode(byte[] s) {
		if (s == null)
			return null;
		return (new BASE64Encoder()).encode(s);
	}

	/**
	 * 将 s 进行 BASE64 编码
	 * 
	 * @param s
	 * @return 作者：王承 日期：2016年4月6日下午3:22:01
	 */
	public static String encode(String s) {

		if (s == null)
			return null;
		return encode(s.getBytes());
	}

	/**
	 * 将 BASE64 编码的字符串 s 进行解码
	 * 
	 * @param s
	 * @return 作者：王承 日期：2016年4月6日下午3:22:08
	 */
	public static byte[] decode(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return b;
		} catch (Exception e) {
			return null;
		}
	}

}