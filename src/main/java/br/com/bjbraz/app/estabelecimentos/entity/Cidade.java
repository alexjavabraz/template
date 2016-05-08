package br.com.bjbraz.app.estabelecimentos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = Cidade.BUSCAR_POR_NOME_ESTADO, query = "select c from Cidade c  where c.uf = :nomeEstado order by c.cidade"),
		@NamedQuery(name = Cidade.BUSCAR_POR_ID_ESTADO, query = "select cd from Cidade cd  where cd.idEstado = :idEstado order by cd.cidade") })
@Entity
@Table(name = "cidade", schema = "alex_database")
public class Cidade implements java.io.Serializable {

	private static final long serialVersionUID = 307326131085398937L;

	public static final String BUSCAR_POR_NOME_ESTADO = "Cidade.BUSCAR_POR_NOME_ESTADO";

	public static final String BUSCAR_POR_ID_ESTADO = "Cidade.BUSCAR_POR_ID_ESTADO";

	@Id
	@Column(name = "ID_CIDADE")
	private Integer idCidade;

	@Column(name = "nomeCIDADE")
	private String cidade;

	@Column(name = "ID_ESTADO")
	private Integer idEstado;

	@Column(name = "NOME_ESTADO")
	private String nomeEstado;

	@Column(name = "UF")
	private String uf;

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
