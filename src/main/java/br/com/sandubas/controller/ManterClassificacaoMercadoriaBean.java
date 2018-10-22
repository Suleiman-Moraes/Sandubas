package br.com.sandubas.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.interfaces.ICRUDSimples;
import br.com.sandubas.service.ClassificacaoMercadoriaService;
import br.com.sandubas.util.TemplatePaginacao;
import br.com.sandubas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ManterClassificacaoMercadoriaBean extends TemplatePaginacao<ClassificacaoMercadoria> implements Serializable, ICRUDSimples<ClassificacaoMercadoria>{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClassificacaoMercadoriaService service;
	
	public ManterClassificacaoMercadoriaBean() {
		registros = new LazyDataModel<ClassificacaoMercadoria>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public List<ClassificacaoMercadoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				List<ClassificacaoMercadoria> registros = null;
				filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
				registros = service.paginarRegistro(first, pageSize, filtroSelecionado, filtroValor);
				totalDeRegistros = service.contarRegistrosCadastrados(filtroSelecionado, filtroValor);
				setRowCount(totalDeRegistros);
				filtroValor = "";
				return registros;
			}
		};
	}
	
	public void mostrarMensagemSucesso(boolean edicao) {
		if (edicao) {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("classificacaoMercadoriaSucessoEditado"));
		} else {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("classificacaoMercadoriaSucessoNovo"));
		}
	}

	@Override
	public Object[] abrirModal(ClassificacaoMercadoria objeto) {
		Object[] retorno = new Object[2];
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);//redimensionar a modal
		opcoes.put("contentHeight", 270);//altura
		opcoes.put("contentWidth", 600);//largura
		opcoes.put("draggable", true);//movimentar a modal
		
		retorno[0] = opcoes;//configuração da modal
		retorno[1] = "linkModalCadastroClassificacaoMercadoria";//Link da modal no url_pt
		
		return retorno;
	}

	@Override
	public void deletarObjeto() {
		try {
			this.service.excluir(getObjeto());
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public void salvarObjeto() {
		try {
			service.salvar(objeto);
			PrimeFaces.current().dialog().closeDynamic(objeto);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public void limpar() {
		this.objeto = new ClassificacaoMercadoria();
	}
	
	@PostConstruct
	@Override
	public void init() {
		filtrosPesquisa.add(new FiltroPesquisa("Todos", ""));
		filtrosPesquisa.add(new FiltroPesquisa("Código", "id"));
		filtrosPesquisa.add(new FiltroPesquisa("Nome", "nome"));
		filtrosPesquisa.add(new FiltroPesquisa("Descrição", "descricao"));
	}
	
	@Override
	public UIComponent[] indentificarComponentes() throws Exception {
		UIComponent[] componentes = new UIComponent[3];
		
		HtmlInputText id = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("id"));
		HtmlInputText nome = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("nome"));
		HtmlInputText descricao = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("descricao"));
		
		componentes[0] = id;
		componentes[1] = nome;
		componentes[2] = descricao;

		return componentes;
	}
	
	//	Getters and Setters
	@Override
	public ClassificacaoMercadoria getObjeto() {
		if(objeto == null) {
			objeto = new ClassificacaoMercadoria();
		}
		return objeto;
	}
}
