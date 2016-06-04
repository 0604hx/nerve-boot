package com.nerve.commons;

import com.nerve.commons.tools.AuthorizationUtils;
import org.junit.Test;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * com.zeus.commons
 * Created by zengxm on 2016/5/19.
 */
public class MacTest {
	private final static char[] mChars = "0123456789ABCDEF".toCharArray();

	private static String getMac() {
		try {
			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
			while (el.hasMoreElements()) {
				byte[] mac = el.nextElement().getHardwareAddress();
				if (mac == null)
					continue;

				StringBuilder builder = new StringBuilder();
				for (byte b : mac) {
					builder.append(mChars[(b & 0xFF) >> 4]);
					builder.append("-");
				}
				builder.deleteCharAt(builder.length() - 1);
				return builder.toString();

			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Test
	public void mac(){
		System.out.println(getMac());

		System.out.println(AuthorizationUtils.getMachineCode());
	}
}
