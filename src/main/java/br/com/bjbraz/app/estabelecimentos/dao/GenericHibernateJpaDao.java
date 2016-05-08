package br.com.bjbraz.app.estabelecimentos.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate5.HibernateCallback;

/**
 * Dao Generico de acesso utilizando Hibernate.
 * 
 * @see GenericJpaDao
 * @version $VERSION
 */
public abstract class GenericHibernateJpaDao<T, ID extends Serializable> extends GenericJpaDao<T, ID> {

    /**
     * Retorna os resultados de uma Query utilizando um exemplo e excluindo as propriedades listadas
     * no excludeProperty.
     * 
     * @param exampleInstance Entidade de exemplo.
     * @param excludeProperty Propriedades qe serão excluidas na busca.
     * @return Lista de Instancias persistidas no Banco.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByExample(T exampleInstance, String... excludeProperty) {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Criteria crit = getHibernateSession().createCriteria(getEntityBeanType());
        org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(exampleInstance);
        return fillInExclusions(crit, example, excludeProperty).list();
    }

    /**
     * Retorna os resultados de uma Query utilizando Criteria.
     * 
     * @param criterion Criteria da query.
     * @return Lista de Instancias persistidas no Banco.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(org.hibernate.criterion.Criterion... criterion) {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Session session = getHibernateSession();
        org.hibernate.Criteria crit = session.createCriteria(getEntityBeanType());
        return fillInCriterions(crit, criterion).list();
    }

    /**
     * Retorna uma instancia utilizando uma criteria.
     * 
     * @param criterion Criteria da query.
     * @return Instancia persistida no Banco.
     */
    @SuppressWarnings("unchecked")
    protected T findOneByCriteria(Criterion... criterion) {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Session session = getHibernateSession();
        org.hibernate.Criteria crit = session.createCriteria(getEntityBeanType());
        return (T) fillInCriterions(crit, criterion).uniqueResult();
    }

    /**
     * Retorona uma instancia utilizando um example e excluindo as propriedades listadas
     * 
     * @param exampleInstance Entidade de exemplo.
     * @param excludeProperty Propriedades qe serão excluidas na busca.
     * @return Instancia retornada do Banco.
     */
    @SuppressWarnings("unchecked")
    protected T findOneByExample(T exampleInstance, String... excludeProperty) {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Criteria crit = getHibernateSession().createCriteria(getEntityBeanType());
        org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(exampleInstance);
        return (T) fillInExclusions(crit, example, excludeProperty).uniqueResult();
    }

    /**
     * Uso interno.
     * 
     * @param criteria
     * @param _criteria
     * @return
     */
    protected Criteria fillInCriterions(Criteria criteria, Criterion... _criteria) {
        for (org.hibernate.criterion.Criterion c : _criteria) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * Uso interno. Adiciona os exclude properties a uma criteria.
     * @param criteria Criteria da query.
     * @param example Example que será usado.
     * @param excludedProperties propriedades que serao excluidas.
     * @return Criteria com as exclusions.
     */
    protected Criteria fillInExclusions(Criteria criteria, Example example, String... excludedProperties) {
        for (String exclude : excludedProperties) {
            example.excludeProperty(exclude);
        }
        criteria.add(example);
        return criteria;
    }

    /**
     * Retorna uma Lista de Resultados a partir de um HibernateCallback.
     * 
     * @param <R> Tipo de Retorno.
     * @param callback HibernateCallback
     * @return Lista de Resultados do HibernateCallback.
     */
    @SuppressWarnings("unchecked")
    protected <R> List<R> findUsingCallback(HibernateCallback callback) {
        try {
            return (List<R>) callback.doInHibernate(getHibernateSession());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Retorna uma instancia a partir de um HibernateCallback.
     * 
     * @param <R> Tipo de Retorno.
     * @param callback HibernateCallback
     * @return Instancia persistida.
     */
//    @SuppressWarnings("unchecked")
//    protected <R> R findOneUsingCallback(HibernateCallback callback) {
//        try {
//            return (R) callback.doInHibernate(getHibernateSession());
//        }
//        catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    /**
     * Session do Hibernate.
     * 
     * @return Instancia da session.
     */
    protected Session getHibernateSession() {
        return (Session) this.em.getDelegate();
    }

}
