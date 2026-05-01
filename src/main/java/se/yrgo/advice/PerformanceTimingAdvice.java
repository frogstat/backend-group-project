package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {
    @Around("execution(* se.yrgo.data..*.*(..))")
    public Object performTimeMeasurement(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();

        Object result = pjp.proceed();

        long end = System.nanoTime();

        double timeInMs = (end - start) / 1_000_000.0;

        System.out.printf("Time taken for method %s from class %s was %.2f ms.%n",
                pjp.getSignature().getName(), pjp.getTarget().getClass().getSimpleName(),
                timeInMs);

        return result;
    }


}
