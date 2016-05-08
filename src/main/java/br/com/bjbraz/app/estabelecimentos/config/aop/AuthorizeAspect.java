package br.com.bjbraz.app.estabelecimentos.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.core.Ordered;

/**
 *
 * @version $VERSION
 */
@Aspect
public class AuthorizeAspect implements Ordered {


    public int getOrder() {
        return FrameworkPointcuts.AUTHORIZE_ASPECT_ORDER;
    }

    @Around("br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl() "
        + "&& @annotation(br.com.bjbraz.framework.annotation.Authorize)")
    public Object authorize(ProceedingJoinPoint jp) throws Throwable {

        Logger logger = Util.getLogger(jp);

        String serviceName = Util.serviceName(jp);
        String methodName = Util.methodName(jp);
        String object = serviceName + "." + methodName;

        Object retVal;
        try {
            retVal = jp.proceed();
        }
        finally {
            MDC.remove("user");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("-- Authorized " + Util.methodName(jp));
        }

        return retVal;
    }

}
