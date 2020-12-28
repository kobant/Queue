package com.company.lomadoa;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/28 11:22
 */
public class ExampleList {
	private static List<String> items = new ArrayList<>();

	static {
		items.add("A");
		items.add("BC");
		items.add("C");
		items.add("BD");
		items.add("E");
	}

	public static void main(String[] args) {
		//Java8之前操作List
		for(String item:items){
			System.out.println(item);
		}

		System.out.println("**********************");
		//Java8 lambda遍历list
		//items.forEach(c-> System.out.println(c) );

		items.forEach(c-> System.out.println(c));
		System.out.println("............................");
		/*items.forEach(item->{
			if("C".equals(item)){
				System.out.println(item);
			}
		});*/

		items.forEach(item->{
			if ("C".equals(item)){
				System.out.println(item);
			}
		});
		System.out.println("--------");

		//先过滤
		//items.stream().filter(s->s.contains("B")).forEach(c1-> System.out.println(c1));

		items.stream().filter(s -> s.contains("B")).forEach(c1-> System.out.println(c1));
	}
}