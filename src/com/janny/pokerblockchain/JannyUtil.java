/**
 * @Description:    简单的区块链示例:扑克牌斗地主记账。
 * 
 * @author          Charles (yonglin_guo@hotmail.com)
 * @version         V1.0  
 * @Date            03/18/2020
 */
 
package com.janny.pokerblockchain;



import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.Base64;

import java.security.Key;
import java.security.MessageDigest;

//需要下载并确保 gson-2.8.6.jar 在类路径中
import com.google.gson.GsonBuilder;

/**
 * 工具类
 * 获得当前时间的字符串格式，对于字符串的数字签名、将一个对象装换为JSON格式数据、返回难度字符串目标
 * @author Charles
 *
 */
public class JannyUtil {
	
	//返回系统当前时间的字符串格式  	
	public static String getCurrentDateStr() { 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		return dateString;
	}	

	
	/*计算一个字符串的的Hash值 
	 * 	MessageDigest.getInstance("MD5"); MD5 产生一个128位(16字节)的散列值(hash value)
	 * 	MessageDigest.getInstance("SHA-1"); SHA-1 产生一个160(20字节)位字符串
	 * 	MessageDigest.getInstance("SHA-256"); SHA-256 产生一个256位(32字节)字符串
	 */	
	public static String applySha256(String input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
	        
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//返回JSON格式数据，供打印用。
	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
	
	//返回难度字符串目标，例如难度5将返回“00000”  
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}
	
	//返回难度字符串目标，例如难度5将返回“00000”  
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	

	public static void main(String[] args) {	

		System.out.println("Datetime....... "+getCurrentDateStr());
	}
}