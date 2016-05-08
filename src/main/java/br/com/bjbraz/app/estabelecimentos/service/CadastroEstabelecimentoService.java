package br.com.bjbraz.app.estabelecimentos.service;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;

public interface CadastroEstabelecimentoService {
    
    public List<EstabelecimentoCategoria> listarTodasCategorias();
    
    public List<EstabelecimentoGrupo> listarGruposPorCategoria(Integer idCategoria);
    
    public List<EstabelecimentoSubGrupo> listarSubGrupoPorGrupo(Integer idGrupo);
    
    public EstabelecimentoGrupo salvarGrupo(EstabelecimentoGrupo grupo);
    
    public EstabelecimentoSubGrupo salvarGrupo(EstabelecimentoSubGrupo sgrupo);
    
    public Estabelecimento salvarEstabelecimento(Estabelecimento e);

    public List<Estabelecimento> listarTodosEstabelecimentos();

    public List<Estabelecimento> buscarEstabelecimentos(String categoria, String grupo, String sgrupo, String nome);

    public Estabelecimento buscarPorId(String id);

	public EstabelecimentoSubGrupo listarSubGrupoPorNome(String nomeDoSubGrupo);

	public Estabelecimento listarEstabelecimentoPorNome(String nomeFantasia);

	public List<Estabelecimento> listarTodosEstabelecimentos(Estabelecimento filtro);
    
}