package br.com.bjbraz.app.estabelecimentos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name=Estado.BUSCAR_ESTADOS, query="select e from Estado e where e.idEstado = 25 order by e.uf")
})
@Entity
@Table(name="estado", schema="alex_database")
public class Estado implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String BUSCAR_ESTADOS = "Estado.BUSCAR_ESTADOS";
	
	@Id
	@Column(name="ID_ESTADO", unique=true, nullable=false)
	private Integer idEstado;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="UF")
	private String uf;

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}



}
