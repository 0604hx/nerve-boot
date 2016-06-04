package com.nerve.commons.pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数器
 * org.nerve.webmagic.pool
 * Created by zengxm on 2016/4/21 0021.
 */
public class CounterPool {
	private static class Counter{
		//尝试采集的数目
		static AtomicLong crawl=new AtomicLong(0);
		//成功采集的数目
		static AtomicLong pick=new AtomicLong(0);
		//执行的任务总数
		static AtomicLong task=new AtomicLong(0);
		//成功回传到接收器的数据总数
		static AtomicLong back=new AtomicLong(0);
	}

	public static void addCrawl(){
		Counter.crawl.incrementAndGet();
	}

	public static void addCrawl(long l){
		Counter.crawl.addAndGet(l);
	}

	public static void addPick(){
		Counter.pick.incrementAndGet();
	}
	public static void addPick(long l){
		Counter.pick.addAndGet(l);
	}

	public static void addTask(){
		Counter.task.incrementAndGet();
	}

	public static void addBack(){
		Counter.back.incrementAndGet();
	}
	public static void addBack(long l){
		Counter.back.addAndGet(l);
	}

	public static String getStatus(){
		return "Status of "+Counter.class.getName()+":"+getStatusMap().toString();
	}

	public static Map<String,Object> getStatusMap(){
		final Map<String,Object> map=new ConcurrentHashMap<>();
		map.put("totalTasks", Counter.task.longValue());
		map.put("tasks", WorkerThreadPool.getInstance().getActiveSize());
		map.put("crawl", Counter.crawl.longValue());
		map.put("pick", Counter.pick.longValue());
		map.put("back", Counter.back.longValue());
		return map;
	}

	/**
	 * 打印当前状态信息
	 */
	public static void showStatus(){
		System.out.println(getStatus());
	}
}
