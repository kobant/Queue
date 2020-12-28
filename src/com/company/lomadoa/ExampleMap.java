package com.company.lomadoa;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/28 11:29
 */
public class ExampleMap {

	private static Map<String, Integer> items = new HashMap<>();

	static {
		items.put("A", 10);
		items.put("B", 20);
		items.put("C", 30);
		items.put("D", 40);
		items.put("E", 50);
		items.put("F", 60);
	}

	public static void main(String[] args) {

		//Java8之前遍历是这样遍历map
		for(Map.Entry<String,Integer> entry:items.entrySet()){
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
		}

		System.out.println("-----------------------------");
		//Java8遍历map
		//items.forEach((key,value)-> System.out.println("key:" + key + " value:" + value));

       items.forEach((key,value)-> System.out.println("key:" + key + " value:" + value));
	}


}