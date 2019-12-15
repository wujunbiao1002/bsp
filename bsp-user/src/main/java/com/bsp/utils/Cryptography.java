package com.bsp.utils;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加密工具类
 * */
public class Cryptography {

	private static Logger logger = LoggerFactory.getLogger(Cryptography.class);

	/**
	 * 定义 加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String Algorithm = "DESede";

	/**
	 * ECB加密
	 * 
	 * @param original
	 *            源文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String tripleDESEncrypt(String original, String key) {
		try {
			byte[] keybyte = key.substring(0, 24).getBytes();
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return Base64.getEncoder().encodeToString(c1.doFinal(original.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("3DES加密出错", e);
		}
		return null;
	}

	/**
	 * ECB解密
	 *
	 * @param cryptograph
	 *            密文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String tripleDESDecrypt(String cryptograph, String key) {
		try {
			byte[] keybyte = key.substring(0, 24).getBytes();
			byte[] src = Base64.getDecoder().decode(cryptograph);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(src));
		} catch (Exception e) {
			logger.error("3DES解密出错", e);
		}
		return null;
	}


	/**
	 * MD5 盐值加密
	 */
	public static String MD5Hash(String target,String salt){
		//将当前输入密码加密，用于匹配数据库中的密码
		String res=new Md5Hash(target,salt).toString();
		return res;
	}

	/**
	 * 校验MD5结果
	 * @param  encrypt 加密后的字符串，target加密前，salt盐值
	 */
	public static boolean checkMd5Hash(String encrypt,String target,String salt){
		return MD5Hash(target,salt).equals(encrypt)?true:false;
	}
}
