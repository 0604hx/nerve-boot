package com.nerve.commons.tools;

/**
 * org.nerve.selenium.util
 * Created by zengxm on 2016/4/19 0019.
 */
public class Timing {
	private double start;
	private double used=-1;

	public Timing(){
		start=System.nanoTime();
	}

	/**
	 * 默认返回的毫秒
	 * @return
	 */
	public double used(){
		if(used==-1)
			used= (System.nanoTime()-start)/1000000;
		return used;
	}

	public double getStart(){
		return start;
	}

	/**
	 *
	 * @return
	 */
	public long toMillSecond(){
		return (long)used()/1000;
	}
}
