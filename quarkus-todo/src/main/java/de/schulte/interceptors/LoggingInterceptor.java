package de.schulte.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Loggable
@Interceptor
public class LoggingInterceptor {

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ctx) throws Exception {
        System.out.printf("Logging für Method %s.%s (Hashcode = %s)%n",
                ctx.getTarget().getClass().getSimpleName(),
                ctx.getMethod().getName(),
                hashCode());
        return ctx.proceed();
    }
}
