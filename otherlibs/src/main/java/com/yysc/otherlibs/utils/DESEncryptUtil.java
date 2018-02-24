package com.yysc.otherlibs.utils;

import com.google.gson.Gson;
import com.yysc.otherlibs.Commom;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


/**
 * 
 * 文件名：DESEncrypt.java 描述：加密解密工具类 作者：王承 日期：2016年4月6日上午10:27:03
 */
public class DESEncryptUtil {

	/***
	 * 加密密钥
	 */
	private static String privateKey = "6319dd2d032d503a92491398";

	/**
	 * 加密方法提供3des加密，并且base64编码
	 * 
	 * @param key
	 *            24字节的密钥
	 * @param str
	 *            明文
	 * @return 加密后的数据 作者：王承 日期：2016年4月6日上午10:27:28
	 */
	private static String DataEncrypt(byte[] key, String str) {
		String encrypt = null;
		byte[] ret;
		try {
			ret = ThreeDesUtil.encryptMode(key, str.getBytes("UTF-8"));
			encrypt = new String(Base64Util.encode(ret));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			encrypt = str;
		}
		return encrypt;

	}

	/**
	 * 解密方法，先解密base64,在按3des解密
	 * 
	 * @param str
	 *            密文
	 * @return 作者：王承 日期：2016年4月6日下午3:24:32
	 */
	public static String DataDecrypt(byte[] key, String str) {
		String decrypt = null;
		try {
			byte[] ret = ThreeDesUtil.decryptMode(key, Base64Util.decode(str));
			decrypt = new String(ret, "UTF-8");
		} catch (Exception e) {
			System.out.print(e);
			decrypt = str;
		}
		return decrypt;
	}

	/**
	 * 加密方法
	 * 
	 * @param param
	 *            需加密数据
	 * @return 密文 作者：王承 日期：2016年4月6日下午3:25:11
	 */
	// @SuppressWarnings("unchecked")
	// public static String encodeUrl(String param) {
	// String encoded = null;
	// Map<String, Object> map2 = new Gson().fromJson(param, HashMap.class);
	// String[] arr = null;
	// if (null != map2) {
	// arr = map2.get("encryptParam").toString().split(",");
	// }
	// if (arr.length > 0) {
	// for (String str : arr) {
	// encoded = DataEncrypt(privateKey.getBytes(), map2.get(str)
	// .toString());
	// try {
	// encoded = URLEncoder.encode(encoded, "utf-8");
	// } catch (UnsupportedEncodingException e) {
	//
	// }
	// map2.put(str, encoded);
	// }
	// }
	//
	// return new Gson().toJson(map2);
	// }

	/**
	 * 使用默认密钥解密方法，url解码，base64解码，3des解码
	 * 
	 * @param param
	 * @return 作者：王承 日期：2016年4月6日下午3:26:40
	 */
	// @SuppressWarnings("unchecked")
	// public static String decodeUrl(String param) {
	// Map<String, Object> map2 = new Gson().fromJson(param, HashMap.class);
	// String[] arr = null;
	// if (null != map2) {
	// arr = map2.get("encryptParam").toString().split(",");
	// }
	// if (arr.length > 0) {
	// for (String str : arr) {
	// String encoded = "";
	// try {
	// encoded = URLDecoder.decode(map2.get(str).toString(),
	// "utf-8");
	// } catch (UnsupportedEncodingException e) {
	//
	// }
	// encoded = DataDecrypt(privateKey.getBytes(), encoded);
	// map2.put(str, encoded);
	// }
	// }
	//
	// return new Gson().toJson(map2);
	// }

	/**
	 * 加密
	 * 
	 * @param src
	 *            加密内容
	 * @return
	 * @throws Exception
	 *             作者：蔡福猛 日期：2016年5月10日上午11:39:54
	 */
	public static String encryptThreeDESECB(String src) {

		try {

			DESedeKeySpec dks = new DESedeKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			byte[] b = cipher.doFinal(src.getBytes());

			Base64Util encoder = new Base64Util();
			return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 3des解密 ,key必须是长度大于等于 3*8 = 24 位
	 * 
	 * @param src
	 *            解密内容
	 * @return
	 * @throws Exception
	 *             作者：蔡福猛 日期：2016年5月10日上午11:40:19
	 */
	public static String decryptThreeDESECB(String src) {
		// --通过base64,将字符串转成byte数组
		Base64Util decoder = new Base64Util();
		byte[] bytesrc = decoder.decode(src);

		try {

			// --解密的key
			DESedeKeySpec dks = new DESedeKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// --Chipher对象解密
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 对数据进行签名
	 * 
	 * @param
	 *            
	 * @return 加密及签名后的数据 作者：王承 日期：2016年4月6日上午10:53:41
	 */
	@SuppressWarnings("unchecked")
	public static String doSign(Commom commom) {
		// Map<String, Object> map = new Gson().fromJson(param, HashMap.class);
		/** 令牌 */
		String token = "";
		/** 接口短地址 */
		String url = "";
		/** 时间戳 */
		String timestamp = "";
		/** 接口参数 */
		String data = "";

		if (null != commom.getToken() && !"".equals(commom.getToken())) {
			token = commom.getToken();
		}
		if (null != commom.getUrl() && !"".equals(commom.getUrl())) {
			url = commom.getUrl();
		}
		if (null != commom.getTimestamp() && !"".equals(commom.getTimestamp())) {
			timestamp = commom.getTimestamp();
		}

		/** 数据签名顺序为：token+timestamp+url+privateKey */
		String bts = token + timestamp + url + privateKey;

		String sign = SHA256Util.Encrypt(bts);
		// map.put("sign", sign);
		//
		// Gson gson = new Gson();
		return sign;
	}

	/**
	 * 验证签名信息
	 * 
	 * @param param
	 *            签名信息
	 * @return data 作者：王承 日期：2016年4月6日下午4:15:25
	 */
	@SuppressWarnings("unchecked")
	public static Commom checkSign(String param) {
		Commom commom = new Gson().fromJson(param, Commom.class);
		if (null != commom) {
			String token = commom.getToken();
			String timestamp = commom.getTimestamp();
			String url = commom.getUrl();
			String sign = commom.getSign();
			// String mdata = map.get("data").toString();
			/** 数据签名顺序为：token+timestamp+url+privateKey */

			String bts = token + timestamp + url + privateKey;
			String mSign = SHA256Util.Encrypt(bts);

			if (mSign.equals(sign)) {
				/** 签名相等 */
				return commom;
			}
		}
		return null;
	}
}