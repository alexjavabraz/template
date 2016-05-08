package br.com.bjbraz.app.estabelecimentos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bjbraz.app.estabelecimentos.dao.CidadeDao;
import br.com.bjbraz.app.estabelecimentos.dao.EstadoDao;
import br.com.bjbraz.app.estabelecimentos.entity.Cidade;
import br.com.bjbraz.app.estabelecimentos.entity.Estado;
import br.com.bjbraz.app.estabelecimentos.service.EstadoService;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private CidadeDao cidadeDao;

    @Override
    public List<Estado> verEstados() {
        return estadoDao.verEstados();
    }

    @Override
    public List<Estado> mostrarEstados() {
        return estadoDao.mostrarEstados();
    }

    @Override
    public List<Cidade> buscarPorEstado(String estados) {
        return cidadeDao.verCidadesPorEstado(estados);
    }
    
    @Override
    public List<Cidade> buscarPorEstadoId(String idEstado) {
        return cidadeDao.verCidadesPorEstadoId(idEstado);
    }

}
