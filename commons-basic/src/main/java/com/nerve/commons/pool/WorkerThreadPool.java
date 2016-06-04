package com.nerve.commons.pool;

import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * org.nerve.webmagic.pool
 * Created by zengxm on 2016/4/21 0021.
 */
public final class WorkerThreadPool {

	private static final class WorkerThreadPoolInstance{
		static WorkerThreadPool instance=new WorkerThreadPool(DEFAULT_SIZE);
	}

	private static final int DEFAULT_SIZE=5;
	private int poolSize=0;
	private ThreadPoolExecutor executorService;

	/**
	 * 获取WorkerThreadPool实例
	 * @return
	 */
	public static WorkerThreadPool getInstance(){
		return WorkerThreadPoolInstance.instance;
	}

	public final static ExecutorService getExecutorService(){
		return WorkerThreadPoolInstance.instance.executorService;
	}

	private WorkerThreadPool(int poolSize){
		if(poolSize<=0)
			poolSize=DEFAULT_SIZE;
		this.poolSize=poolSize;
		executorService= (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
			private final AtomicInteger atomicInteger=new AtomicInteger(1);
			@Override
			public Thread newThread(Runnable r) {
				Thread thread=new Thread(
						r,
						WorkerThreadPool.class.getSimpleName()+" #"+atomicInteger.getAndIncrement());
				thread.setDaemon(true); //设置为守护进程
				thread.setUncaughtExceptionHandler(new WorkerUncaughtExceptionHandler());

				System.out.println(">>>>>> create new worker thread:"+thread.getName()+":"+ thread.getUncaughtExceptionHandler());
				return thread;
			}
		});
	}

	/**
	 * 得到当前工作中的线程数目
	 * @return
	 */
	public int getActiveSize(){
		return executorService.getActiveCount();
	}
	public long getTaskCount(){
		return executorService.getTaskCount();
	}

	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return executorService.getActiveCount()<=0;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public Future<?> submit(Callable<?> callable){
		return executorService.submit(callable);
	}
	public Future<?> submit(TimerTask task){
		return executorService.submit(task);
	}

	public void execute(Runnable runnable){
		executorService.execute(runnable);
	}
}
