package com.yysc.otherlibs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名加密SHA256 文件名：SHA256Util.java 描述： 作者：王承 日期：2016年4月6日下午4:16:48
 */
public class SHA256Util {

	/**
	 * 转换成十六进制字符串
	 * 
	 * @param bts
	 * @return 作者：王承 日期：2016年4月6日下午4:18:37
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 对字符串加密,加密算法使用SHA-256
	 * 
	 * @param strSrc
	 *            要加密的字符串
	 * @return 作者：蔡福猛 日期：2016年5月10日下午6:47:49
	 */
	public static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 对字符串加密,加密算法使用MD5
	 * 
	 * @param strSrc
	 *            要加密的字符串
	 * @return 作者：蔡福猛 日期：2016年5月10日下午6:47:49
	 */
	public static String EncryptMD5(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

}
