package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Cidade;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;

public interface CategoriaDao {
	
	public List<EstabelecimentoCategoria> verCidadesPorEstadoId(String idEstado);
	
	public List<Cidade> findAll();
}
