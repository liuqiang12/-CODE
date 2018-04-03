package com.JH.dgather.frame.xmlparse.service.impl;

public class TestThread implements Runnable {

	@Override
	public void run() {
		
		test();
	}
	static int num = 0;
	
	public static void test()
	{
		num = 1;
		System.out.println(num);
		num = 2;
		System.out.println(num);
	}

	public static void main(String[] args) {
		Thread a = new Thread(new TestThread());
		Thread b = new Thread(new TestThread());
		a.start();
		b.start();
	}
}
