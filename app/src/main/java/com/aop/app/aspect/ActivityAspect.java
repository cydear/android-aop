package com.aop.app.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 针对Activity的切面定义
 *
 * @author lary.huang
 * @version v 1.4.8 2018/12/26 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
//使用@Aspect注解标注，这样class ActivityAspect就等同于aspect ActivityAspect
@Aspect
public class ActivityAspect {
    /**
     * @Pointcut 注解作用于一个函数，它就代表这个pointcut切入点的名字。
     * 如果带参数的pointcut，则把参数类型和名字放到代表pointcut名字logForActivity中，然后在@pointcut注解中使用参数名。
     */
    @Pointcut("execution(* com.aop.app..*Activity.onCreate(..))")
    public void logForActivity() {
    }

    /**
     * 定义Advice
     *
     * @param joinPoint
     * @Before：这就是Before的advice，对于after，after -returning，和after-throwing。对于的注解格式为
     * @After，@AfterReturning，@AfterThrowing。Before后面跟的是pointcut名字，然后其代码块由一个函数来实现。比如此处的log。
     */
    @Before("logForActivity()")
    public void log(JoinPoint joinPoint) {
        Log.d("====>AspectJ：", "====>:"+joinPoint.toShortString());
    }
}
