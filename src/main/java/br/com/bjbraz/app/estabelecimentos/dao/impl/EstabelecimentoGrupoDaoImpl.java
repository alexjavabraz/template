package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoGrupoDao;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;

@Repository
public class EstabelecimentoGrupoDaoImpl extends AbstractDAO<EstabelecimentoGrupo, Integer> implements EstabelecimentoGrupoDao {
    
    private static final Logger log = LoggerFactory.getLogger(EstabelecimentoGrupoDaoImpl.class);

    @Override
    public List<EstabelecimentoGrupo> listarGruposDaCategoria(Integer idCategoria) {
        
        try {
            List<EstabelecimentoGrupo> itens = super.findByNamedQuery(EstabelecimentoGrupo.BUSCAR_POR_CATEGORIA,
                createParams(new String[] { "id" }, new Serializable[] { idCategoria }));

            return itens;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<EstabelecimentoGrupo>();
    }

    @Override
    public EstabelecimentoGrupo salvar(EstabelecimentoGrupo eg) {
        return super.persist(eg);
    }

}
