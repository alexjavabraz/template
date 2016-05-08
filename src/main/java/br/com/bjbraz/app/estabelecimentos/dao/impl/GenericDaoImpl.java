package br.com.bjbraz.app.estabelecimentos.dao.impl;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.bjbraz.app.estabelecimentos.dao.GenericDao;
import br.com.bjbraz.app.estabelecimentos.dao.GenericHibernateJpaDao;
import br.com.bjbraz.app.estabelecimentos.exception.DaoException;
import br.com.bjbraz.app.estabelecimentos.util.ReflectionUtil;

@Repository
public abstract class GenericDaoImpl<T, Y extends Serializable> extends GenericHibernateJpaDao<T, Y> implements
    GenericDao<T> {


    private final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

    private static final String BUSCA_ULTIMO_ITEM = "select o from #1 o where o.#2=(select max(#2) from #1)";

    /**
     * Retorna o valor do ID do objeto <code>item</code>
     * 
     * @param item
     * @return
     */
    protected abstract Y getId(T item);

    /**
     * Retorna o nome da propriedade, da entidade, que contém a anotacao DefaultListOrder
     * 
     * @return
     */
    protected String getOrderPropertyName() {
        return ReflectionUtil.getOrderPropertyName(getEntityBeanType());
    }

    /**
     *O banco de dados nao tem autoincrement definido para os PK das tabelas, nem sequence.
     * Esse metodo busca o max PK da tabela, incrementa uma unidade e preenche no novo objeto.
     * Metodo generico que eh usado em todos objetos do sistema.
     * @param item Objeto que tera o ID setado
     */
    protected void setIdItem(T item) {
        T last = this.findLast();
        String id = ReflectionUtil.getPropertyNameForAnnotation(getEntityBeanType(), Id.class);
        if (id == null) {
            String embeddedId = ReflectionUtil.getPropertyNameForAnnotation(getEntityBeanType(), EmbeddedId.class);
            if (embeddedId != null)
                return;
            throw new DaoException("Nao foi encontrada anotacao +" + Id.class.getName() + " para a classe "
                + getEntityBeanType().getName());
        }
        ReflectionUtil ru = ReflectionUtil.getInstance();
        if (last != null) {
            ru.setNextIdValue(item, last, id);
        }
        else {
            ru.setProperty(item, id, 0L);
        }
    }


    public void atualizar(T item) {
        this.persist(item);
    }


    public T buscar(T item) {
        Y id = getId(item);
        if (id == null)
            throw new DaoException("ipm.mensagem.cadastro.item.idIsNull", item.getClass().toString());
        return findById(id, false);

    }

    public T buscarReturningNullIdEmpty(T item) {
        if (getId(item) == null)
            return null;
        return buscar(item);
    }

    public void excluir(T item) {
        this.remove(item);
    }

    public void incluir(T item) {
        setIdItem(item);
        this.persist(item);
        logger.info("incluido item {}", item.getClass().getName());
    }

    /**
     * Busca o objeto que tem o max(id) no banco de dados.
     * 
     * @return
     */
    protected T findLast() {
        String entityName = getEntityBeanType().getSimpleName();
        String id = ReflectionUtil.getPropertyNameForAnnotation(getEntityBeanType(), Id.class);
        if (id == null) {
            throw new RuntimeException("Nao foi encontrada anotacao +" + Id.class.getName() + " para a classe "
                + entityName);
        }
        String query = BUSCA_ULTIMO_ITEM.replaceAll("#1", entityName).replaceAll("#2", id);

        return this.findOneByQuery(query);
    }

    @Transactional(readOnly = true)
    public List<T> listar() {
        HibernateCallback hibernateCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {

                Class<T> entity = getEntityBeanType();
                logger.debug("criando criteria para a classe {}", entity.getSimpleName());

                Criteria c = session.createCriteria(entity);
                
//                if(entity.equals(Projeto.class)){
//                        c = addOrderForCriteria(c, "id");
//                }else if(entity.equals(Subeixo.class)){
//                        c = addOrderForCriteria(c, "nome");
//                }else{
                        c = addOrderForCriteria(c, "numeroSequencial");
//                }
                
                return c.list();
            }
        };
        return this.findUsingCallback(hibernateCallback);
    }

    @Transactional(readOnly = true)
    public List<T> listarForName(final String description) {
        HibernateCallback hibernateCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {

                Class<T> entity = getEntityBeanType();
                logger.debug("criando criteria para a classe {}", entity.getSimpleName());

                Criteria c = session.createCriteria(entity);
                c = addOrderForCriteria(c, description);
                
                return c.list();
            }
        };
        return this.findUsingCallback(hibernateCallback);
    }

    public List<T> listar(T item) {
        throw new RuntimeException("metodo sobrescrito nao implementado na subclasse");
    }

    public List<T> listarTodos() {
        String entityName = getEntityBeanType().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("select i from ").append(entityName).append(" i");

        return this.findByQuery(sb.toString());
    }

    /**
     * Retorna um criteria com LIKE se o <code>valorPropriedade</code> estiver preenchido.
     * 
     * @param valorPropriedade
     * @param nomePropriedade
     * @return
     */
    protected final Criterion getCriteriaLike(String valorPropriedade, String nomePropriedade) {

        if (!StringUtils.isEmpty(valorPropriedade)) {
            return Restrictions.ilike(nomePropriedade, "%" + valorPropriedade + "%");
        }
        return null;
    }

    /**
     * Retorna um criteria com AND das duas criterias se forem != null
     * 
     * @param c1 criteria 1
     * @param c2 criteria 2
     * @return
     */
    protected final Criterion getCriteriaConjunction(Criterion c1, Criterion c2) {
        if (c1 == null && c2 == null) {
            return null;
        }
        if (c1 == null) {
            return c2;
        }
        if (c2 == null) {
            return c1;
        }
        return Restrictions.and(c1, c2);
    }

    /**
     * Busca o campo marcado com default order. Adiciona na criteria
     * 
     * @param c
     * @return
     */
    protected Criteria addOrderForCriteria(Criteria c, String numeroSequencia) {
        String orderProperty = numeroSequencia;

        if (orderProperty != null) {
            logger.debug("DefaultListOrder para a classe {} propriedade {}", getEntityBeanType().getSimpleName(), orderProperty);
            c = c.addOrder(Order.asc(orderProperty));
        }
        return c;
    }


}

