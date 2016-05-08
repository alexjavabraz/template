package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;

/**
 * <p>
 * Backing Bean Basico que expoe metodos utilitarios basicos aos backing beans que estenderem.
 * </p>
 * <p/>Todos os Backing Beans deverao estender esta classe diretamente ou indiretamente.<p/>
 * <p>
 * Existem tambem outras implementacoes que estendem esta classe exemplo:
 * </p>
 * <ul>
 * <li><code>ReportBBean</code></li>
 * <li><code>CrudServiceBBean</code></li>
 * <li><code>ReportCrudServiceBBean</code></li>
 * </ul>
 * 
 * @see CrudServiceBBean
 * @see ReportBBean
 * @see ReportCrudServiceBBean
 * @version $VERSION
 */
public abstract class BasicBBean implements BeanNameAware, Serializable {

    private static final long serialVersionUID = -7451189428350976222L;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected static final String SUCCESS = "Success";
    protected static final String ERROR = "Error";
    public transient boolean fromSerialization;
    private String beanName;
    
	@Inject
	@ManagedProperty("#{themeService}")
	protected ThemeService service;

	@Inject
	protected CadastroEstabelecimentoService servicec;

    /**
     * Este metodo cria um novo value bind para o backing bean.
     * 
     * @param <T> Tipo do Backing Bean.
     * @param managedBeanName nome do Backing Bean.
     * @return Retorna a instancia do Backing Bean.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getBind(String managedBeanName) {
        return (T) getFacesContext().getApplication().createValueBinding(managedBeanName).getValue(getFacesContext());
    }

    /**
     * Adiciona uma mensagem global ao FacesContext.
     * 
     * @param severity Severidade da mensagem.
     * @param summary Sumario da mensagem.
     * @param details Detalhes da mensagem.
     */
    protected void addMessage(FacesMessage.Severity severity, String summary, String details) {
        getFacesContext().addMessage(summary, new FacesMessage(severity, summary, details));
    }

    /**
     * Retorna uma instancia do <code>FacesContext</code>.
     * 
     * @return Instancia do <code>FacesContext</code>.
     */
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Retorna o <code>HttpServletRequest</code> corrente.
     * 
     * @return Instancia do <code>HttpServletRequest</code>.
     */
    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Retorna o <code>HttpServletResponse</code> corrente.
     * 
     * @return Instancia do <code>HttpServletResponse</code>.
     */
    protected HttpServletResponse getHttpResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    /**
     * Retorna uma instancia <code>HttpSession</code>.
     * 
     * @param create Caso seja true cria uma nova caso nao exista uma, caso seja false nao cria uma
     *            nova caso nao exista.
     * @return Uma instancia <code>HttoSession</code> ou <code>null</code>.
     */
    protected HttpSession getHttpSession(boolean create) {
        return (HttpSession) getFacesContext().getExternalContext().getSession(create);
    }

    /**
     * Retorna um atributo do scopo Request.
     * 
     * @param <T> Tipo do Atributo.
     * @param attributeName Nome do Atributo.
     * @return A instancia do atributo ou <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(String attributeName) {
        return (T) getHttpRequest().getAttribute(attributeName);
    }

    /**
     * Retorna um parametro do escopo Request.
     * 
     * @param paramName Nome do parametro.
     * @return Valor do parametro.
     */
    protected String getRequestParameter(String paramName) {
        return getHttpRequest().getParameter(paramName);
    }

    /**
     * Retorna um atributo do scopo Session.
     * 
     * @param <T> Tipo do Atributo.
     * @param attributeName Nome do Atributo.
     * @return A instancia do atributo ou <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getSessionAttribute(String attributeName) {
        return (T) getHttpSession(true).getAttribute(attributeName);
    }

    /**
     * Adiciona um atributo a Session.
     * 
     * @param attributeName Nome do Atributo.
     * @param attributeValue Valor do Atributo.
     */
    protected void setSessionAttribute(String attributeName, Object attributeValue) {
        getHttpSession(true).setAttribute(attributeName, attributeValue);
    }

    /**
     * Retorna um atributo do scopo faces.
     * 
     * @param <T> Tipo do Atributo.
     * @param event ActionEvent do componente.
     * @param attName Nome do Atributo.
     * @return Valor do atributo.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(ActionEvent event, String attName) {
        return (T) event.getComponent().getAttributes().get(attName);
    }

    public void setBeanName(String name) {
        beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        fromSerialization = true;
    }

}
