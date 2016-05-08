package br.com.bjbraz.app.estabelecimentos.config.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @version $VERSION
 */
@Aspect
public class FrameworkPointcuts {

    /* Ordem em que os aspectos devem ser interceptar os metodos da camada de servicos */
    public static final int HIGHEST_PRECEDENCE = org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
    public static final int LOG_ASPECT_ORDER = 100;
    public static final int AUDIT_ASPECT_ORDER = 200;
    public static final int AUTHORIZE_ASPECT_ORDER = 300;
    public static final int TIMER_ASPECT_ORDER = 400;
    public static final int EXCEPTION_BARRIER_ASPECT_ORDER = 500;
    public static final int MONITOR_ASPECT_ORDER = 600;
    public static final int TRANSACTION_ASPECT_ORDER = 1000000000;
    public static final int CACHE_ASPECT_ORDER = 1000000300;
    public static final int FLUSH_ASPECT_ORDER = 1000000400;
    public static final int LOWEST_PRECEDENCE = org.springframework.core.Ordered.LOWEST_PRECEDENCE;

    @Pointcut("execution(* br.com.bjbraz.app..service..*ServiceImpl.*(..))")
    public void businessServiceImpl() {
    }

    @Pointcut("execution(* br.com.bjbraz.app.estabelecimentos.web.jsf.*Bean.*(..))")
    public void backingBean() {
    }

}
