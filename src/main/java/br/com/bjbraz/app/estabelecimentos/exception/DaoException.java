package br.com.bjbraz.app.estabelecimentos.exception;

/**
 * @author Alex Simas Braz
 * @since 27/02/2016
 */
public class DaoException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5288401033749506368L;
    private String detalhes;

    public DaoException(String s) {
        super(s);
    }
    
    public DaoException(){}

    public DaoException(String string, String detalhes) {
        this(string);
        this.detalhes = detalhes;
    }

    public String getDetalhes() {
        return detalhes;
    }

}