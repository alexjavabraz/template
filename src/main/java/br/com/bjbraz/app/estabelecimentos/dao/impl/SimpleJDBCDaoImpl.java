package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.GenericJdbcDao;
import br.com.bjbraz.app.estabelecimentos.dao.SimpleJDBCDao;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.exception.ErrorMessage;
import br.com.bjbraz.app.estabelecimentos.exception.SystemException;

@Repository
public class SimpleJDBCDaoImpl extends GenericJdbcDao implements SimpleJDBCDao {

    private static final Logger log = LoggerFactory.getLogger(SimpleJDBCDaoImpl.class);

    @Autowired
    public void initDataSource(@Qualifier("dataSource1") DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<Map<String, Object>> listarTodasCategorias() {
        List<java.util.Map<String, Object>> mapaRetorno = null;

        try {

            String sql = " select * from estabelecimento_categoria where in_ativo = 1 ";
            mapaRetorno = getJdbcTemplate().queryForList(sql);

        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SystemException(ErrorMessage.UNEXPECTED_ERROR);
        }
        return mapaRetorno;
    }

    @Override
    public List<Map<String, Object>> listarTodosOsGruposDaCategoria(Integer idCategoria) {
        List<java.util.Map<String, Object>> mapaRetorno = null;

        try {

            String sql = " select * from estabelecimento_grupo where id_cat = ? and in_ativo = 1 ";
            mapaRetorno = getJdbcTemplate().queryForList(sql, idCategoria);

        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SystemException(ErrorMessage.UNEXPECTED_ERROR);
        }
        return mapaRetorno;
    }

    @Override
    public List<Map<String, Object>> listarTodosOsSubGrupos(Integer idGrupo) {
        List<java.util.Map<String, Object>> mapaRetorno = null;

        try {

            String sql = " select * from estabelecimento_sub_grupo where in_ativo = 1 and id_grupo = ? ";
            mapaRetorno = getJdbcTemplate().queryForList(sql, idGrupo);

        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SystemException(ErrorMessage.UNEXPECTED_ERROR);
        }
        return mapaRetorno;
    }
    
    public List<Estabelecimento> pesquisarEstabelecimentos(String idCategoria, String idGrupo, String idSubGrupo, String query) {
        List<Estabelecimento> aretorno = new ArrayList<Estabelecimento>();

        String sql = " select e.id,e.nome_fantasia, e.end_rua, e.end_numero, e.end_complemento, e.bairro,   "+
            " e.slogam, e.imagem1, e.telefone, e.site, e.email, e.bairro, e.facebook,e.id_destaque, e.imagem2, e.imagem3,e.imagem4,"+
            " e.cep, s.id_sg, s.nome_sub_grupo, e.dh_inclusao  "+
            " from estabelecimento e,estabelecimento_sub_grupo s  "+
            " where   "+
            " (e.nome_fantasia like ('%"+query+"%')  "+ 
            " or   "+
            " e.slogam like         ('%"+query+"%')  "+ 
            " or   "+
            " s.nome_sub_grupo like ('%"+query+"%'))  "+
            " and e.id_sub_grupo = s.id_sg  "+
            " and s.in_ativo  = 1  "+
            " order by e.id_destaque, e.dh_inclusao ";
        
//        List<java.util.Map<String, Object>> mapaRetorno = getSimpleJdbcTemplate().queryForList(sql, idGrupo);


        Connection con = getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                aretorno = preencheDTO(aretorno, rs);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return aretorno;
    }
    
    private List<Estabelecimento> preencheDTO(List<Estabelecimento> arrretorno, ResultSet rs) {
        List<Estabelecimento> aretorno = new ArrayList<Estabelecimento>();
        
        try{
            while (rs.next()) {
                Estabelecimento retorno = new Estabelecimento();
                retorno.setId(rs.getInt("id"));
                retorno.setNomeFantasia(rs.getString("nome_fantasia")); // "Vedhanta Galeteria";
                retorno.setBairro(rs.getString("bairro")); // "Granja Viana";
//                retorno.setCep() = "Cotia";
//                retorno.ehDestaque = (rs.getString("id_destaque") != null); // Boolean.TRUE;
                retorno.setEmail(rs.getString("email")); // "contato@vedhanta.com.br";
                retorno.setCep(rs.getString("cep")); // "06708-645";
                retorno.setEndRua(rs.getString("end_rua") + " ," + rs.getString("end_numero")); // "Rua Jose Félix de Oliveira, 1032";
                retorno.setEndNumero(rs.getString("end_numero")); // "1032";
                retorno.setSite(rs.getString("site")); // "http://www.restaurantevedanta.com.br/novo/";
                retorno.setFacebook(rs.getString("facebook")); // "244304455698640";
                retorno.setTelefone(rs.getString("telefone")); // "(11) 4702-2618";
                retorno.setSlogam(rs.getString("slogam")); // "Cardápio variado, iguarias por preços acessíveis e galetos irrestíveis.";
                retorno.setImagem1(rs.getBytes("imagem1"));
                
                if(rs.getBytes("imagem2") != null){
                    retorno.setImagem2(rs.getBytes("imagem2"));
                }
                
                if(rs.getBytes("imagem3") != null){
                    retorno.setImagem3(rs.getBytes("imagem3"));
                }
                
                if(rs.getBytes("imagem4") != null){
                    retorno.setImagem4(rs.getBytes("imagem4"));
                }
                
                retorno.setIdSubGrupo(rs.getInt("id_sg"));
                
                aretorno.add(retorno);
            }
        }catch(Exception e){
            
        }
        
        return aretorno;
    }


}
