package com.aop.app.aspect;

import android.app.Activity;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 校验登录状态切面
 */
@Aspect
public class LoginAspect {
    @Pointcut("execution(@CheckLoginAnnotation * com.aop.app..*(..))")
    public void checkLogin(Activity activity) {
    }

//    @Before("checkLogin()")
//    public void check(JoinPoint joinPoint) {
//        Log.d("===>", "===>您没有登录，请先去登录");
//        return;
//    }

    @Around("checkLogin()")
    public void check(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isLogin = false;
        if (!isLogin) {
            Log.d("===>", "===>您没有登录，请先去登录");
        } else {
            joinPoint.proceed();
        }
    }
}
