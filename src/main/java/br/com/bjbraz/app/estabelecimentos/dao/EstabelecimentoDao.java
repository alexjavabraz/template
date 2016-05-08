package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;

public interface EstabelecimentoDao {
    
    public Estabelecimento salvar(Estabelecimento e);

    public List<Estabelecimento> listarTodosEstabelecimentos();

    public Estabelecimento findById(Integer id);

	public Estabelecimento listarEstabelecientoPorNome(String nomeFantasia);

	public List<Estabelecimento> listarTodosEstabelecimentos(Estabelecimento filtro);

}