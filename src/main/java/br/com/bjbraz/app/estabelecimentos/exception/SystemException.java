package br.com.bjbraz.app.estabelecimentos.exception;

import br.com.bjbraz.app.estabelecimentos.config.aop.FaultException;

/**
 * Excecao usada para sinalizar situacoes fora do curso esperado da aplicacao e que nao sao tratadas explicitamente.
 * Isso inclui, por exemplo:
 * <ul>
 * <li>Falhas do sistema ou de dependentes
 * <li>Falhas de comunicacao ou acesso a recursos
 * <li>Uso indevido de classes ou metodos pelo programador
 * <li>Bugs do sistema
 * </ul>
 * A ocorrencia de uma SystemException deve ser considerada nao normal,
 * e para sua correção normalmente e necessário uma intervenção externa.
 * 
 * @version $VERSION
 */
public class SystemException extends FaultException {

    private static final long serialVersionUID = -2241348082983579086L;
    private int errorCode;

    public SystemException(ErrorMessage message, Object... messageArgs) {
        super(createMessage(message, messageArgs), null);
        errorCode = message.getCode();
    }

    public SystemException(Throwable cause, ErrorMessage message, Object... messageArgs) {
        super(createMessage(message, messageArgs), cause);
        errorCode = message.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

    private static String createMessage(ErrorMessage message, Object... messageArgs) {
        return message.formatMesssage(messageArgs);
    }

}
