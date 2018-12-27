package com.aop.app.aspect;

import com.aop.app.AopApplication;
import com.aop.app.annotation.AndroidPermission;
import com.yanzhenjie.permission.AndPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 权限授权Aspect
 */
@Aspect
public class AndroidPermissionAspect {
    private final static String PERMISSION_POINTCUT = "execution(@com.aop.app.annotation.AndroidPermission * com.aop.app..*(..))&&@annotation(permissionAnn)";

    /**
     * 定义PointCuts 选择需要进行授权的执行点JoinPoint
     */
    @Pointcut(PERMISSION_POINTCUT)
    public void checkPermission(AndroidPermission permissionAnn) {

    }

    /**
     * 针对PERMISSION_POINTCUT 选择的执行点JoinPoints Hook的Advice
     *
     * @param joinPoint
     * @param permissionAnn
     * @throws Throwable
     */
    @Around("checkPermission(permissionAnn)")
    public void applyPermission(ProceedingJoinPoint joinPoint, AndroidPermission permissionAnn) throws Throwable {
        String[] permissions = permissionAnn.permissions();
        if (permissions == null || permissions.length <= 0) {
            joinPoint.proceed();
            return;
        }
        //申请权限
        excuteApplyPermission(joinPoint, permissions);
    }

    /**
     * 申请权限
     *
     * @param joinPoint
     * @param permissions
     */
    private void excuteApplyPermission(ProceedingJoinPoint joinPoint, String[] permissions) {
        AndPermission.with(AopApplication.getTopActivity())
                .runtime()
                .permission(permissions)
                .rationale((context, data, executor) -> {
                    executor.execute();
                })
                .onGranted(perms -> {
                    //授权成功
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onDenied(perms -> {
                    //授权失败
                    if (AndPermission.hasAlwaysDeniedPermission(AopApplication.getTopActivity(), perms)) {
                        //进入系统设置权限页
                        AndPermission.with(AopApplication.getTopActivity())
                                .runtime()
                                .setting()
                                .start();
                    }
                })
                .start();
    }
}
