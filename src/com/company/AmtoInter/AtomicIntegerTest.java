package com.company.AmtoInter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/28 10:43
 */
public class AtomicIntegerTest {

	private static final int THREADS_CONUT = 20;
	public static AtomicInteger count = new AtomicInteger(0);

	//private static int count = 0;
	//private static volatile int  count = 0;
	public static void increase() {
		//count++;
		count.incrementAndGet();
	}

	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_CONUT];
		for (int i = 0; i < THREADS_CONUT; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						increase();
					}
				}
			});
			threads[i].start();
		}

		while (Thread.activeCount() > 2) {
			Thread.yield();
		}
		System.out.println(count);
	}
}