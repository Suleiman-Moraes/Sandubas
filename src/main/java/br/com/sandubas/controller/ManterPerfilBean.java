package br.com.sandubas.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.spacer.Spacer;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Funcionalidade;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.service.PerfilService;
import br.com.sandubas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ManterPerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilService perfilService;

	private Perfil perfil;

	private List<Funcionalidade> funcionalidades;

	private DualListModel<Funcionalidade> funcionalidadesDualList;

	private String filtro;
	
	//Relacionados a ComboBOX
	private String filtroSelecionado;
	private String filtroValor;
	private int totalDeRegistros;
	private LazyDataModel<Perfil> registros;
	private List<FiltroPesquisa> filtrosPesquisa = new ArrayList<>();
	
	// Construtor
	public ManterPerfilBean() {
		registros = new LazyDataModel<Perfil>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Perfil> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				List<Perfil> registros = null;
				filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
				registros = perfilService.paginarPerfil(first, pageSize, filtroSelecionado, filtroValor);
				totalDeRegistros = perfilService.contarPerfilCadastradas(filtroSelecionado, filtroValor);
				setRowCount(totalDeRegistros);
				filtroValor = "";
				return registros;
			}
		};
	}

	public void abrirModalPerfil(Perfil perfil) {
		Map<String, Object> opcoes = new HashMap<>();
		Map<String, List<String>> params = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 550);
		opcoes.put("contentWidth", 620);
		opcoes.put("draggable", true);

		if (perfil != null) {
			List<String> perfilId = new ArrayList<>();
			perfilId.add(perfil.getId().toString());
			params.put("perfilId", perfilId);
		}
		PrimeFaces.current().dialog().openDynamic(FacesUtil.propertiesLoaderURL().getProperty("linkModalCadastroPerfil"), opcoes, params);
	}

	public void mostrarMensagemSucesso() {
		FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("perfilSucessoNovo"));
	}

	public void salvarPerfil() {
		try {
			this.perfil.setFuncionalidades(funcionalidadesDualList.getTarget());
			perfilService.salvarPerfil(perfil);
			PrimeFaces.current().dialog().closeDynamic(perfil);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	public void excluirPerfil() {
		try {
			this.perfilService.excluirPerfil(getPerfil());
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	public void selecionaFiltro() {
		this.perfilService.getPerfilDAO().setFiltroSelecionado(filtro);
	}

	public void limpar() {
		this.perfil = new Perfil();
	}
	
	//Relacionados a ComboBox
	public void selecionarFiltroPesquisa(AjaxBehaviorEvent event) {
		UIOutput select = (UIOutput) event.getSource();
		String value = select.getValue().toString();
		FiltroPesquisa filtroPesquisa = filtrosPesquisa.stream().filter(f -> f.getValue().equals(value)).findFirst().get();

		InputText nome = FacesUtil.inputText("fo-perfil:listarPerfis:nome");
		
		InputText id = FacesUtil.inputText("fo-perfil:listarPerfis:id");

		Spacer espaco = FacesUtil.spacer("fo-perfil:listarPerfis:espaco");
		switch (filtroPesquisa.getValue()) {
		case "nome":
		case "descricao":
			nome.setStyle("display: inline");
			id.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "id":
			id.setStyle("display: inline");
			nome.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		default:
			id.setStyle("display: none");
			nome.setStyle("display: none");
			espaco.setStyle("display: inline");
		}
	}

	@PostConstruct
	public void init() {
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar Todos", ""));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Nome", "nome"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Descricão", "descricao"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Código", "id"));
	}

	// Getter e Setters

	public PerfilService getPerfilService() {
		return perfilService;
	}

	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public DualListModel<Funcionalidade> getFuncionalidadesDualList() {
		this.funcionalidades = this.perfilService.getFuncionalidadeDAO().getList(Funcionalidade.class);
		this.funcionalidadesDualList = new DualListModel<Funcionalidade>(this.funcionalidades,
				new ArrayList<Funcionalidade>(0));
		if (this.perfil.getId() != null) {
			List<Funcionalidade> auxSource = this.funcionalidades;
			List<Funcionalidade> auxTarget = this.perfil.getFuncionalidades();	
			auxSource.removeAll(auxTarget);
			this.funcionalidadesDualList.setSource(auxSource);
			this.funcionalidadesDualList.setTarget(auxTarget);
		}
		return funcionalidadesDualList;
	}

	public void setFuncionalidadesDualList(DualListModel<Funcionalidade> funcionalidadesDualList) {
		this.funcionalidadesDualList = funcionalidadesDualList;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	//Getters e Setters ComboBox
	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}

	public String getFiltroValor() {
		return filtroValor;
	}

	public int getTotalDeRegistros() {
		return totalDeRegistros;
	}

	public LazyDataModel<Perfil> getRegistros() {
		return registros;
	}

	public List<FiltroPesquisa> getFiltrosPesquisa() {
		return filtrosPesquisa;
	}

	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}

	public void setFiltroValor(String filtroValor) {
		this.filtroValor = filtroValor;
	}

	public void setTotalDeRegistros(int totalDeRegistros) {
		this.totalDeRegistros = totalDeRegistros;
	}

	public void setRegistros(LazyDataModel<Perfil> registros) {
		this.registros = registros;
	}

	public void setFiltrosPesquisa(List<FiltroPesquisa> filtrosPesquisa) {
		this.filtrosPesquisa = filtrosPesquisa;
	}
}
