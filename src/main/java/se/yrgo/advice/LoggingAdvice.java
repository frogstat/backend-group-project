package se.yrgo.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {
    @Before("execution(* se.yrgo.services..*.*(..))")
    public void logMethodCall(JoinPoint jp){
        System.out.println("Calling method: " + jp.getSignature());
    }
}
