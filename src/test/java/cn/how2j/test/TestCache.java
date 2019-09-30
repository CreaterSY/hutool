package cn.how2j.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestCache {
	@Test
	@Comment("缓存工具")
	public void test0(){
		p4("hutool的缓存，在处理缓存满了或者到期的时候，有如下几种策略：");

		p3("FIFOCache","first in first out , 先入先出，一旦缓存满了，先放进去的，先被清空");
		p3("LFUCache","least frequently used, 一旦缓存满了，用得最少的，先被清空 (命中低的数据被清空)");
		p3("LRUCache","least recently used, 一旦缓存满了，最久没用的，先被清空 (旧数据被清空)");
		p3("TimedCache","一旦时间到了，被清空 (考虑时效性)");
		p3("WeakCache","一旦内存满了，要垃圾回收了，优先被清空 (内存占用重要性)");
		p3("FileCach","把文件对象作为缓存，减少IO访问频率");
		
//		LFUCache
//		LRUCache least recently used
//		TimedCache
//		WeakCache
//		FileCach
	}
	
	@Test
	@Comment("FIFO 示例")
	public void test1(){
		p4("初始化缓存大小是 2");
		p4("接着向里面挨个放入1，2，3，导致缓存变满");
		Cache<String,Integer> cache= CacheUtil.newFIFOCache(2);
		cache.put("key1",1 );
		cache.put("key2",2 );
		cache.put("key3",3 );
		p3("遍历缓存中的数据",CollectionUtil.join(cache, ","));
		p4("如预料一般，最早放入的最先被清空");
	}
	
	@Test
	@Comment("LFUCache 示例")
	public void test2(){
		p4("初始化缓存大小是 2");
		p4("接着向里面挨个放入1，2，3，导致缓存变满");
		Cache<String,Integer> cache= CacheUtil.newLFUCache(2);
		cache.put("key1",1 );
		cache.put("key2",2 );
		p4("中途故意使用一次 key1");
		cache.get("key1");
		cache.put("key3",3 );
		p3("遍历缓存中的数据",CollectionUtil.join(cache, ","));
		p4("如预料一般，使用频率最低的 2 的被清空");
	}

	@Test
	@Comment("LFUCache 示例")
	public void test3(){
		p4("初始化缓存大小是 2");
		p4("接着向里面挨个放入1，2，3，导致缓存变满");
		Cache<String,Integer> cache= CacheUtil.newLRUCache(2);
		cache.put("key1",1 );
		cache.put("key2",2 );
		p4("中途故意使用一次 key1");
		cache.get("key1");
		cache.put("key3",3 );
		p3("遍历缓存中的数据",CollectionUtil.join(cache, ","));
		p4("如预料一般，最久没有被使用的  2 的被清空");
	}

	@Test
	@Comment("TimedCache 示例")
	public void test4(){
		p4("初始化缓存大小是 2");
		p4("接着向里面挨个放入1，2， 分别放设置存放时间为1秒和5秒");
		Cache<String,Integer> cache= CacheUtil.newTimedCache(Integer.MAX_VALUE);
		cache.put("key1",1, 1000 );
		cache.put("key2",2,5000 );
		p4("休息3秒");
		ThreadUtil.sleep(3000);
		p3("遍历缓存中的数据",CollectionUtil.join(cache, ","));
		p4("如预料一般， 经过3秒后，1被清空了，2还在");
	}
	
	@Test
	@Comment("WeakCache 示例")
	public void test5(){
		p4("WeekCache表示当垃圾回收发生的时候，不会阻挡回收器把它回收走。");
		
		p4("请注意看描述：\"不会阻挡\", 就是说，垃圾回收真正要对它下手了，是可以下手的。");
		p4("但是垃圾回收发生的时候，不一定会回收所有垃圾和 week引用。");
		p4("正因为如此，不易观察到现象，而且不稳定，所以就不做演示了，免得误导");
		
	}
	
	@Test
	@Comment("FileCache 示例")
	public void test6(){
		p4("FileCache 也分 LFU, LRU 等，只是调用方式有所区别，并没有被放到 CacheUtil里，找了好一会儿才找到。。。，");
		//参数1：容量，能容纳的byte数
		//参数2：最大文件大小，byte数，决定能缓存至少多少文件，大于这个值不被缓存直接读取
		//参数3：超时。毫秒
		
		long capacity = 1024*1024*500; //最多500m, 太大了，内存吃不消，缓存就没法实施了
		long maxFileSize = 1024*1024*10; //最大10m, 文件小于这个就缓存，太大了也不缓存
		long timeout = 1000*60*60*24; //缓存一天，超过这个就自动从缓存里移除了
		
		LFUFileCache cache = new LFUFileCache(1024*1024*500, 500, 2000);
		//使用办法：
		//byte[] bytes = cache.getFileBytes("e:/project/hutool/img/logo.png");
		
	}
	
	
	
	
	

	
	
	
	
	
	
	


	
	
	
	
	
	


	private String preComment = null;

	private void c(String msg) {
		System.out.printf("\t备注：%s%n", msg);
	}

	private void p1(String type1, Object value1, String type2, Object value2) {
		p(type1, value1, type2, value2, "format1");
	}

	private void p2(String type1, Object value1, String type2, Object value2) {
		p(type1, value1, type2, value2, "format2");
	}

	private void p3(String type1, Object value1) {
		p(type1, value1, "", "", "format3");
	}
	private void p4(Object value) {
		p(null, value, "", "", "format4");
	}

	private void p(String type1, Object value1, String type2, Object value2, String format) {
		try {
			throw new Exception();
		} catch (Exception e) {

			String methodName = getTestMethodName(e.getStackTrace());
			Method m = ReflectUtil.getMethod(this.getClass(), methodName);
			Comment annotation = m.getAnnotation(Comment.class);
			if (null != annotation) {
				String comment = annotation.value();
				if (!comment.equals(preComment)) {
					System.out.printf("%n%s 例子： %n%n", comment);
					preComment = comment;
				}

			}
		}
		int padLength = 12;
		type1 = StrUtil.padEnd(type1, padLength, Convert.toSBC(" ").charAt(0));
		type2 = StrUtil.padEnd(type2, padLength, Convert.toSBC(" ").charAt(0));
		if ("format1".equals(format)) {
			System.out.printf("\t%s的:\t\"%s\" %n\t被转换为----->%n\t%s的 :\t\"%s\" %n%n", type1, value1, type2, value2);
		}
		if ("format2".equals(format)) {
			System.out.printf("\t基于 %s:\t\"%s\" %n\t获取 %s:\t\"%s\"%n%n", type1, value1, type2, value2);
		}
		if ("format3".equals(format)) {
			System.out.printf("\t%s:\t\"%s\" %n\t%n", type1, value1);

		}
		if ("format4".equals(format)) {
			System.out.printf("\t%s%n%n", value1);
			
		}
	}

	private String getTestMethodName(StackTraceElement[] stackTrace) {
		for (StackTraceElement se : stackTrace) {
			String methodName = se.getMethodName();
			if (methodName.startsWith("test"))
				return methodName;
		}
		return null;
	}

	@Target({ METHOD, TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	public @interface Comment {
		String value();
	}
}
