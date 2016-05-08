package br.com.bjbraz.app.estabelecimentos.config.aop;

import java.util.Date;

/**
 * Excecao de uso interno do Framework, usada para encapsular informacoes de outras excecoes capturadas na
 * camada de servicos.
 *
 * @version $VERSION
 */
public class FaultException extends RuntimeException {

    private static final long serialVersionUID = -4352700620194532272L;
    private Date timeStamp;
    private String occurrenceId;
    private String methodName;
    transient private Object current;
    transient private Object[] args;

    public FaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public FaultException(Throwable cause) {
        super(cause);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setOccurrenceId(String occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    public String getOccurrenceId() {
        return occurrenceId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getCurrent() {
        return current;
    }

    public void setCurrent(Object current) {
        this.current = current;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
