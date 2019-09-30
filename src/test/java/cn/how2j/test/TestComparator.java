package cn.how2j.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.comparator.ComparatorChain;
import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.comparator.PropertyComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestComparator {
	@Test
	@Comment("属性比较器")
	public void test1() {
		List<Hero> heros = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			heros.add(new Hero("hero "+ i, RandomUtil.randomInt(100)));
		}
		System.out.println("未排序的集合:");
		System.out.println(CollectionUtil.join(heros, "\r\n"));

		Collections.sort(heros,new PropertyComparator<>("hp"));
		System.out.println("根据属性 hp 排序之后：");
		System.out.println(CollectionUtil.join(heros, "\r\n"));
	}


	@Test
	@Comment("拼音比较器")
	public void test2() {
		List<String> names = new ArrayList<>();
		names.add("令狐冲");
		names.add("陈家洛");
		names.add("石破天");
		names.add("胡一刀");

		p3("未排序的集合",CollectionUtil.join(names, " , "));
		
		Collections.sort(names,new PinyinComparator());
		p3("根据拼音排序的集合",CollectionUtil.join(names, " , "));
	}
	
	
	
	
	
	
	
	class Hero{
		String name;
		int hp;
		
		
		
		
		public Hero(String name, int hp) {
			super();
			this.name = name;
			this.hp = hp;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getHp() {
			return hp;
		}
		public void setHp(int hp) {
			this.hp = hp;
		}
		@Override
		public String toString() {
			return "Hero [name=" + name + ", hp=" + hp + "]";
		}
		
		
		
		
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
