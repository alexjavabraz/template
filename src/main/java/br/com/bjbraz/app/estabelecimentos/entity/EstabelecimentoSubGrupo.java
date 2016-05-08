package br.com.bjbraz.app.estabelecimentos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the estabelecimento_sub_grupo database table.
 * 
 */

@NamedQueries({ 
	
	@NamedQuery(name = EstabelecimentoSubGrupo.BUSCAR_POR_GRUPO, query = "select c from EstabelecimentoSubGrupo c where c.estabelecimentoGrupo.idGrupo = :id  "),
	@NamedQuery(name = EstabelecimentoSubGrupo.BUSCAR_POR_DESCRICAO, query = "select e from EstabelecimentoSubGrupo e where e.nomeSubGrupo = :descricao  ")

})
@Entity
@Table(name = "estabelecimento_sub_grupo")
public class EstabelecimentoSubGrupo implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_GRUPO = "EstabelecimentoSubGrupo.BUSCAR_POR_GRUPO";
    public static final String BUSCAR_POR_DESCRICAO = "EstabelecimentoSubGrupo.BUSCAR_POR_DESCRICAO";

    @Id
    @Column(name = "id_sg")
    private Integer idSg;

    @Column(name = "in_ativo")
    private int inAtivo;

    @Column(name = "in_ordem")
    private int inOrdem;

    @Column(name = "nome_sub_grupo")
    private String nomeSubGrupo;

    // bi-directional many-to-one association to EstabelecimentoGrupo
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private EstabelecimentoGrupo estabelecimentoGrupo;

    public EstabelecimentoSubGrupo() {
    }

    public Integer getIdSg() {
        return this.idSg;
    }

    public void setIdSg(Integer idSg) {
        this.idSg = idSg;
    }

    public int getInAtivo() {
        return this.inAtivo;
    }

    public void setInAtivo(int inAtivo) {
        this.inAtivo = inAtivo;
    }

    public int getInOrdem() {
        return this.inOrdem;
    }

    public void setInOrdem(int inOrdem) {
        this.inOrdem = inOrdem;
    }

    public String getNomeSubGrupo() {
        return this.nomeSubGrupo;
    }

    public void setNomeSubGrupo(String nomeSubGrupo) {
        this.nomeSubGrupo = nomeSubGrupo;
    }

    public EstabelecimentoGrupo getEstabelecimentoGrupo() {
        return this.estabelecimentoGrupo;
    }

    public void setEstabelecimentoGrupo(EstabelecimentoGrupo estabelecimentoGrupo) {
        this.estabelecimentoGrupo = estabelecimentoGrupo;
    }

}
