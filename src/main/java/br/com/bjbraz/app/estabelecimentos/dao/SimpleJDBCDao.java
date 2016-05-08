package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;
import java.util.Map;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;


public interface SimpleJDBCDao {

    public List<Map<String, Object>> listarTodasCategorias();
    
    public List<Map<String, Object>> listarTodosOsGruposDaCategoria(Integer idCategoria);
    
    public List<Map<String, Object>> listarTodosOsSubGrupos(Integer idGrupo);
    
    public List<Estabelecimento> pesquisarEstabelecimentos(String idCategoria, String idGrupo, String idSubGrupo, String query);

}