package com.sly.demo100;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class ApplicationTests {
	
	public Thread thread;
	
	public static Lock lock = new ReentrantLock();

	@Test
	public void contextLoads() throws InterruptedException {
		System.out.println("开始");
		lock.lock();
		thread = Thread.currentThread();
		long id = thread.getId();
		System.out.println(id);
		thread.wait();
		System.out.println("完成");
		
	}
	
	@Test
	public void test02() {
		lock.lock();
		thread.notify();
		System.out.println("通知");
	}

}
