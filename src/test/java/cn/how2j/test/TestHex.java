package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.awt.Color;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestHex {
    @Test
    @Comment("判断是否是十六进制")
    public void test1() {

    	String s1 = "12";
    	boolean b1 = HexUtil.isHexNumber(s1);
    	String s2 = "0x12";
    	boolean b2 = HexUtil.isHexNumber(s2);
    	
    	p2("字符串",s1, "是否十六机制",b1);
    	p2("字符串",s2, "是否十六机制",b2);
         
    }
    @Test
    @Comment("字符串和十六进制互相转换")
    public void test2() {
    	
    	String s1 = "how2j.cn - java教程";
    	String s2 = HexUtil.encodeHexStr(s1);
    	String s3 = HexUtil.decodeHexStr(s2);
    	
    	p2("原数据",s1, "十六机制编码",s2);
    	p2("十六进制",s2, "十六机制解码",s3);
    	
    }
    @Test
    @Comment("颜色转换")
    public void test3() {
    	
    	Color color1 = Color.red;
    	String s1 = HexUtil.encodeColor(color1);
    	String s2 = "#112233";
    	Color color2 = HexUtil.decodeColor(s2);
    	p2("颜色对象1",color1, "字符串",s1);
    	p2("字符串",s2, "颜色对象2",color2);
    	
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