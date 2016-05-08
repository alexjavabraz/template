package br.com.bjbraz.app.estabelecimentos.exception;

/**
 * Excecao para ser usada em erros de negocio, e que vao ser tratados pela aplicacao.
 * A ocorrencia de uma BusinessException deve ser considerada como parte do fluxo normal da aplicacao,
 * nao como um bug nem como uma condicao de erro que precise de alguma intervencao em producao.
 * <p/>
 * Exemplo: SaldoNegativoException
 *
 * @version $VERSION
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 8102653930898590031L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
