package cn.how2j.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

public class TestMail {
	private MailAccount account;
	
	@Before
	public void prepareMailAccount() {
		account = new MailAccount();
		account.setHost("smtp.163.com");
		account.setPort(25);
		account.setAuth(true);
		account.setFrom("huidefengsy@163.com"); //假邮箱，请自己申请真实邮箱
		account.setUser("huidefengsy@163.com"); //假邮箱，请自己申请真实邮箱
		account.setPass("878869sy"); //假密码，请自己申请真实邮箱
	}
	
	@Test
	@Comment("发送普通文本")
	public void test1(){
		MailUtil.send(account,"huidefengsy@163.com", "hutool 测试邮件" + DateUtil.now(), "测试内容", false);
	}
	@Test
	@Comment("发送html邮件")
	public void test2(){
		//因为账号密码不对，所以不能正确发送
		MailUtil.send(account,"huidefengsy@163.com", "hutool 测试邮件" + DateUtil.now(), "<p>测试内容</p>", true);
	}
	@Test
	@Comment("发送带附件的邮件")
	public void test3(){
		
		//因为账号密码不对，所以不能正确发送
		MailUtil.send(account,"huidefengsy@163.com", "hutool 测试邮件" + DateUtil.now(), "<p>测试内容</p>", true, FileUtil.file("d:/test.txt"));
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
