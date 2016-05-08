package br.com.bjbraz.app.estabelecimentos.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

/**
 *
 * @version $VERSION
 */
@Aspect
public class TimerAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.TIMER_ASPECT_ORDER;
    }

    //@Around("br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl()")
    @Around("br.com.bjbraz.framework.aop.FrameworkPointcuts.backingBean()")
    public Object timer(ProceedingJoinPoint jp) throws Throwable {
        Logger logger = Util.getLogger(jp);

        Object retVal;
        if (logger.isDebugEnabled()) {
            long t0 = System.currentTimeMillis();
            try {
                retVal = jp.proceed();
            }
            finally {
                long t1 = System.currentTimeMillis();
                long dt = t1 - t0;

                logger.debug("-- " + Util.methodName(jp) + ": " + dt + " ms");
            }
        }
        else {
            retVal = jp.proceed();
        }
        return retVal;
    }

}
