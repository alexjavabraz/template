package br.com.bjbraz.app.estabelecimentos.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 *
 * @version $VERSION
 */
@Aspect
public class MonitorAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.MONITOR_ASPECT_ORDER;
    }

    @Around("br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl()")
    public Object monitor(ProceedingJoinPoint jp) throws Throwable {
        Logger logger = Util.getLogger(jp);

        Object retVal;

        Monitor monitor = MonitorFactory.start(Util.fullMethodName(jp));
        if (monitor.isEnabled()) {
            try {
                retVal = jp.proceed();
            }
            finally {
                monitor.stop();
                if (logger.isDebugEnabled()) {
                    logger.debug("-- " + Util.methodName(jp) + ": " + displayData(monitor));
                }
            }
        }
        else {
            retVal = jp.proceed();
        }
        return retVal;
    }

    private StringBuffer displayData(Monitor monitor) {
        StringBuffer b = new StringBuffer(400);
        b.append("Hits=");
        b.append(monitor.getHits());
        b.append(", LastValue=");
        b.append(monitor.getLastValue());
        b.append(", Min=");
        b.append(monitor.getMin());
        b.append(", Max=");
        b.append(monitor.getMax());
        b.append(", Avg=");
        b.append(monitor.getAvg());
        b.append(", Total=");
        b.append(monitor.getTotal());
        b.append(", StdDev=");
        b.append(monitor.getStdDev());
        b.append(", FirstAccess=");
        b.append(monitor.getFirstAccess());
        b.append(", LastAccess=");
        b.append(monitor.getLastAccess());
        return b;
    }

}
