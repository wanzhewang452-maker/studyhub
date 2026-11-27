package com.studyhub.aspect;

import com.studyhub.annotation.AutoFill;
import com.studyhub.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.studyhub.mapper.*.*(..))" +
            " && @annotation(com.studyhub.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {

        log.info("开始进行公共字段自动填充");

        // 1. 获取方法签名
        MethodSignature signature =
                (MethodSignature) joinPoint.getSignature();

        // 2. 获取方法上的注解
        Method method = signature.getMethod();
        AutoFill autoFill = method.getAnnotation(AutoFill.class);

        // 3. 获取操作类型
        OperationType operationType = autoFill.value();

        // 4. 获取实体对象
        Object[] args = joinPoint.getArgs();

        if (args.length == 0 || args[0] == null) {
            throw new IllegalArgumentException("缺少实体参数");
        }

        Object entity = args[0];

        // 5. 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 6. 反射填充公共字段
        try {
            if (operationType == OperationType.INSERT) {

                Method setCreatedAt = entity.getClass()
                        .getMethod("setCreatedAt", LocalDateTime.class);

                setCreatedAt.invoke(entity, now);
            }

            Method setUpdatedAt = entity.getClass()
                    .getMethod("setUpdatedAt", LocalDateTime.class);

            setUpdatedAt.invoke(entity, now);

        } catch (ReflectiveOperationException e) {

            throw new IllegalStateException(
                    "公共字段自动填充失败", e);
        }
    }
}