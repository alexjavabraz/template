package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;

public interface EstabelecimentoSubGrupoDao {
    
    public List<EstabelecimentoSubGrupo> listarTodosDoGrupo(Integer idGrupo);
    
    public EstabelecimentoSubGrupo salvar(EstabelecimentoSubGrupo esg);

    public EstabelecimentoSubGrupo findById(Integer idSubGrupo);

	public EstabelecimentoSubGrupo listarPorNome(String idSubGrupo);

}
