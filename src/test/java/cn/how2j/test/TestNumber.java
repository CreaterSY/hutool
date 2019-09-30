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
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestNumber {

	@Test
	@Comment("精确计算")
	public void test1(){
		double result1 = (1.2 - 0.4);
		p3("浮点数计算 1.2 - 0.4 无法得到精确结果",result1);
		double result2 = NumberUtil.sub(1.2, 0.4);
		p3("浮点数计算 NumberUtil.sub(1.2,0.4) 就能得到精确结果",result2);
	}
	@Test
	@Comment("四舍五入")
	public void test2(){
		double a = 100.123;
		double b = 100.125;
		
		double result1= NumberUtil.round(a, 2).doubleValue();
		double result2= NumberUtil.round(b, 2).doubleValue();
		
		p1("浮点数", a, "四舍五入之后", result1);
		p1("浮点数", b, "四舍五入之后", result2);
		
	}
	@Test
	@Comment("数字格式化")
	public void test3(){
		
//		0 -> 取一位整数
//		0.00 -> 取一位整数和两位小数
//		00.000 -> 取两位整数和三位小数
//		# -> 取所有整数部分
//		#.##% -> 以百分比方式计数，并取两位小数
//		#.#####E0 -> 显示为科学计数法，并取五位小数
//		,### -> 每三位以逗号进行分隔，例如：299,792,458
//		光速大小为每秒,###米 -> 将格式嵌入文本
		
		p3("对π进行格式化，π的值是",Math.PI);
		double pi= Math.PI;
		String format = null;
		String str = null;
		format= "0";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= "0.00";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= "00.000";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= "#";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= "#.##";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= "#.##%";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);
		
		format= "#.####E0";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);

		format= ",###";
		str = NumberUtil.decimalFormat(format,pi*10000);
		p2("格式",format,"x1000 再格式化后得到", str);

		format= ",####";
		str = NumberUtil.decimalFormat(format,pi*10000);
		p2("格式",format,"x1000 再格式化后得到", str);

		format= "π的大小是#.##########，请课后记忆";
		str = NumberUtil.decimalFormat(format,pi);
		p2("格式",format,"格式化后得到", str);
		 
	}
	@Test
	@Comment("数字判断")
	public void test4(){
		String s1 = "3.1415926";
		int n = 11;
		p2("字符串",s1, "是否数字",NumberUtil.isNumber(s1));
		p2("字符串",s1, "是否整数(这个有问题)",NumberUtil.isInteger(s1));
		p2("字符串",s1, "是否浮点数",NumberUtil.isDouble(s1));
		p2("整数",n, "是否质数",NumberUtil.isPrimes(n));
	}
	@Test
	@Comment("随机数")
	public void test5(){
		int random[]=NumberUtil.generateRandomNumber(1,1000,10);
		
		p3("最小是1，最大是1000，总长度是10的不重复随机数组",Convert.toStr(random));
	}
	@Test
	@Comment("整数列表")
	public void test6(){
		int numbers[] = NumberUtil.range(0, 100, 9);
		p3("最小是0，最大是100，步长是9的数组",Convert.toStr(numbers));
	}
	@Test
	@Comment("其他相关")
	public void test7(){
		
		p3("计算3的阶乘", NumberUtil.factorial(3));
		p3("计算9的平方根", NumberUtil.sqrt(9));
		p3("计算9和6的最大公约数", NumberUtil.divisor(9,6));
		p3("计算9和6的最小公倍数", NumberUtil.multiple(9,6));
		p3("获得数字9对应的二进制字符串", NumberUtil.getBinaryStr(9));
		p3("获取123456789对应金额", NumberUtil.decimalFormatMoney(123456789));
	}
	
	

	

	

	

	

	


	
	
    private String preComment = null;  
    private void c(String msg) {
        System.out.printf("\t备注：%s%n",msg);
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
        if("format1".equals(format)) {
            System.out.printf("\t%s的:\t\"%s\" %n\t被转换为----->%n\t%s的 :\t\"%s\" %n%n",type1,value1, type2, value2);
        }
        if("format2".equals(format)) {
            System.out.printf("\t基于 %s:\t\"%s\" %n\t获取 %s:\t\"%s\"%n%n",type1,value1, type2, value2);
        }
        if("format3".equals(format)) {
            System.out.printf("\t%s:\t\"%s\" %n\t%n",type1,value1);
 
        }
    }
     
    private String getTestMethodName(StackTraceElement[] stackTrace) {
        for (StackTraceElement se : stackTrace) {
            String methodName = se.getMethodName();
            if(methodName.startsWith("test"))
                return methodName;
        }
        return null;
    }
 
    @Target({METHOD,TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Comment {
         String value();
    }
}
