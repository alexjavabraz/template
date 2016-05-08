package br.com.bjbraz.app.estabelecimentos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bjbraz.app.estabelecimentos.dao.CidadeDao;
import br.com.bjbraz.app.estabelecimentos.dao.SimpleJDBCDao;
import br.com.bjbraz.app.estabelecimentos.entity.Cidade;
import br.com.bjbraz.app.estabelecimentos.service.DummyService;


@Service
public class DummyServiceImpl implements DummyService{
	
	@Autowired
	private CidadeDao dao;
	
	@Autowired
	private SimpleJDBCDao dao2;
	

	@Override
	public List<Cidade> listAll() {
		return dao.findAll();
	}
	
	@Override
	public List<Cidade> listAll2() {
		dao2.listarTodasCategorias();
		return null;
	}	

}
