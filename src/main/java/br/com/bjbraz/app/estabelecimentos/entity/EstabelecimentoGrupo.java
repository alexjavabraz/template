package br.com.bjbraz.app.estabelecimentos.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the estabelecimento_grupo database table.
 * 
 */

@NamedQueries({ @NamedQuery(name = EstabelecimentoGrupo.BUSCAR_POR_CATEGORIA, query = "select c from EstabelecimentoGrupo c "
    + " where c.estabelecimentoCategoria.idCat = :id  ") })
@Entity
@Table(name = "estabelecimento_grupo")
public class EstabelecimentoGrupo implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_CATEGORIA = "EstabelecimentoGrupo.BUSCAR_POR_CATEGORIA";

    @Id
    @Column(name = "id_grupo")
    private Integer idGrupo;

    @Column(name = "in_ativo")
    private int inAtivo;

    @Column(name = "nome_grupo")
    private String nomeGrupo;

    private int ordem;

    // bi-directional many-to-one association to EstabelecimentoCategoria
    @ManyToOne
    @JoinColumn(name = "id_cat")
    private EstabelecimentoCategoria estabelecimentoCategoria;

    // bi-directional many-to-one association to EstabelecimentoSubGrupo
    @OneToMany(mappedBy = "estabelecimentoGrupo")
    private Set<EstabelecimentoSubGrupo> estabelecimentoSubGrupos;

    public EstabelecimentoGrupo() {
    }

    public Integer getIdGrupo() {
        return this.idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getInAtivo() {
        return this.inAtivo;
    }

    public void setInAtivo(int inAtivo) {
        this.inAtivo = inAtivo;
    }

    public String getNomeGrupo() {
        return this.nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public int getOrdem() {
        return this.ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public EstabelecimentoCategoria getEstabelecimentoCategoria() {
        return this.estabelecimentoCategoria;
    }

    public void setEstabelecimentoCategoria(EstabelecimentoCategoria estabelecimentoCategoria) {
        this.estabelecimentoCategoria = estabelecimentoCategoria;
    }

    public Set<EstabelecimentoSubGrupo> getEstabelecimentoSubGrupos() {
        return this.estabelecimentoSubGrupos;
    }

    public void setEstabelecimentoSubGrupos(Set<EstabelecimentoSubGrupo> estabelecimentoSubGrupos) {
        this.estabelecimentoSubGrupos = estabelecimentoSubGrupos;
    }

}
