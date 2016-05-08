package br.com.bjbraz.app.estabelecimentos.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * The persistent class for the estabelecimento database table.
 * 
 */
@NamedQueries({ 
	
	@NamedQuery(name = Estabelecimento.BUSCAR_POR_DESCRICAO, query = "select c from Estabelecimento c where upper(c.nomeFantasia) = upper(:descricao)  ")

})
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496675013631247782L;

	public static final String BUSCAR_POR_DESCRICAO = "Estabelecimento.BUSCAR_POR_DESCRICAO";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;

    @Column(name = "end_complemento")
    private String endComplemento;

    @Column(name = "end_numero")
    private String endNumero;

    @Column(name = "end_rua")
    private String endRua;

    @Column(name = "id_cidade")
    private int idCidade;

    @Column(name = "id_destaque")
    private boolean idDestaque;

    @Column(name = "id_estado")
    private int idEstado;

    @Column(name="imagem1", columnDefinition = "LONGBLOB")
    private byte[] imagem1;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_inclusao")
    private Date dhInclusao;

    @Lob()
    private byte[] imagem2;

    @Lob()
    private byte[] imagem3;

    @Lob()
    private byte[] imagem4;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "razao_social")
    private String razaoSocial;

    private String site;

    private String slogam;

    private String telefone;
    
    @Column(name="bairro", length=100, nullable=true)
    private String bairro;
    
    @Column(name="cep", length=10, nullable=true)
    private String cep;
    
    @Column(name="facebook", length=60, nullable=true)
    private String facebook;  
    
    @Column(name="id_sub_grupo")
    private Integer idSubGrupo;
    
    @Column(name="posicao")
    private Integer posicao;    
    
    @Transient
    EstabelecimentoSubGrupo sgrupo     = null;
    
    @Transient
    EstabelecimentoGrupo grupo         = null;
    
    @Transient
    EstabelecimentoCategoria categoria = null;
    
    @Transient
    private Date dataInclusaoInicio;
    
    @Transient
	private Date dataInclusaoFim;
    
    @Transient
    private StreamedContent imagem;
    
    @Transient
    private UploadedFile file;

    public Estabelecimento() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndComplemento() {
        return this.endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndNumero() {
        return this.endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndRua() {
        return this.endRua;
    }

    public void setEndRua(String endRua) {
        this.endRua = endRua;
    }

    public int getIdCidade() {
        return this.idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public boolean isIdDestaque() {
		return idDestaque;
	}

	public void setIdDestaque(boolean idDestaque) {
		this.idDestaque = idDestaque;
	}

	public int getIdEstado() {
        return this.idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public byte[] getImagem1() {
        return this.imagem1;
    }

    public void setImagem1(byte[] imagem1) {
        this.imagem1 = imagem1;
    }

    public byte[] getImagem2() {
        return this.imagem2;
    }

    public void setImagem2(byte[] imagem2) {
        this.imagem2 = imagem2;
    }

    public byte[] getImagem3() {
        return this.imagem3;
    }

    public void setImagem3(byte[] imagem3) {
        this.imagem3 = imagem3;
    }

    public byte[] getImagem4() {
        return this.imagem4;
    }

    public void setImagem4(byte[] imagem4) {
        this.imagem4 = imagem4;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSlogam() {
        return this.slogam;
    }

    public void setSlogam(String slogam) {
        this.slogam = slogam;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Integer getIdSubGrupo() {
        return idSubGrupo;
    }

    public void setIdSubGrupo(Integer idSubGrupo) {
        this.idSubGrupo = idSubGrupo;
    }

    public Date getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(Date dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

    public EstabelecimentoSubGrupo getSgrupo() {
        return sgrupo;
    }

    public void setSgrupo(EstabelecimentoSubGrupo sgrupo) {
        this.sgrupo = sgrupo;
    }

    public EstabelecimentoGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(EstabelecimentoGrupo grupo) {
        this.grupo = grupo;
    }

    public EstabelecimentoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(EstabelecimentoCategoria categoria) {
        this.categoria = categoria;
    }

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Date getDataInclusaoInicio() {
		return dataInclusaoInicio;
	}

	public void setDataInclusaoInicio(Date dataInclusaoInicio) {
		this.dataInclusaoInicio = dataInclusaoInicio;
	}

	public Date getDataInclusaoFim() {
		return dataInclusaoFim;
	}

	public void setDataInclusaoFim(Date dataInclusaoFim) {
		this.dataInclusaoFim = dataInclusaoFim;
	}
	
	public StreamedContent getImagem() {
		try {
			imagem = null;
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem = new DefaultStreamedContent(new ByteArrayInputStream(getImagem1()), "image/png", "imagem"+System.currentTimeMillis()+".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return imagem;
	}
	
	public void setImagem(StreamedContent s ){
		imagem = s;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
		try {
			byte[] bytes = IOUtils.toByteArray(file.getInputstream());
			setImagem1(bytes);
			getImagem();
		} catch (Exception e) {
		}
		
	}
	
	
	
}