package com.company;

import java.util.concurrent.DelayQueue;

/**
 * @Description: 网吧
 * @Author: liaocongcong
 * @Date: 2020/12/28 16:57
 */
public class InternetBar implements Runnable {

	//网民队列  使用延迟队列
	private DelayQueue<Netizen> dq = new DelayQueue<Netizen>();

	//上网
	public void startPlay(String id,String name,Integer money){
		//截止时间= 钱数*时间+当前时间(1块钱1秒)
		Netizen netizen = new Netizen(id,name,1000*money+System.currentTimeMillis());
		System.out.println("开始计费------");
		dq.add(netizen);
	}
	//下机时间
	public void endTime(Netizen netizen){
		System.out.println(netizen.getName()+"余额用完，下机");
	}

	@Override
	public void run() {
       //线程，监控每个网民上网时长
		while (true){
			try {
				//除非时间到.否则会一直等待,直到取出这个元素为止
				Netizen take = dq.take();
				endTime(take);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		//创建一个网吧
		InternetBar internetBar = new InternetBar();
		//创建网民
		internetBar.startPlay("001","侯征",3);
		internetBar.startPlay("002","姚振",3);
		internetBar.startPlay("003","何毅",5);
		Thread t1=new Thread(internetBar);
		t1.start();
	}
}
