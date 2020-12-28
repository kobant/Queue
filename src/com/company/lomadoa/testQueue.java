package com.company.lomadoa;

import com.company.Person;
import com.sun.deploy.util.StringUtils;
import com.sun.xml.internal.ws.util.StreamUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/28 14:05
 */
public class testQueue {

	/**
	 * 这是一个使用于高并发场景的队列(额,各位看这块博客的小朋友,
	 * 最好对线程基础比较熟悉再来看,当然我也在拼命学习啦,哈哈哈),主要是无锁的方式,他的想能要比BlockingQueue好
	 * ,是基于链接节点的无界线程安全队列,先进先出,不允许有null元素
	 */
	@Test
	public void test01(){

		ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();
		//add 与offer都是添加元素，在ConcurrentLinkedQueue无区别
		clq.add("a");
		clq.add("b");
		clq.add("c");
		clq.add("d");
		System.out.println("取出头部 ："+clq.poll());  //取出头部，会删除头部
		System.out.println("取出头部 2 ："+clq.peek());//取出头部，但不会删除头部
		System.out.println(clq);
		clq.forEach(c->{
			System.out.println(c);
		});
	}


	/**
	 *   ArrayBlockingQueue
	 */
	@Test
	public void test02()throws Exception {
		//必须指定队列长度
		ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(2);
		abq.add("a");
		//add :添加元素，如果用BlokingQueue可以容纳，则返回true，否则返回false
		System.out.println(abq.offer("b"));//容量不够返回false
		//offer：如果可能的话，即如果BlockingQueue可以容纳,则返回true,
		// 否则返回false,支持设置超时时间
		abq.offer("d",2, TimeUnit.SECONDS);//添加元素 ，时长，单位
		//put :添加元素，如果BlockingQueue没有空间，则此方法的线程阻断直到BlockingQueue里面有空间再继续
		abq.put("d");//会一直等待
		//poll 取出头部元素，若不能立即取出，则可以等time参数规定时间，取不出时则返回null，支持设置超时
		// 时间
		abq.poll();
		abq.poll(2,TimeUnit.SECONDS);//两秒取不到返回null
		//take() 取出头部元素，若BlockingQueue为空，阻断进入等待状态直到Blocking有新对象加入为止
		abq.take();
		//取出头部元素
		abq.element();
		//drainTo //一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），
		// 通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
		List list = new ArrayList();
		abq.drainTo(list,2);//将队列中两个元素取到list中，取走后队列中就没有取走的元素
		System.out.println(list);
		System.out.println(abq);
	}


	/**
	 * 2.2 LinkedBlockingQueue
	 */
	@Test
	public void test03(){
		LinkedBlockingQueue lbq = new LinkedBlockingQueue();//可以指定，也可以不指定
		lbq.add("a");
		lbq.add("b");
		lbq.add("c");

		//API与ArrayBlockingQueue相同
		//是否包含
		System.out.println(lbq.contains("a"));
		//移除头部元素或者指定元素
		System.out.println(lbq.remove());
		//转数组
		Object[] array= lbq.toArray();
		System.out.println(array.toString());
		//element 取出头部  但不删除
		System.out.println(lbq.element());
		System.out.println(lbq.element());
		System.out.println(lbq.element());
	}


	/**
	 * SynchronousQueue
	 */
	@Test
	public void test04(){
		SynchronousQueue<String> sq= new SynchronousQueue<String>();
		// iterator() 永远返回空，因为里面没东西。
		// peek() 永远返回null

		/**
		 * isEmpty()永远是true。
		 * remainingCapacity() 永远是0。
		 * remove()和removeAll() 永远是false。
		 */
        new Thread(()->{
            try {
            	//取出并且remove掉queue里面的element  （认为是在queue里的。。。），取不到东西他会一直等
				System.out.println(sq.take()+"---------");
			}catch (InterruptedException e){
            	e.printStackTrace();
			}
		}).start();

		new Thread(()->{
			try {
				//offer() 往queue里放一个element后立即返回，
				//如果碰巧这个element被另一个thread取走了，offer方法返回true，认为offer成功；否则返回false
				//true ,上面take线程一直在等,
				////下面刚offer进去就被拿走了,返回true,如果offer线程先执行,则返回false
				System.out.println(sq.offer("b"));
			}catch (Exception e){
				e.printStackTrace();
			}
		}).start();

		new Thread(()->{
			try {
				//往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走
				sq.put("a");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * PriorityBlockingQueue
	 */
	@Test
	public void test05() throws Exception{
		//对列里元素必须实现Comparable接口，用来决定优先级
		PriorityBlockingQueue<String> pbq = new PriorityBlockingQueue<>();
		pbq.add("b");
		pbq.add("g");
		pbq.add("a");
		pbq.add("c");
		//获取的时候会根据优先级取元素，插入的时候不会排序，节省性能
		//System.out.println(pbq.take()); //a,获取时会排序,按优先级获取
		System.out.println("排序+  ：");
		System.out.println(pbq.toString());//如果前面没有取值,直接syso也不会排序

		System.out.println("-------------------");
		Iterator<String> iterator = pbq.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.println("-=======================-");
		/*pbq.forEach(i->{
			System.out.println(i);
		});*/

	}

	@Test
	public void test06(){
		PriorityBlockingQueue<Person> pbq = new PriorityBlockingQueue<Person>();
		Person p2=new Person("姚振",20);
		Person p1=new Person("侯征",24);
		Person p3=new Person("何毅",18);
		Person p4=new Person("李世彪",22);
		pbq.add(p1);
		pbq.add(p2);
		pbq.add(p3);
		pbq.add(p4);

		System.out.println(pbq);

		try {
			//只要take获取元素就会按照优先级排序,获取一次就全部排好序了,后面就会按优先级迭代
			pbq.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//按年龄排好了序
		for (Iterator iterator = pbq.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			System.out.println(person);
		}
	}


}
