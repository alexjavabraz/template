package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.Estado;
import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;
import br.com.bjbraz.app.estabelecimentos.service.EstadoService;
import br.com.bjbraz.app.estabelecimentos.validator.Email;

@Scope("session")
@Component
public class MeuManagedBBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543348233516763545L;

	private List<SelectItem> categories;
	private String selection;
	private String option;

	private UploadedFile file;
	private boolean destaque = false;
	private String phone;
	private String celPhone;
	private String fileName;

	@Email(message = "Favor informar um email válido")
	private String email;

	private Estabelecimento estabelecimento = new Estabelecimento();

	@Inject
	private EstadoService estadoService;

	@Inject
	private CadastroEstabelecimentoService service;

	private boolean temImagem = false;

	private StreamedContent imagem;

	public void inicializar() {
		estabelecimento = new Estabelecimento();
		temImagem = false;
		destaque = false;
		selection = "";
		option = "";
		file = null;
		fileName = null;
		email = null;
		imagem = null;
		
		if(service == null){
			System.out.println("ERRO !!!!");
		}
	}

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! ");
		montaSelectGrupos();
		montaSelectCidades();
	}

	private void montaSelectCidades() {
		List<Estado> estados = estadoService.mostrarEstados();

	}

	private void montaSelectGrupos() {
		categories = new ArrayList<SelectItem>();

		List<EstabelecimentoCategoria> categorias = service.listarTodasCategorias();

		for (int i = 0; i < categorias.size(); i++) {
			EstabelecimentoCategoria categoria = categorias.get(i);

			SelectItemGroup group1 = new SelectItemGroup(categoria.getNomeCat());

			List<EstabelecimentoGrupo> grupos = service.listarGruposPorCategoria(categoria.getIdCat());

			/**
			 * 
			 */
			group1.setSelectItems(new SelectItem[grupos.size()]);

			for (int x = 0; x < grupos.size(); x++) {

				EstabelecimentoGrupo grupo = grupos.get(x);

				SelectItemGroup group11 = new SelectItemGroup(grupo.getNomeGrupo());

				List<EstabelecimentoSubGrupo> subGrupos = service.listarSubGrupoPorGrupo(grupo.getIdGrupo());

				group11.setSelectItems(new SelectItem[subGrupos.size()]);

				for (int y = 0; y < subGrupos.size(); y++) {

					EstabelecimentoSubGrupo subGrupo = subGrupos.get(y);

					SelectItem option111 = new SelectItem(subGrupo.getNomeSubGrupo());

					group11.getSelectItems()[y] = option111;
				}

				group1.getSelectItems()[x] = group11;

			}

			categories.add(group1);
		}

	}

	public String getMessage() {
		return "Hello World JSF!";
	}

	public List<SelectItem> getCategories() {
		return categories;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String next() {
		if (file != null && (null != selection) && ("".equals(selection)) ) {
			fileName = file.getFileName();
			FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);

			preparaImagem();
			return "index2";
		}else{
			FacesMessage message = new FacesMessage("Favor informar Categoria => Grupo => SubGrupo");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

		return null;
	}
	
	public String voltar() {
		return "index";
	}
	
	public String limpar() {
		
		inicializar();
		
		return "index";
	}

	public String upload() {
		if (file != null && (null != selection) && (!"".equals(selection))) {
			fileName = file.getFileName();
			FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);

			preparaImagem();
			
			return "index2";
		}else{
			FacesMessage message = new FacesMessage("Favor informar Categoria => Grupo => SubGrupo");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

		return null;
	}

	public String gravar() {
		
		EstabelecimentoSubGrupo esg = service.listarSubGrupoPorNome(selection);
		getEstabelecimento().setIdSubGrupo(esg.getIdSg());
		
		Estabelecimento estabelecimentoComMesmoNome = service.listarEstabelecimentoPorNome(getEstabelecimento().getNomeFantasia());
		
		if(estabelecimentoComMesmoNome != null){
			FacesMessage message = new FacesMessage("J� existe um Estabelecimento com este nome");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		
		
		if (esg != null ) {
			try{
				getEstabelecimento().setImagem1(IOUtils.toByteArray(file.getInputstream()));
				service.salvarEstabelecimento(getEstabelecimento());
				
				FacesMessage message = new FacesMessage(getEstabelecimento().getNomeFantasia() + " salvo com sucesso !");
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				inicializar();
				return "index";
			}catch(Exception e){
//				logger.error("Erro ao salvar estabelecimento ", e);
				FacesMessage message = new FacesMessage("Ocorreu um erro ao salvar Estabelecimento, tente novamente");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		
		return null;
	}

	private void preparaImagem() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(file.getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png");
			
			temImagem = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCelPhone() {
		return celPhone;
	}

	public void setCelPhone(String celPhone) {
		this.celPhone = celPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isTemImagem() {
		return temImagem;
	}

	public void setTemImagem(boolean temImagem) {
		this.temImagem = temImagem;
	}

	public StreamedContent getImagem() {
		return imagem;
	}

	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

}