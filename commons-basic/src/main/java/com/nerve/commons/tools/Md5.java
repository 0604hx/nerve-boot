package com.nerve.commons.tools;

import java.security.MessageDigest;

/**
 * com.zeus.commons.tools
 * Created by zengxm on 2016/5/19.
 */
public final class Md5 {
	private static char[] hexChar = {
			'0','1','2','3','4','5','6','7','8','9',
			'a','b','c','d','e','f'
	};

	/**
	 * 转换字节数组为十六进制字符串
	 * @param b
	 * @return
	 */
	public String toHexString(byte[] b){
		StringBuilder sb = new StringBuilder(b.length*2);
		for(int i=0;i<b.length;i++){
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encode(String data){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest;
			digest = messageDigest.digest(data.getBytes());
			return toHexString(digest);
		} catch (Exception e) {
			throw new IllegalStateException("md5 error:"+e.getMessage());
		}
	}
}
