package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Cidade;

public class TesteHibernate extends GenericHibernateJpaDao<Cidade, Integer>{
	
	public List<Cidade> findAll(){
		return super.findAll(300);
	}

}
