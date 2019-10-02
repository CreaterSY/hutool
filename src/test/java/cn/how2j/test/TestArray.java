package cn.how2j.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class TestArray {

	@Test
	@Comment("为空的判断")
	public void test1(){
		int [] a = null;
		int [] b = new int[5];
		int [] c = new int[] {10,11,12};
		
		p1("数组",Convert.toStr(a),"是否为空", ArrayUtil.isEmpty(a));
		p1("数组",Convert.toStr(b),"是否为空", ArrayUtil.isEmpty(b));
		p1("数组",Convert.toStr(c),"是否为空", ArrayUtil.isEmpty(c));
		
	}
	@Test
	@Comment("调整数组大小")
	public void test2(){
		Integer [] a = new Integer[] {10,11,12};
		Integer[] b = ArrayUtil.resize(a,5);
		
		p3("调整大小前的数组", Convert.toStr(a) );
		p3("调整大小后的数组", Convert.toStr(b) );
		
		
	}
	@Test
	@Comment("合并数组")
	public void test3(){
		Integer [] a = {1,2,3};
		Integer [] b = {10,11,12};
		Integer [] c = ArrayUtil.addAll(a,b);
		
		p2("合并前的两个数组 " ,Convert.toStr(a) + " , " + Convert.toStr(b), "合并后的数组是", Convert.toStr(c));
	}
	
	
	@Test
	@Comment("克隆")
	public void test4(){
		Integer [] a = {1,2,3};
		Integer b[]= ArrayUtil.clone(a);
		
		p2("原数组",Convert.toStr(a), "克隆的数组", Convert.toStr(b));
		
	}
	@Test
	@Comment("生成有序数组")
	public void test5(){
		p3("生成开始是0，结束是100，步长是9的有序数组", Convert.toStr( ArrayUtil.range(0, 100, 9)));
	}
	@Test
	@Comment("过滤")
	public void test6(){
		Integer []a = {1,2,3,4,5,6,7,8,9};
		Integer[] b = ArrayUtil.filter(a,new Filter<Integer>() {

			@Override
			public boolean accept(Integer t) {
				if(0==t%3)
					return true;
				return false;
			}
			
		});
		
		p2("原数组",Convert.toStr(a),"3的倍数过滤之后",Convert.toStr(b));
		
	}
	@Test
	@Comment("转换为map")
	public void test7(){
		Integer a[] = {1,2,3};
		String c[] = {"a","b","c"};
		Map<Integer,String> m = ArrayUtil.zip(a, c);
		
		p2("两个数组",Convert.toStr(a) + " , " + Convert.toStr(c),"转换为 Map ",m);
		
		
		
	}
	@Test
	@Comment("是否包含某元素")
	public void test8(){
		Integer a[] = {1,2,3};
		
		p1("数组", Convert.toStr(a),"是否包含元素3", ArrayUtil.contains(a, 3));
		
	}
	@Test
	@Comment("装箱拆箱")
	public void test9(){
		int a[] = {1,2,3};
		Integer b[] = ArrayUtil.wrap(a);
		int c[] = ArrayUtil.unWrap(b);
		
		p3("数组基本类型的装箱拆箱","ArrayUtil.wrap | ArrayUtil.unWrap");
	}
	@Test
	@Comment("转换为字符串")
	public void testa(){
		int a[] = {1,2,3};
		
		p3("数组转换为默认字符串", ArrayUtil.toString(a) );
		p3("数组转换为自定义分隔符的字符串", ArrayUtil.join(a,"-" ));
		
	}
	@Test
	@Comment("拆分")
	public void testb(){
		
		byte []a = {1,2,3,4,5,6,7,8,9};
		
		byte[][] b=ArrayUtil.split(a, 2);
		
		p3("数组被拆成2为长度的等份",Convert.toStr(a));
		
		for (byte[] bs : b) {
			p3("拆分后的数组：",Convert.toStr(bs));
		}
				
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
