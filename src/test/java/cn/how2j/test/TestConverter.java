package cn.how2j.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestConverter {
	
	@Test
	@Comment("转换为字符串")
	public void test1() {
		int a = 1;
		String aStr = Convert.toStr(a);

		int[] b = {1,2,3,4,5};
		String bStr = Convert.toStr(b);
		
		Object c = null;
		String cStr = Convert.toStr(c,"空字符串(默认值)");

		p("整数", a, "字符串" , aStr);
		p("long数组", b, "字符串" , bStr);	
		p("空对象", c, "字符串" , cStr);	
		
		
	}
	
	@Test
	@Comment("数组类型互相转化")
	public void test2() {
		String[] a = { "1", "2", "3", "4" };
		Integer[] b = Convert.toIntArray(a);
		p("字符串数组", Convert.toStr(a), "Integer数组" , Convert.toStr(b));	
	}
	
	@Test
	@Comment("数组和集合互换")

	public void test3() {
		
		String[] a = { "1", "2", "3", "4" };
		
		List<?> l = Convert.toList(a);
		
		String[] b= Convert.toStrArray(l);
		p("字符串数组", a, "集合" , l);	
		p("集合", l, "字符串数组" , b);	
	}

		
	
	@Test
	@Comment("半角全角互相转换")
	public void test4() {
		String a = "123456789";
		String b = Convert.toSBC(a);
		String c = Convert.toDBC(b);
		p("半角", a, "全角" , b);	
		p("全角", b, "半角" , c);	
	}
		
	

	
	@Test
	@Comment("Unicode和字符串转换")
	public void test6() {
		String a = "how2j的Hutool教程";
		String unicode = Convert.strToUnicode(a);
		String b = Convert.unicodeToStr(unicode);	
		
		p("字符串", a, "unicode" , unicode);	
		p("unicode", unicode, "字符串" , b);	
		
	}
	@Test
	@Comment("不同编码之间的转换")
	public void test7() {
		String a = "how2j的Hutool教程";
		//转换后result为乱码
		String b = Convert.convertCharset(a, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
		String c = Convert.convertCharset(b, CharsetUtil.ISO_8859_1, "UTF-8");
		
		p("UTF-8", a, "IOS-8859-1" , b);	
		p("IOS-8859-1", b, "UTF-8" , c);	
	}
	
	
	@Test
	@Comment("数字转换为金额")
	public void test8() {
		double  a= 1234567123456.12;
		String b = Convert.digitToChinese(a);
		p("数字", a, "钞票金额" , b);	
	}
	@Test
	@Comment("原始类和包装类转换")
	public void test9() {
		Class<?> wrapClass = Integer.class;

		Class<?> unWraped = Convert.unWrap(wrapClass);

		Class<?> primitiveClass = long.class;

		Class<?> wraped = Convert.wrap(primitiveClass);
		p("包装类型", wrapClass, "原始类型" , unWraped);	
		p("原始类型", primitiveClass, "wraped" , wraped);	
	}

	private String preComment = null;	
	private void p(String type1, Object value1, String type2, Object value2) {
		try {
			throw new Exception();
		} catch (Exception e) {
			String methodName = e.getStackTrace()[1].getMethodName();
			Method m =ReflectUtil.getMethod(this.getClass(), methodName);
			Comment annotation = m.getAnnotation(Comment.class);
			if(null!=annotation) {
				String comment= annotation.value();
				if(!comment.equals(preComment)) {
					System.out.printf("%n%s 例子： %n%n",comment);
					preComment = comment;
				}
				
			}
		}
		int padLength = 12;
		type1=StrUtil.padEnd(type1,padLength,Convert.toSBC(" ").charAt(0));
		type2=StrUtil.padEnd(type2,padLength,Convert.toSBC(" ").charAt(0));
		System.out.printf("\t%s的:\t\"%s\" %n\t被转换为----->%n\t%s的 :\t\"%s\" %n%n",type1,value1, type2, value2);
	}
	
	@Target({METHOD,TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	public @interface Comment {
	     String value();
	}
}
