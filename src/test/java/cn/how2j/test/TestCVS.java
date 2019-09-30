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
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestCVS {

	@Test
	@Comment("读写CVS")
	public void test1() {
		
		//写数据
		CsvWriter writer = CsvUtil.getWriter("test.csv", CharsetUtil.CHARSET_UTF_8);
		writer.write(
		    new String[] {"a1", "b1", "c1"}, 
		    new String[] {"a2", "b2", "c2"}, 
		    new String[] {"a3", "b3", "c3"}
		);
		//读数据
		
		CsvReader reader = CsvUtil.getReader();
		//从文件中读取CSV数据
		CsvData data = reader.read(FileUtil.file("test.csv"));
		List<CsvRow> rows = data.getRows();
		//遍历行
		for (CsvRow csvRow : rows) {
		    //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
		    System.out.println(csvRow.getRawList());
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
