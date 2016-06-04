package com.nerve.commons.pool;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * org.nerve.webmagic.utils
 * Created by zengxm on 2016/4/20 0020.
 */
public final class ScheduledThreadPool {

	/**
	 * 通过静态内部类的方式，创建单例对象
	 * 提供并发情况下的性能
	 */
	private static class ScheduledThreadPoolInstance{
		static ScheduledThreadPool instance=new ScheduledThreadPool(5);
	}

	private ScheduledExecutorService executorService;

	/**
	 * 获取单例
	 * @return
	 */
	public static ScheduledThreadPool getInstance(){
		return ScheduledThreadPoolInstance.instance;
	}

	private ScheduledThreadPool(int poolSize){
		executorService= Executors.newScheduledThreadPool(poolSize, new ThreadFactory() {

			private final AtomicInteger atomicInteger=new AtomicInteger(1);
			@Override
			public Thread newThread(Runnable r) {
				Thread thread=new Thread(
						r,
						ScheduledThreadPool.class.getSimpleName()+" #"+atomicInteger.getAndIncrement());
				thread.setDaemon(true); //设置为守护进程
				thread.setUncaughtExceptionHandler(new WorkerUncaughtExceptionHandler());

				System.out.println(">>>>>> create new scheduled thread:"+thread.getName());
				return thread;
			}
		});
	}

	/**
	 * 在指定的
	 * @param task
	 * @param delay
	 * @param initialDelay
	 */
	public void put(TimerTask task, int delay, int initialDelay){
		if(delay<0)
			throw new IllegalArgumentException("argument delay must >=0");
		if(initialDelay<0)
			throw new IllegalArgumentException("argument initialDelay must >=0");

		executorService.scheduleWithFixedDelay(task, initialDelay, delay, TimeUnit.SECONDS);
	}

	/**
	 * 加入一个定时任务
	 * @param task
	 * @param delay 单位为：秒
	 */
	public void put(TimerTask task, int delay){
		if(delay<0)
			throw new IllegalArgumentException("argument delay must >=0");
		executorService.schedule(task, delay, TimeUnit.SECONDS);
	}
}
