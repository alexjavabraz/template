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
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;

@ManagedBean
@Scope("session")
@Component
public class PesquisarEstabelecimentosBean extends BasicBBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6286193401944423527L;

	private List<Estabelecimento> estabelecimentos;
	private Estabelecimento estabelecimento;
	private Estabelecimento filtro;

	private List<Theme> themes;

	@PostConstruct
	public void init() {

		filtro = new Estabelecimento();
		estabelecimentos = new ArrayList<Estabelecimento>();
		estabelecimento = null;

		if (themes != null)
			themes = service.getThemes();
	}

	public void pesquisar() {
		estabelecimentos = servicec.listarTodosEstabelecimentos(filtro);
	}

	public void salvar() {
		try {
			servicec.salvarEstabelecimento(getEstabelecimento());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estabelecimento SALVO com sucesso",
					"Estabelecimento SALVO com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			logger.error("Erro salvando o estabelecimento", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Erro ao salvar estabelecimento");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void preparaImagem() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);

			byte[] bytes = IOUtils.toByteArray(estabelecimento.getFile().getInputstream());

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			estabelecimento.setImagem(new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String doClick() {
		filtro = new Estabelecimento();
		estabelecimentos = new ArrayList<Estabelecimento>();
		estabelecimento = null;

		// pesquisar();

		return "pesquisar_estabelecimento.jsf";
	}

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getFiltro() {
		return filtro;
	}

	public void setFiltro(Estabelecimento filtro) {
		this.filtro = filtro;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setService(ThemeService service) {
		this.service = service;
	}

	public ThemeService getService() {
		return service;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

}
