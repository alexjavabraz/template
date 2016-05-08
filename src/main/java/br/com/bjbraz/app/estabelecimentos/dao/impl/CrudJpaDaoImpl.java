package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.CacheMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bjbraz.app.estabelecimentos.dao.CrudJpaDao;

/**
 * Implementacao do CrudJpaDao.
 * @version $VERSION
 */
public final class CrudJpaDaoImpl implements CrudJpaDao {

    private final Logger logger = LoggerFactory.getLogger(CrudJpaDaoImpl.class);

    private EntityManager em;

    public CrudJpaDaoImpl(EntityManager em) {
        assert em != null : "EntityManager cannot be null";
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        assert this.em != null : "EntityManager has not been set on DAO before usage";
        return em;
    }

    @SuppressWarnings("unchecked")
    public <T> T findById(Class<?> entityType, Serializable id, boolean lock) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding entity using " + id);
        }

        T entity;
        if (lock) {
            entity = getEntityManager().find((Class<T>) entityType, id);
            em.lock(entity, javax.persistence.LockModeType.WRITE);
        }
        else {
            entity = getEntityManager().find((Class<T>) entityType, id);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Entity Found = " + entity);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<?> entityType, int maxResults) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding all entities of type " + entityType.getName());
        }

        List<T> results = getEntityManager().createQuery("from " + entityType.getName()).setMaxResults(maxResults)
            .getResultList();

        if (logger.isDebugEnabled()) {
            logger.debug("Entities found using no criteria for the entity " + entityType.getName());
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findByExample(Class<?> entityType, T exampleInstance, String... excludeProperty) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding by example for entity" + entityType.getName() + " using the following criteria "
                + exampleInstance + " and excluding " + excludeProperty);
        }

        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Criteria crit = ((org.hibernate.ejb.HibernateEntityManager) getEntityManager()).getSession()
            .createCriteria(entityType);
        org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(exampleInstance);

        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        crit.setCacheable(true);
        crit.setCacheMode(CacheMode.NORMAL);
        List<T> results = crit.list();

        if (logger.isDebugEnabled()) {
            logger.debug("Entities found using the following type " + entityType.getName() + " with result = "
                + results);
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findByCriteria(Class<?> entityType, org.hibernate.criterion.Criterion... criterion) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding entities by criteria for entity " + entityType.getName()
                + " using the following criteria " + criterion);
        }

        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Session session = ((org.hibernate.ejb.HibernateEntityManager) getEntityManager()).getSession();
        org.hibernate.Criteria crit = session.createCriteria(entityType);
        for (org.hibernate.criterion.Criterion c : criterion) {
            crit.add(c);
        }

        List<T> results = crit.list();

        if (logger.isDebugEnabled()) {
            logger.debug("Finding entities by criteria for entity " + entityType.getName() + " with results = "
                + results);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findByQuery(Class<?> entityType, String queryString, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding all entities of type " + entityType.getName() + " using the following query "
                + queryString + " and the following parameters " + params);
        }

        Query query = em.createQuery(queryString);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }

        return (List<T>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findByQueryWraper(QueryWrapper queryWrapper, Object... params) {
        Query query = em.createQuery(queryWrapper.getQueryString());
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                queryWrapper.setParam(i + 1, query, params[i]);
            }
        }
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.bjbraz.framework.dao.jpa.CrudJpaDao#persist(T)
     */
    public <T> T persist(T entity) {
        if (logger.isDebugEnabled()) {
            logger.debug("Saving instance " + entity);
        }

        T entityUpdated = getEntityManager().merge(entity);

        if (logger.isDebugEnabled()) {
            logger.debug("Entity saved " + entityUpdated);
        }

        return entityUpdated;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.bjbraz.framework.dao.jpa.CrudJpaDao#remove(java.lang.Class, java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public <T> void remove(Class<?> entityType, Serializable id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Removing instance with primary key " + id);
        }

        T stored = (T) getEntityManager().getReference(entityType, id);

        if (logger.isDebugEnabled()) {
            logger.debug("Removing instance " + stored);
        }

        getEntityManager().remove(stored);

        if (logger.isDebugEnabled()) {
            logger.debug("Entity " + stored + " removed");
        }
    }

    protected static interface QueryWrapper<T> {
        String getQueryString();

        void setParam(int index, Query query, Object value);
    }

}
