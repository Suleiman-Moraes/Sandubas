package br.com.sandubas.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.interfaces.ICRUDSimples;
import br.com.sandubas.service.UsuarioService;
import br.com.sandubas.util.TemplatePaginacao;
import br.com.sandubas.util.jsf.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ManterUsuarioBean extends TemplatePaginacao<Usuario> implements Serializable, ICRUDSimples<Usuario> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService service;

	@Setter
	private DualListModel<Perfil> perfisDualList;

	@Setter
	private List<Perfil> perfis;

	@Getter @Setter
	private String limitOffset;

	@Setter
	private FuncaoUsuarioEnum[] funcoes;

	@PostConstruct
	public void init() {
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Todos registros", ""));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Nome", "nome"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Login", "login"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Função", "funcaoUsuarioEnum"));
		filtrosPesquisa.add(new FiltroPesquisa("Pesquisar por Situação", "statusUsuarioEnum"));
	}

	public ManterUsuarioBean() {
		registros = new LazyDataModel<Usuario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				List<Usuario> registros = null;
				filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
				registros = service.paginarUsuarios(first, pageSize, filtroSelecionado, filtroValor);
				totalDeRegistros = service.contarUsuariosCadastrados(filtroSelecionado, filtroValor);
				setRowCount(totalDeRegistros);
				filtroValor = "";
				return registros;
			}
		};
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

	@Override
	public void limpar() {
		this.objeto = new Usuario();
	}

	public void atualizarDualList() {
		if (this.objeto.getFuncaoUsuarioEnum() != null) {

			// Declaração e inicialização de variaveis
			@SuppressWarnings("unused")
			List<Perfil> perfisDoUsuarioValidos = new ArrayList<>();

			// busca os todos os perfis e os perfis do banco de dados
			this.perfis = this.service.getPerfilService()
					.obterPerfilPassandoFuncaoUsuario(this.objeto.getFuncaoUsuarioEnum());

			// inicial o componete com as duas lista preparadas
			this.perfisDualList = new DualListModel<Perfil>(this.perfis, new ArrayList<Perfil>(0));
		} else {

			// inicial uma lista do zero
			this.perfisDualList = new DualListModel<Perfil>(new ArrayList<Perfil>(0), new ArrayList<Perfil>(0));
		}
	}

	public void carregarDualList() {
		if (this.objeto.getFuncaoUsuarioEnum() != null) {

			// Declaração e inicialização de variaveis
			List<Perfil> perfisDoUsuarioValidos = new ArrayList<>();

			// busca os todos os perfis e os perfis do banco de dados
			this.perfis = this.service.getPerfilService()
					.obterPerfilPassandoFuncaoUsuario(this.objeto.getFuncaoUsuarioEnum());
			this.objeto.setPerfis(this.service.getPerfilService().getPerfilDAO()
					.buscarPerfisDoUsuarioPassandoUsuario(this.objeto));

			// Testa se o perfil do banco e valido para atualizar dual list
			for (Perfil perfil : this.objeto.getPerfis()) {
				if (this.perfis.contains(perfil)) {
					perfisDoUsuarioValidos.add(perfil);
				}
			}

			// Seta os perfis validos do usuario na variavel this.usuario
			this.objeto.setPerfis(perfisDoUsuarioValidos);

			// remove os perfis que o usuario ja possui
			this.perfis.removeAll(this.objeto.getPerfis());

			// inicial o componete com as duas lista preparadas
			this.perfisDualList = new DualListModel<Perfil>(this.perfis, this.objeto.getPerfis());
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
		funcoes = service.getFuncoes();
	}
	
	@Override
	public Object[] abrirModal(Usuario objeto) {
		Object[] retorno = new Object[2];
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 700);
		opcoes.put("contentWidth", 800);
		opcoes.put("draggable", true);
		
		retorno[0] = opcoes;// configuração da modal
		retorno[1] = "linkModalCadastroUsuario";// Link da modal no url_pt

		return retorno;
	}

	@Override
	public void deletarObjeto() {
		try {
			this.service.excluirUsuario(getUsuario());
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public void salvarObjeto() {
		try {
			this.objeto.setPerfis(this.perfisDualList.getTarget());
			this.service.salvarUsuario(objeto);
			PrimeFaces.current().dialog().closeDynamic(objeto);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}
	
	@Override
	public UIComponent[] indentificarComponentes() throws Exception {
		UIComponent[] componentes = new UIComponent[5];

		HtmlInputText id = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("id"));
		HtmlInputText nome = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("nome"));
		HtmlInputText login = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("login"));
		HtmlInputText funcaoUsuarioEnum = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("funcaoUsuarioEnum"));
		HtmlInputText statusUsuarioEnum = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("statusUsuarioEnum"));

		componentes[0] = id;
		componentes[1] = nome;
		componentes[2] = login;
		componentes[3] = funcaoUsuarioEnum;
		componentes[4] = statusUsuarioEnum;
		
		return componentes;
	}

	public Usuario getUsuario() {
		if (objeto == null) {
			objeto = new Usuario();
		}
		return objeto;
	}

	public DualListModel<Perfil> getPerfisDualList() {
		if (this.perfisDualList == null) {
			carregarDualList();
		}
		return perfisDualList;
	}

	public FuncaoUsuarioEnum[] getFuncoes() {
		if (funcoes == null || funcoes.length == 0) {
			this.carregarFuncoes();
		}
		return funcoes;
	}
}
