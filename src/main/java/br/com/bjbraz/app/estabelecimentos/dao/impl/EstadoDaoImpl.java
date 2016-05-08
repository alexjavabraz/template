package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstadoDao;
import br.com.bjbraz.app.estabelecimentos.entity.Estado;

@Repository
public class EstadoDaoImpl extends AbstractDAO<Estado, Integer> implements EstadoDao{

	@Override
	public List<Estado> verEstados() {
		return super.findAll(50);
	}

	@Override
	public List<Estado> mostrarEstados() {
		return super.findByNamedQuery(Estado.BUSCAR_ESTADOS);
	}

}
