package br.com.bjbraz.app.estabelecimentos.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface do DAO CRUD.
 * @version $VERSION
 */
public interface CrudJpaDao {

    /**
     * Retorna uma instancia persistida no Banco.
     * 
     * @param <T> Tipo da instancia.
     * @param entityType Tipo da Entidade.
     * @param id Id da instancia.
     * @param lock Se true efetua o Lock e false nao efetua o lock.
     * @return Retorna a instancia da Entidade.
     */
    <T> T findById(Class<?> entityType, Serializable id, boolean lock);

    /**
     * Retorna todas as instancias persistidas no Banco.
     * 
     * @param <T> Tipo da instancia.
     * @param entityType tipo da Entidade.
     * @param maxResults Quantidade máxima de resultados.
     * @return Lista de entidades.
     */
    <T> List<T> findAll(Class<?> entityType, int maxResults);

    /**
     * Persiste uma entidade no Banco.
     * 
     * @param <T> Tipo da entidade.
     * @param entity Entidade a ser persistida.
     * @return Instancia da Entidade persistida.
     */
    <T> T persist(T entity);

    /**
     * Remove uma entidade do Banco.
     * 
     * @param <T> Tipo da entidade.
     * @param entityType Tipo da Entidade.
     * @param id id da Entidade.
     */
    <T> void remove(Class<?> entityType, Serializable id);

}
