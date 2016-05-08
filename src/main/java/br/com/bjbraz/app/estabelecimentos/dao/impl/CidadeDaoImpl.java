package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.CidadeDao;
import br.com.bjbraz.app.estabelecimentos.entity.Cidade;

@Repository
public class CidadeDaoImpl extends AbstractDAO<Cidade, Integer> implements CidadeDao {

    private static final Logger log = LoggerFactory.getLogger(CidadeDaoImpl.class);

    /** RETORNA A LISTA DE ESTADOS ARMAZENADOS NO BANCO */
    @Override
    public List<Cidade> verCidadesPorEstado(String nomeEstado) {
        try {
            return super.findByNamedQuery(Cidade.BUSCAR_POR_NOME_ESTADO,
                createParams(new String[] { "nomeEstado" }, new Serializable[] { nomeEstado }));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cidade> verCidadesPorEstadoId(String idEstado) {
        try {
            return super.findByNamedQuery(Cidade.BUSCAR_POR_ID_ESTADO,
                createParams(new String[] { "idEstado" }, new Serializable[] { new Integer(idEstado) }));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    
    public List<Cidade> findAll(){
    	return super.findAll(100);
    }
}
