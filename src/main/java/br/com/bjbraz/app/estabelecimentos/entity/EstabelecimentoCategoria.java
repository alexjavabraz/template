package br.com.bjbraz.app.estabelecimentos.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the estabelecimento_categoria database table.
 * 
 */
@Entity
@Table(name = "estabelecimento_categoria")
public class EstabelecimentoCategoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cat")
    private Integer idCat;

    @Column(name = "in_ativo")
    private int inAtivo;

    @Column(name = "nome_cat")
    private String nomeCat;

    // bi-directional many-to-one association to EstabelecimentoGrupo
    @OneToMany(mappedBy = "estabelecimentoCategoria")
    private Set<EstabelecimentoGrupo> estabelecimentoGrupos;

    public EstabelecimentoCategoria() {
    }

    public Integer getIdCat() {
        return this.idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public int getInAtivo() {
        return this.inAtivo;
    }

    public void setInAtivo(int inAtivo) {
        this.inAtivo = inAtivo;
    }

    public String getNomeCat() {
        return this.nomeCat;
    }

    public void setNomeCat(String nomeCat) {
        this.nomeCat = nomeCat;
    }

    public Set<EstabelecimentoGrupo> getEstabelecimentoGrupos() {
        return this.estabelecimentoGrupos;
    }

    public void setEstabelecimentoGrupos(Set<EstabelecimentoGrupo> estabelecimentoGrupos) {
        this.estabelecimentoGrupos = estabelecimentoGrupos;
    }

}
