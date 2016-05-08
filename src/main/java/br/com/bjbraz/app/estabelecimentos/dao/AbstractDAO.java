package br.com.bjbraz.app.estabelecimentos.dao;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;


@SuppressWarnings( { "unchecked", "hiding" })
public abstract class AbstractDAO<E, PK extends Serializable> extends GenericHibernateJpaDao<E, PK> {

	private final HashMap<String, Serializable> mapaVazio = new HashMap<String, Serializable>();
	protected static final Integer TAMANHO_MAXIMO_PESQUISA = 1000;//Integer.valueOf(Mensagem.getMensagem("1000"));

	/**
	 * Metodos que cria os parametros.
	 * 
	 * @param keys
	 * @param values
	 * @return
	 */
	protected static Map<String, Serializable> createParams(String[] keys, Serializable[] values) {
		if (keys == null || values == null || keys.length != values.length) {
			throw new InvalidParameterException();
		}
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		int sz = keys.length;
		for (int i = 0; i < sz; i++) {
			params.put(keys[i], values[i]);
		}
		return params;
	}

	/**
	 * Efetiva as busca por ID
	 * 
	 * @param id
	 * @return
	 */
	public E findById(PK id) {
		return findById(id, false);
	}

	protected <E> E findUniqueByNamedQuery(String name) throws NoResultException {
		return (E) this.findUniqueByNamedQuery(name, mapaVazio);
	}

	protected <E> E findUniqueByNamedQuery(String name, Map<String, Serializable> params) throws NoResultException {
		Query q = this.em.createNamedQuery(name);
		for (Map.Entry<String, Serializable> p : params.entrySet()) {
			if (p.getValue() instanceof Date) {
				q.setParameter(p.getKey(), (Date) p.getValue(), TemporalType.DATE);
			} else {
				q.setParameter(p.getKey(), p.getValue());
			}
		}

		E entity = null;
		entity = (E) q.getSingleResult();
		return entity;
	}

	protected <E> List<E> findByNamedQuery(String namedQuery, Map<String, Serializable> params) {
		Query q = this.em.createNamedQuery(namedQuery);
		for (Map.Entry<String, Serializable> p : params.entrySet()) {
			if (p.getValue() instanceof Date) {
				q.setParameter(p.getKey(), (Date) p.getValue(), TemporalType.DATE);
			} else {
				q.setParameter(p.getKey(), p.getValue());
			}
		}
		return (List<E>) q.getResultList();
	}

	protected <E> List<E> findByNamedQuery(String name) {
		return (List<E>) this.findByNamedQuery(name, mapaVazio);
	}

}
