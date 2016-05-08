package br.com.bjbraz.app.estabelecimentos.config.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

/**
 *
 * @version $VERSION
 */
@Aspect
public class FlushAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.FLUSH_ASPECT_ORDER;
    }

    @PersistenceContext()
    private EntityManager em;

    @AfterReturning("br.com.bjbraz.framework.aop.FrameworkPointcuts.businessServiceImpl()")
    public void flush(JoinPoint jp) throws Throwable {
        if (isTransactionInProgress()) {
            
            Logger logger = Util.getLogger(jp);

            if (logger.isTraceEnabled()) {
                logger.trace("-- " + Util.methodName(jp) + ": flushing");
                em.flush();
                logger.trace("-- " + Util.methodName(jp) + ": flushed");
            }
            else {
                em.flush();
            }
        }
    }

    private boolean isTransactionInProgress() {
        SessionImpl session = (SessionImpl) em.getDelegate();
        return session.isTransactionInProgress();
    }

}
