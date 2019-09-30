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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestUnicode {
	@Test
	@Comment("unicode转换")
	public void test1() {
		String charset = "utf-8";

		String content = "how2j.cn - java教程";
		
		p3("原字符串",content);
		
		String unicode= UnicodeUtil.toUnicode(content);
		content = UnicodeUtil.toString(unicode);

		p3("获取unicode",unicode);
		p3("转会原字符串",content);

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
