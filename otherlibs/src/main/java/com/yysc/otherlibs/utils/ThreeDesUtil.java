package com.yysc.otherlibs.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 文件名：ThreeDesUtil.java
 * 描述：字符串 DESede(3DES) 加密
 * 作者：王承
 * 日期：2016年4月6日下午4:19:09
 */
public class ThreeDesUtil {

	/**
	 * 定义 加密算法,可用
	 */
	private static final String Algorithm = "DESede"; //

	/**
	 * 加密处理模式
	 * @param keybyte 为加密密钥，长度为24字节
	 * @param src 为被加密的数据缓冲区（源）
	 * @return
	 * 作者：王承
	 * 日期：2016年4月6日下午4:19:35
	 */
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			/* 生成密钥 */
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			/* 加密 */
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密处理模式
	 * @param keybyte 为加密密钥，长度为24字节
	 * @param src 为被加密的数据缓冲区（源）
	 * @return
	 * 作者：王承
	 * 日期：2016年4月6日下午4:21:09
	 */
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			/* 生成密钥 */
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			/* 解密 */
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
}
