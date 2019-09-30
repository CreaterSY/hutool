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
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestHash {
    @Test
    @Comment("各种各样的hash算法")
    public void test2() {
    	String s = "how2j.cn- java教程";
    	int number = 12;
    	long hash1 = HashUtil.additiveHash(s, Integer.MAX_VALUE);
    	long hash2 = HashUtil.rotatingHash(s, Integer.MAX_VALUE);
    	long hash3 = HashUtil.oneByOneHash(s);
    	long hash4 = HashUtil.bernstein(s);
//    	long hash5 = HashUtil.universal(s); 怎么调用？站长不会调用。。。颜面~~~ 
//    	long hash6 = HashUtil.zobrist(s);
    	long hash7 = HashUtil.fnvHash(s);
    	long hash8 = HashUtil.intHash(number);
    	long hash9 = HashUtil.rsHash(s);
    	long hash10 = HashUtil.jsHash(s);
    	long hash11 = HashUtil.pjwHash(s);
    	long hash12 = HashUtil.elfHash(s);
    	long hash13 = HashUtil.bkdrHash(s);
    	long hash14 = HashUtil.sdbmHash(s);
    	long hash15 = HashUtil.djbHash(s);
    	long hash16 = HashUtil.dekHash(s);
    	long hash17 = HashUtil.apHash(s);
    	long hash18 = HashUtil.tianlHash(s);
    	long hash19 = HashUtil.javaDefaultHash(s);
    	long hash20 = HashUtil.mixHash(s);    	
    	p2("原数据",s, "加法算法对应的哈希值", hash1);
    	p2("原数据",s, "旋转算法对应的哈希值", hash2);
    	p2("原数据",s, "一次一个算法对应的哈希值", hash3);
    	p2("原数据",s, "Bernstein's算法对应的哈希值", hash4);
//    	p2("原数据",s, " Universal 算法对应的哈希值", hash5);
//    	p2("原数据",s, " Zobrist 算法对应的哈希值", hash6);
    	p2("原数据",s, " 改进的32位FNV 算法对应的哈希值", hash7);
    	p2("原数据",s, "Thomas Wang的整数算法对应的哈希值", hash8);
    	p2("原数据",s, "RS算法对应的哈希值", hash9);
    	p2("原数据",s, "JS算法对应的哈希值", hash10);
    	p2("原数据",s, "PJ算法对应的哈希值", hash11);
    	p2("原数据",s, "ELF算法对应的哈希值", hash12);
    	p2("原数据",s, "BKDR算法对应的哈希值", hash13);
    	p2("原数据",s, "SDBM算法对应的哈希值", hash14);
    	p2("原数据",s, "DJB算法对应的哈希值", hash15);
    	p2("原数据",s, "DEK算法对应的哈希值", hash16);
    	p2("原数据",s, "AP算法对应的哈希值", hash17);
    	p2("原数据",s, "TianL算法对应的哈希值", hash18);
    	p2("原数据",s, "JAVA自己带算法对应的哈希值", hash19);
    	p2("原数据",s, "混合算法对应的哈希值", hash20);
    	
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