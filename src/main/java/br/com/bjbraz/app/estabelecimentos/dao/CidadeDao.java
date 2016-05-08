package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Cidade;

public interface CidadeDao {
	
	public List<Cidade> verCidadesPorEstado(String estados);
	
	public List<Cidade> verCidadesPorEstadoId(String idEstado);
	
	public List<Cidade> findAll();
}
