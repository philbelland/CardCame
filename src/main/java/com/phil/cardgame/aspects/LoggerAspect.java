package com.phil.cardgame.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.phil.cardgame..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        //System.out.println(">>> "+joinPoint.getSignature().toString() + " method execution start");
        Instant start = Instant.now();
        Object returnObj = joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(">>> " + joinPoint.getSignature().toString() + ": "+timeElapsed + "ms");
        //System.out.println(">>> "+joinPoint.getSignature().toString() + " method execution end");
        return returnObj;
    }
}
