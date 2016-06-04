package com.nerve.commons.pool;

/**
 * com.zeus.dpos.collector.pool
 * Created by zengxm on 2016/4/29.
 */
public class WorkerUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println("----------------------"+t.getName()+" exception----------------------");
		e.printStackTrace();
		System.err.println("----------------------"+t.getName()+" exception----------------------");
	}
}
