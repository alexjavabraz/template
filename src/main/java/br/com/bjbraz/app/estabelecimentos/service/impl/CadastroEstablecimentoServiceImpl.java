package br.com.bjbraz.app.estabelecimentos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoCategoriaDao;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoDao;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoGrupoDao;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoSubGrupoDao;
import br.com.bjbraz.app.estabelecimentos.dao.SimpleJDBCDao;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;
import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;

@Service
public class CadastroEstablecimentoServiceImpl implements CadastroEstabelecimentoService{
    
    @Autowired
    private EstabelecimentoCategoriaDao categoriaDao;
    
    @Autowired
    private EstabelecimentoGrupoDao grupoDao;
    
    @Autowired
    private EstabelecimentoSubGrupoDao subGrupoDao;
    
    @Autowired
    private EstabelecimentoDao estabelecimentoDao;
    
    @Autowired
    private SimpleJDBCDao simpleDao;

    @Override
    public List<EstabelecimentoCategoria> listarTodasCategorias() {
        return categoriaDao.listarTodasCategorias();
    }

    @Override
    public List<EstabelecimentoGrupo> listarGruposPorCategoria(Integer idCategoria) {
        return grupoDao.listarGruposDaCategoria(idCategoria);
    }

    @Override
    public List<EstabelecimentoSubGrupo> listarSubGrupoPorGrupo(Integer idGrupo) {
        return subGrupoDao.listarTodosDoGrupo(idGrupo);
    }

    @Override
    @Transactional
    public EstabelecimentoGrupo salvarGrupo(EstabelecimentoGrupo grupo) {
        return grupoDao.salvar(grupo);
    }

    @Override
    @Transactional
    public EstabelecimentoSubGrupo salvarGrupo(EstabelecimentoSubGrupo sgrupo) {
        return subGrupoDao.salvar(sgrupo);
    }

    @Override
    @Transactional
    public Estabelecimento salvarEstabelecimento(Estabelecimento e) {
    	e.setIdEstado(25);
    	e.setIdCidade(4845);
        return estabelecimentoDao.salvar(e);
    }

    @Override
    public List<Estabelecimento> listarTodosEstabelecimentos() {
        return estabelecimentoDao.listarTodosEstabelecimentos();
    }

    @Override
    public List<Estabelecimento> buscarEstabelecimentos(String categoria, String grupo, String sgrupo, String nome) {
        return simpleDao.pesquisarEstabelecimentos(categoria, grupo, sgrupo, nome);
    }

    @Override
    public Estabelecimento buscarPorId(String id) {
        Integer aid = Integer.parseInt(id);
        Estabelecimento e = estabelecimentoDao.findById(aid);
        
        try{
            EstabelecimentoSubGrupo sgrupo     = subGrupoDao.findById(e.getIdSubGrupo());
            EstabelecimentoGrupo grupo         = sgrupo.getEstabelecimentoGrupo();
            EstabelecimentoCategoria categoria = grupo.getEstabelecimentoCategoria();
            
            e.setCategoria(categoria);
            e.setGrupo(grupo);
            e.setSgrupo(sgrupo);
        }catch(Exception ex){}
        
        return e;
    }

	@Override
	public EstabelecimentoSubGrupo listarSubGrupoPorNome(String nomeDoSubGrupo) {
		return subGrupoDao.listarPorNome(nomeDoSubGrupo);
	}

	@Override
	public Estabelecimento listarEstabelecimentoPorNome(String nomeFantasia) {
		return estabelecimentoDao.listarEstabelecientoPorNome(nomeFantasia);
	}

	@Transactional
	@Override
	public List<Estabelecimento> listarTodosEstabelecimentos(Estabelecimento filtro) {
		return estabelecimentoDao.listarTodosEstabelecimentos(filtro);
	}

}
