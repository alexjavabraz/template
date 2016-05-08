package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoDao;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.util.UtilFunction;

@Repository
public class EstabelecimentoDaoImpl extends AbstractDAO<Estabelecimento, Integer> implements EstabelecimentoDao {
	
	private static final Logger log = LoggerFactory.getLogger(EstabelecimentoDaoImpl.class);

    @Override
    public Estabelecimento salvar(Estabelecimento e) {
        return super.persist(e);
    }

    @Override
    public List<Estabelecimento> listarTodosEstabelecimentos() {
        return super.findAll(TAMANHO_MAXIMO_PESQUISA);
    }

	@Override
	public Estabelecimento listarEstabelecientoPorNome(String nomeFantasia) {
		try {
            List<Estabelecimento> itens = super.findByNamedQuery(Estabelecimento.BUSCAR_POR_DESCRICAO,
                createParams(new String[] { "descricao" }, new Serializable[] { nomeFantasia }));

            if(itens.size() > 0){
            	return itens.get(0);
            }
            
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
	}

	@Override
	public List<Estabelecimento> listarTodosEstabelecimentos(Estabelecimento filtro) {
		
		List<Criterion> crits = new ArrayList<Criterion>();
		
		if(filtro.getDataInclusaoInicio() != null && filtro.getDataInclusaoFim() != null){
			crits.add(Restrictions.between("dhInclusao", filtro.getDataInclusaoInicio(), filtro.getDataInclusaoInicio()));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getNomeFantasia()) ){
			crits.add(Restrictions.or(
				
				Restrictions.like("nomeFantasia", "%"+filtro.getNomeFantasia()+"%"), 
				
				Restrictions.like("razaoSocial", "%"+filtro.getNomeFantasia()+"%")
				));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getEmail())){
			crits.add(Restrictions.eq("email", filtro.getEmail()));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getSite())){
			crits.add(Restrictions.eq("site", filtro.getSite()));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getBairro())){
			crits.add(Restrictions.eq("bairro", filtro.getBairro()));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getCep() )){
			crits.add(Restrictions.eq("cep", filtro.getCep()));
		}
		
		if(UtilFunction.isNotBlanckOrNull(filtro.getEndRua())){
			crits.add(Restrictions.eq("endRua", filtro.getEndRua()));
		}
		
		if(crits.size() > 0){
			return  super.findByCriteria((Criterion[]) crits.toArray(new Criterion[0]));
		}
		
		return super.findAll(1000);
	}

}
