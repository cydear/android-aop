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
    @Pointcut("execution(@CheckLoginAnnotation * com.aop.app..*(..))&&args(activity)")
    public void checkLogin(Activity activity) {
    }

//    @Before("checkLogin()")
//    public void check(JoinPoint joinPoint) {
//        Log.d("===>", "===>您没有登录，请先去登录");
//        return;
//    }

    @Around("checkLogin(activity)")
    public void check(ProceedingJoinPoint joinPoint, Activity activity) throws Throwable {
        Log.d("===>", "===>activity：" + activity);
        boolean isLogin = false;
        if (!isLogin) {
            Log.d("===>", "===>您没有登录，请先去登录");
        } else {
            joinPoint.proceed();
        }
    }
}
