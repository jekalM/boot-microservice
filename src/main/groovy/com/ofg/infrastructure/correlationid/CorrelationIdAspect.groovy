package com.ofg.infrastructure.correlationid

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

import java.util.concurrent.Callable

@Aspect
class CorrelationIdAspect {

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController))")
    private void anyRestControllerAnnotated() {}

    @Pointcut("@target(org.springframework.stereotype.Controller))")
    private void anyControllerAnnotated() {}

    @Pointcut("execution(public java.util.concurrent.Callable *(..))")
    private void anyPublicMethodReturningCallable() {}

    @Pointcut("(anyRestControllerAnnotated() || anyControllerAnnotated()) && anyPublicMethodReturningCallable()")
    private void anyControllerOrRestControllerWithPublicAsyncMethod() {}

    @Around('anyControllerOrRestControllerWithPublicAsyncMethod()')
    Object wrapWithCorrelationId(ProceedingJoinPoint pjp) throws Throwable {
        Callable callable = pjp.proceed() as Callable
        return CorrelationCallable.withCorrelationId {
            callable.call()
        }
    }

}
