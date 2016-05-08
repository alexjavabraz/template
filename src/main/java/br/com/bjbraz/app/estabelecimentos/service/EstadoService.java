package br.com.bjbraz.app.estabelecimentos.service;

import java.util.List;

import br.com.bjbraz.app.estabelecimentos.entity.Cidade;
import br.com.bjbraz.app.estabelecimentos.entity.Estado;

public interface EstadoService {

    public List<Estado> verEstados();

    public List<Estado> mostrarEstados();

    public List<Cidade> buscarPorEstado(String estados);

    public List<Cidade> buscarPorEstadoId(String idEstado);

}
