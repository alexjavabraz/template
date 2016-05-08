package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoSubGrupoDao;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;

@Repository
public class EstabelecimentoSubGrupoDaoImpl extends AbstractDAO<EstabelecimentoSubGrupo, Integer> implements EstabelecimentoSubGrupoDao {

    private static final Logger log = LoggerFactory.getLogger(EstabelecimentoSubGrupoDaoImpl.class);
    
    @Override
    public List<EstabelecimentoSubGrupo> listarTodosDoGrupo(Integer idGrupo) {
        try {
            List<EstabelecimentoSubGrupo> itens = super.findByNamedQuery(EstabelecimentoSubGrupo.BUSCAR_POR_GRUPO,
                createParams(new String[] { "id" }, new Serializable[] { idGrupo }));

            return itens;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<EstabelecimentoSubGrupo>();
    }

    @Override
    public EstabelecimentoSubGrupo salvar(EstabelecimentoSubGrupo esg) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public EstabelecimentoSubGrupo listarPorNome(String idSubGrupo) {
		 try {
	            List<EstabelecimentoSubGrupo> itens = super.findByNamedQuery(EstabelecimentoSubGrupo.BUSCAR_POR_DESCRICAO,
	                createParams(new String[] { "descricao" }, new Serializable[] { idSubGrupo }));

	            if(itens.size() > 0){
	            	return itens.get(0);
	            }
	            
	        }
	        catch (Exception e) {
	            log.error(e.getMessage(), e);
	        }
	        return null;
	}

}
