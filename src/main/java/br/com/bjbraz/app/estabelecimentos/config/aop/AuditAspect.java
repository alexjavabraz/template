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
public class AuditAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.AUDIT_ASPECT_ORDER;
    }

    @Around("br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl() "
        + "&& @annotation(br.com.bjbraz.framework.annotation.Audit)")
    public Object audit(ProceedingJoinPoint jp) throws Throwable {
        Logger logger = Util.getLogger(jp);

        Object retVal = null;
     
        return retVal;
    }

}
