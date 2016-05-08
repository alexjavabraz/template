package br.com.bjbraz.app.estabelecimentos.config.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

import br.com.bjbraz.app.estabelecimentos.exception.BusinessException;
import br.com.bjbraz.app.estabelecimentos.exception.ErrorMessage;
import br.com.bjbraz.app.estabelecimentos.exception.SystemException;

/**
 *
 * @version $VERSION
 */
@Aspect
public class ExceptionBarrierAspect implements Ordered {

    public int getOrder() {
        return FrameworkPointcuts.EXCEPTION_BARRIER_ASPECT_ORDER;
    }

    @AfterThrowing(pointcut = "br.com.bjbraz.app.estabelecimentos.config.aop.FrameworkPointcuts.businessServiceImpl()",
        throwing = "ex", argNames = "jp,ex")
    public void catchException(JoinPoint jp, Throwable ex) throws Throwable {

        String occurrenceId = nextOccurrenceId();
        String methodName = Util.methodName(jp);

        Logger logger = Util.getLogger(jp);
        logger.debug("<< " + methodName + " -> EXCEPTION [" + occurrenceId + "] " + ex.getMessage());

        // excecoes de negocio nao precisam de ERROR log
        if (ex instanceof BusinessException) {
            throw ex;
        }

        // encapsula a excecao em uma SystemException, se necessario
        SystemException sex = (ex instanceof SystemException)
            ? (SystemException) ex
            : new SystemException(ex, ErrorMessage.UNEXPECTED_ERROR, ex.getMessage());

        sex.setTimeStamp(new Date());
        sex.setOccurrenceId(occurrenceId);
        sex.setCurrent(jp.getThis());
        sex.setMethodName(methodName);
        sex.setArgs(jp.getArgs());

        if (logger.isErrorEnabled()) {
            dumpFault(sex, logger);
        }

        throw sex;
    }

    private void dumpFault(SystemException sex, Logger logger) {
        StringBuilder args = new StringBuilder();
        Util.appendArguments(sex.getArgs(), args);

        logger.error("# Error code: " + sex.getErrorCode());
        logger.error("# Error message: " + sex.getMessage());
        logger.error("# Ocurrence: " + sex.getOccurrenceId());
        logger.error("# Timestamp: " + sex.getTimeStamp());
        logger.error("# Service: " + sex.getCurrent());
        logger.error("# Method: " + sex.getMethodName());
        logger.error("# Arguments: (" + args + ")");

        Throwable rootCause = sex;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        StringWriter rootCauseStack = new StringWriter();
        PrintWriter writer = new PrintWriter(rootCauseStack);
        writer.println();
        rootCause.printStackTrace(writer);

        logger.error("# Cause: " + rootCauseStack);
        /*
        if (logger.isDebugEnabled()) {
            StringWriter completeStack = new StringWriter();
            PrintWriter writer2 = new PrintWriter(completeStack);
            writer2.println();
            sex.printStackTrace(writer2);
            logger.debug("# Complete stack: " + completeStack);
        }
        */
    }

    private static int occurrenceSeq = 1;

    private static synchronized String nextOccurrenceId() {
        int id = occurrenceSeq;
        occurrenceSeq++;
        return String.valueOf(id);
    }

}
