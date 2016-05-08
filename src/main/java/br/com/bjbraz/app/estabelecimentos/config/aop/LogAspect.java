package br.com.bjbraz.app.estabelecimentos.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

/**
 *
 * @version $VERSION
 */
@Aspect
public class LogAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.LOG_ASPECT_ORDER;
    }

    @Before(value = "br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl()")
    public void logEntry(JoinPoint jp) throws Throwable {
        Logger logger = Util.getLogger(jp);

        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(">> ");
            sb.append(Util.methodName(jp));
            sb.append('(');
            Util.appendArguments(jp.getArgs(), sb);
            sb.append(')');
            logger.debug(sb.toString());
        }
    }

    @AfterReturning(pointcut = "br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl() "
            + "&& (execution(void *..*(..)))")
    public void logExit(JoinPoint jp) throws Throwable {
        Logger logger = Util.getLogger(jp);

        if (logger.isDebugEnabled()) {
            logger.debug("<< " + Util.methodName(jp) + " -> void");
        }
    }

    @AfterReturning(pointcut = "br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl() "
            + "&& !(execution(void *..*(..)))", returning = "returningValue", argNames = "jp,returningValue")
    public void logExit(JoinPoint jp, Object returningValue) throws Throwable {
        Logger logger = Util.getLogger(jp);

        if (logger.isDebugEnabled()) {
            logger.debug("<< " + Util.methodName(jp) + " -> " + Util.displayObject(returningValue));
        }
    }

}
