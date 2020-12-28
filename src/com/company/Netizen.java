package com.company;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/28 16:59
 */
public class Netizen implements Delayed {

	//身份证
	private String ID;
	//名字
	private String name;
	//上网截止时间
	private long playTime;
    //比较优先级,时间最短的优先


	public Netizen(String ID, String name, long playTime) {
		this.ID = ID;
		this.name = name;
		this.playTime = playTime;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPlayTime() {
		return playTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	//获取上网时长，既延时时长
	@Override
	public long getDelay(TimeUnit unit) {
		//上网截止时间减去现在当前时间=时长
		return this.playTime-System.currentTimeMillis();
	}

	@Override
	public int compareTo(Delayed o) {
		Netizen netizen = (Netizen) o;
		return this.getDelay(TimeUnit.SECONDS)-o.getDelay(TimeUnit.SECONDS)>0?1:0;
	}
}
