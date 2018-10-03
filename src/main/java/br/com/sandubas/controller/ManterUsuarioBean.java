package br.com.sandubas.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.spacer.Spacer;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.service.UsuarioService;
import br.com.sandubas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ManterUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario;

	private List<FiltroPesquisa> filtrosPesquisa = new ArrayList<>();

	private LazyDataModel<Usuario> registros;

	private String filtroValor;

	private int totalDeRegistros;

	private String filtroSelecionado;

	private DualListModel<Perfil> perfisDualList;

	private List<Perfil> perfis;

	private String limitOffset;

	private FuncaoUsuarioEnum[] funcoes;

	@PostConstruct
	public void init() {
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Todos registros", "default"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Email", "email.email"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Função", "funcaoUsuarioEnum"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Login", "login"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Nome", "nome"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Situação", "statusUsuarioEnum"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Tipo de Acesso", "tipoUsuarioEnum"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Unidade", "unidade"));
	}

	public ManterUsuarioBean() {
		registros = new LazyDataModel<Usuario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				List<Usuario> registros = null;
				filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
				registros = usuarioService.paginarUsuarios(first, pageSize, filtroSelecionado, filtroValor);
				totalDeRegistros = usuarioService.contarUsuariosCadastrados(filtroSelecionado, filtroValor);
				setRowCount(totalDeRegistros);
				filtroValor = "";
				return registros;
			}
		};
	}

	public void selecionarFiltroPesquisa(AjaxBehaviorEvent event) {
		UIOutput select = (UIOutput) event.getSource();
		String value = select.getValue().toString();
		FiltroPesquisa filtroPesquisa = filtrosPesquisa.stream().filter(f -> f.getValue().equals(value)).findFirst()
				.get();

		InputText nome = FacesUtil.inputText("fo-usuario:usuarios:nome");

		InputText email = FacesUtil.inputText("fo-usuario:usuarios:email");

		InputText login = FacesUtil.inputText("fo-usuario:usuarios:login");

		HtmlSelectOneMenu funcaoUsuario = FacesUtil.htmlSelectOneMenu("fo-usuario:usuarios:funcaoUsuario");

		HtmlSelectOneMenu unidade = FacesUtil.htmlSelectOneMenu("fo-usuario:usuarios:unidade");

		HtmlSelectOneMenu statusUsuario = FacesUtil.htmlSelectOneMenu("fo-usuario:usuarios:statusUsuario");

		HtmlSelectOneMenu tipoAcesso = FacesUtil.htmlSelectOneMenu("fo-usuario:usuarios:tipoAcesso");

		Spacer espaco = FacesUtil.spacer("fo-usuario:usuarios:espaco");
		switch (filtroPesquisa.getValue()) {
		case "nome":
			nome.setStyle("display: inline");
			email.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			statusUsuario.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "email.email":
			email.setStyle("display: inline");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			statusUsuario.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "login":
			login.setStyle("display: inline");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			statusUsuario.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "unidade":
			unidade.setStyle("display: inline");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			statusUsuario.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "funcaoUsuarioEnum":
			funcaoUsuario.setStyle("display: inline");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			statusUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "statusUsuarioEnum":
			statusUsuario.setStyle("display: inline");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		case "tipoUsuarioEnum":
			tipoAcesso.setStyle("display: inline");
			statusUsuario.setStyle("display: none");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
			break;
		default:
			statusUsuario.setStyle("display: none");
			email.setStyle("display: none");
			nome.setStyle("display: none");
			funcaoUsuario.setStyle("display: none");
			unidade.setStyle("display: none");
			tipoAcesso.setStyle("display: none");
			login.setStyle("display: none");
			espaco.setStyle("display: inline");
		}
	}

	public void abrirModalUsuario(Usuario usuario) {
		Map<String, Object> opcoes = new HashMap<>();
		Map<String, List<String>> params = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 700);
		opcoes.put("contentWidth", 800);
		opcoes.put("draggable", true);

		if (usuario != null) {
			List<String> usuarioId = new ArrayList<>();
			usuarioId.add(usuario.getId().toString());
			params.put("usuarioId", usuarioId);
		}
		RequestContext.getCurrentInstance()
				.openDialog(FacesUtil.propertiesLoaderURL().getProperty("linkModalCadastroUsuario"), opcoes, params);
	}

	public void redefinirSenha() {
//		getUsuario().setRedefinirSenha(Boolean.FALSE);
		getUsuario().setSenha("");
		getUsuario().setConfirmacaoSenha("");
	}

	public void mostrarMensagemSucesso() {
		FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("usuarioSucesso"));
	}

	public void mostrarMensagemSucessoRedefinirSenha() {
		FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("usuarioRedefinirSenhaSucesso"));
	}

	public void excluirUsuario() {
		try {
			this.usuarioService.excluirUsuario(getUsuario());
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	public void salvarUsuario() {
		try {
			this.usuario.setPerfis(this.perfisDualList.getTarget());
			this.usuarioService.salvarUsuario(usuario);
			RequestContext.getCurrentInstance().closeDialog(usuario);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	public void redefinirSenhaUsuario() {
//		try {
//			this.usuarioService.redefinirSenhaUsuario(usuario);
//		} catch (NegocioException e) {
//			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
//		}
	}

	public void mostrarMensagemSucessoSenhaRedefinirManifestante() {
		FacesUtil.addSuccessMessage(
				FacesUtil.propertiesLoader().getProperty("usuarioRedefinirSenhaSucessoManifestante"));
	}

	public void limpar() {
		this.usuario = new Usuario();
	}

	public void atualizarDualList() {
		if (this.usuario.getFuncaoUsuarioEnum() != null) {

			// Declaração e inicialização de variaveis
			@SuppressWarnings("unused")
			List<Perfil> perfisDoUsuarioValidos = new ArrayList<>();

			// busca os todos os perfis e os perfis do banco de dados
			this.perfis = this.usuarioService.getPerfilService()
					.obterPerfilPassandoFuncaoUsuario(this.usuario.getFuncaoUsuarioEnum());

			// inicial o componete com as duas lista preparadas
			this.perfisDualList = new DualListModel<Perfil>(this.perfis, new ArrayList<Perfil>(0));
		} else {

			// inicial uma lista do zero
			this.perfisDualList = new DualListModel<Perfil>(new ArrayList<Perfil>(0), new ArrayList<Perfil>(0));
		}
	}

	public void carregarDualList() {
		if (this.usuario.getFuncaoUsuarioEnum() != null) {

			// Declaração e inicialização de variaveis
			List<Perfil> perfisDoUsuarioValidos = new ArrayList<>();

			// busca os todos os perfis e os perfis do banco de dados
			this.perfis = this.usuarioService.getPerfilService()
					.obterPerfilPassandoFuncaoUsuario(this.usuario.getFuncaoUsuarioEnum());
			this.usuario.setPerfis(this.usuarioService.getPerfilService().getPerfilDAO()
					.buscarPerfisDoUsuarioPassandoUsuario(this.usuario));

			// Testa se o perfil do banco e valido para atualizar dual list
			for (Perfil perfil : this.usuario.getPerfis()) {
				if (this.perfis.contains(perfil)) {
					perfisDoUsuarioValidos.add(perfil);
				}
			}

			// Seta os perfis validos do usuario na variavel this.usuario
			this.usuario.setPerfis(perfisDoUsuarioValidos);

			// remove os perfis que o usuario ja possui
			this.perfis.removeAll(this.usuario.getPerfis());

			// inicial o componete com as duas lista preparadas
			this.perfisDualList = new DualListModel<Perfil>(this.perfis, this.usuario.getPerfis());
		} else {

			// inicial uma lista do zero
			this.perfisDualList = new DualListModel<Perfil>(new ArrayList<Perfil>(0), new ArrayList<Perfil>(0));
		}
	}

//	public void alteraTooltip() {
//		if (usuario.getStatusUsuarioEnum() == StatusUsuarioEnum.ATIVO
//				|| usuario.getStatusUsuarioEnum() == StatusUsuarioEnum.NOVA_SENHA) {
//			unidadesParaCadastro = this.usuarioService.getUnidadeService().getUnidadeDAO().buscarUnidadesAtivas();
//		}
//		if (StatusUsuarioEnum.INATIVO.equals(getUsuario().getStatusUsuarioEnum())
//				|| (StatusUsuarioEnum.NOVA_SENHA.equals(getUsuario().getStatusUsuarioEnum()))) {
//			if (StatusUsuarioEnum.INATIVO.equals(getUsuario().getStatusUsuarioEnum())) {
//				unidadesParaCadastro = this.usuarioService.getUnidadeService().getUnidadeDAO().findAllDistinctUnidade();
//				getUsuario().setTooltipStatus(FacesUtil.propertiesLoader().getProperty("usuarioInativar"));
//			} else {
//				getUsuario().setTooltipStatus(FacesUtil.propertiesLoader().getProperty("usuarioCadastrarSenha"));
//			}
//		} else {
//			getUsuario().setTooltipStatus("");
//		}
//	}

//	public void configurarOpcaoDeAcordoComUnidadeOuvidoriaAGR() {
//		unidadesParaCadastro = this.usuarioService.getUnidadeService().getUnidadeDAO().findAllDistinctUnidade();
//		if (this.usuario.getUnidade() != null && this.usuario.getUnidade().getId() == 1) {
//			this.funcoes = new FuncaoUsuarioEnum[1];
//			this.funcoes[0] = FuncaoUsuarioEnum.OUVIDOR;
//			this.usuario.setFuncaoUsuarioEnum(FuncaoUsuarioEnum.OUVIDOR);
//		} else if (this.usuario.getFuncaoUsuarioEnum() != null
//				&& this.usuario.getFuncaoUsuarioEnum() != FuncaoUsuarioEnum.OUVIDOR) {
//			this.carregarFuncoes();
//			Unidade uni = this.usuarioService.getUnidadeService().getUnidadeDAO().findById(Unidade.class, (long) 1);
//			this.unidadesParaCadastro.remove(uni);
//		} else {
//			this.carregarFuncoes();
//		}
//		this.atualizarDualList();
//	}

	private void carregarFuncoes() {
		funcoes = usuarioService.getFuncoes();
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<FiltroPesquisa> getFiltrosPesquisa() {
		return filtrosPesquisa;
	}

	public LazyDataModel<Usuario> getRegistros() {
		return registros;
	}

	public void setRegistros(LazyDataModel<Usuario> registros) {
		this.registros = registros;
	}

	public String getFiltroValor() {
		return filtroValor;
	}

	public void setFiltroValor(String filtroValor) {
		this.filtroValor = filtroValor;
	}

	public int getTotalDeRegistros() {
		return totalDeRegistros;
	}

	public void setTotalDeRegistros(int totalDeRegistros) {
		this.totalDeRegistros = totalDeRegistros;
	}

	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}

	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}

	public DualListModel<Perfil> getPerfisDualList() {
		if (this.perfisDualList == null) {
			carregarDualList();
		}
		return perfisDualList;
	}

	public void setPerfisDualList(DualListModel<Perfil> perfisDualList) {
		this.perfisDualList = perfisDualList;
	}

	public FuncaoUsuarioEnum[] getFuncoes() {
		if (funcoes == null || funcoes.length == 0) {
			this.carregarFuncoes();
		}
		return funcoes;
	}

	public String getLimitOffset() {
		return limitOffset;
	}

	public void setLimitOffset(String limitOffset) {
		this.limitOffset = limitOffset;
	}
}
