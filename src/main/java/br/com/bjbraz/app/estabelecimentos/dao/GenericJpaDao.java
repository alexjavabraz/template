package br.com.bjbraz.app.estabelecimentos.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Crud Jpa Dao Generico.
 * 
 * @param <T>
 *            Tipo da Entidade.
 * @param <ID>
 *            Tipo do ID da entidade.
 * @version $VERSION
 */
public abstract class GenericJpaDao<T, ID extends Serializable> {

    private Class<T> entityBeanType;

    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public GenericJpaDao() {
	Type genericSuperclass = getClass().getGenericSuperclass();
	ParameterizedType type = (ParameterizedType) genericSuperclass;
	this.entityBeanType = (Class<T>) type.getActualTypeArguments()[0];
    }

    /**
     * Entity Manager
     * 
     * @param em
     *            Entity Manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
	this.em = em;
    }

    protected EntityManager getEntityManager() {
    	
    if(em == null){
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "manager1" );
    	em = entityManagerFactory.createEntityManager();
    }
    	
    	
	if (em == null) {
	    throw new IllegalStateException(
		    "EntityManager has not been set on DAO before usage");
	}
	return em;
    }

    protected Class<T> getEntityBeanType() {
	return entityBeanType;
    }

    /**
     * Retorna uma instancia da Entidade.
     * 
     * @param id
     *            Id da entidade.
     * @param lock
     *            Se true efetua o Lock da entidade, se false nao efetua o lock.
     */
    public T findById(ID id, boolean lock) {
	T entity;
	if (lock) {
	    entity = getEntityManager().find(getEntityBeanType(), id);
	    em.lock(entity, javax.persistence.LockModeType.WRITE);
	} else {
	    entity = getEntityManager().find(getEntityBeanType(), id);
	}
	return entity;
    }

    /**
     * Retorna uma Lista de Entidades.
     * 
     * @param maxResults
     *            Quantidade maxima de resultados.
     * @return Lista de Entidades existentes.
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(int maxResults) {
	return getEntityManager().createQuery(
		"from " + getEntityBeanType().getName()).setMaxResults(
		maxResults).getResultList();
    }

    /**
     * Utiliza uma query JPA para Retornar uma Lista de Entidades.
     * 
     * @param queryString
     *            Query JPA
     * @param params
     *            Parametros da query JPA
     * @return Lista de Entidades retornadas pela query JPA.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByQuery(String queryString, Object... params) {
	Query query = em.createQuery(queryString);
	return fillInQueryParameters(query, params).getResultList();
    }

    /**
     * Utiliza uma query JPA para Retornar uma instancia de uma Entidade.
     * 
     * @param queryString
     *            Query JPA
     * @param params
     *            Parametros da query JPA
     * @return Instancia da Entidade retornada pela query JPA.
     */
    @SuppressWarnings("unchecked")
    protected T findOneByQuery(String queryString, Object... params) {
	Query query = em.createQuery(queryString);
	return (T) fillInQueryParameters(query, params).getSingleResult();
    }

    /**
     * Utiliza uma QueryWrapper para configurar os parametros em uma Query.
     * 
     * @param queryWrapper
     *            QueryWrapper utilizado como callback para configurar uma
     *            query.
     * @param params
     *            Parametros da Query JPA.
     * @return Retorna uma lista de Entidades retornadas pela query JPA.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByQueryWraper(QueryWrapper queryWrapper,
	    Object... params) {
	Query query = em.createQuery(queryWrapper.getQueryString());
	
	if (params != null) {
	    for (int i = 0; i < params.length; i++) {
		queryWrapper.setParam(i + 1, query, params[i]);
	    }
	}
	return query.getResultList();
    }

    /**
     * Persiste uma entidade no Banco.
     * 
     * @param entity
     *            Instancia da Entidade.
     * @return Retorna uma instancia da entidade persistida.
     */
    public T persist(T entity) {
	return getEntityManager().merge(entity);
    }

    /**
     * Remove uma Entidade do Banco.
     * 
     * @param entity
     *            Instancia a ser removida do Banco.
     */
    public void remove(T entity) {
	getEntityManager().remove(entity);
    }

    /**
     * Uso interno. Adiciona os parametros a uma query.
     * 
     * @param query
     *            Instancia do tipo Query.
     * @param params
     *            Parametros da query.
     * @return Query
     */
    protected Query fillInQueryParameters(Query query, Object... params) {
	if (params != null) {
	    for (int i = 0; i < params.length; i++) {
		query.setParameter(i + 1, params[i]);
	    }
	}
	return query;
    }

    /**
     * Classe usada para configurar parametros a uma query.
     */
    public static interface QueryWrapper {
	/**
	 * @return Query JPA.
	 */
	public String getQueryString();

	/**
	 * Este metodo e chamado no momento em que se estiver setando os
	 * valores. Da a oportunidade de se configurar os parametros da maneira
	 * desejada.
	 * 
	 * @param index
	 *            Index do parametro iniciando em 0.
	 * @param query
	 *            Instancia da Query.
	 * @param value
	 *            Valor do parametro a ser setado.
	 */
	public void setParam(int index, Query query, Object value);
    }

}
