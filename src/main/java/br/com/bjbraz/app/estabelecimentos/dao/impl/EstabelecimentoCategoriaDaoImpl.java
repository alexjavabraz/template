package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoCategoriaDao;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;

@Repository
public class EstabelecimentoCategoriaDaoImpl extends AbstractDAO<EstabelecimentoCategoria, Integer> implements EstabelecimentoCategoriaDao {

    @Override
    public List<EstabelecimentoCategoria> listarTodasCategorias() {
        return super.findAll(50);
    }

}
