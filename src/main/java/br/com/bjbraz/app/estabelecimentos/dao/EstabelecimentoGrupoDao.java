package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;

public interface EstabelecimentoGrupoDao {
    
    public List<EstabelecimentoGrupo> listarGruposDaCategoria(Integer idCategoria);
    
    public EstabelecimentoGrupo salvar(EstabelecimentoGrupo eg);

}
