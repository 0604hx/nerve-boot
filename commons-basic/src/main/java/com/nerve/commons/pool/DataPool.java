package com.nerve.commons.pool;



import com.nerve.commons.bean.Data;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Created by zengxm on 2016/4/21 0021.
 */
public final class DataPool {
	private static BlockingDeque<Data> innerDeque=new LinkedBlockingDeque<>();
	private static AtomicInteger atomicCount=new AtomicInteger(0);

	public static void push(Data data){
		innerDeque.push(data);
		atomicCount.incrementAndGet();
	}

	public static Data take() throws InterruptedException {
		return innerDeque.take();
	}
	public static Data poll(){
		return innerDeque.poll();
	}

	public static long size(){
		return innerDeque.size();
	}

	public static void forEachRemaining(Consumer<Data> consumer){
		innerDeque.iterator().forEachRemaining(consumer);
	}
}
