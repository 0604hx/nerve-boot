package com.nerve.commons.tools;

import java.nio.file.Paths;

/**
 * com.zeus.dpos.commons.tools
 * Created by zengxm on 2016/4/26.
 */
public class PathTool {

	/**
	 * 获取资源路径
	 * @return
	 */
	public static String getContentPath(){
		try{
			return Paths.get(PathTool.class.getClassLoader().getResource("").toURI()).toString();
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
