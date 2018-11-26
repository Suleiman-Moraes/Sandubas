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
import org.primefaces.event.FlowEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;
import br.com.sandubas.model.enums.TipoTelefoneEnum;
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

	@Getter @Setter
	private String limitOffset;
	
	private FuncaoUsuarioEnum[] funcoes;
	private StatusUsuarioEnum[] listaStatusUsuarioEnum;
	private TipoTelefoneEnum[] listaTipoTelefoneEnum;

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

	public void mostrarMensagemSucesso(boolean edicao) {
		if (edicao) {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("usuarioSucessoEditado"));
		} else {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("usuarioSucessoNovo"));
		}
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
	
	@Override
	public Object[] abrirModal(Usuario objeto) {
		Object[] retorno = new Object[2];
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 400);
		opcoes.put("contentWidth", 700);
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
			List<Perfil> listaPerfil = new ArrayList<>();
			Perfil aux = service.getPerfilService().getPerfilDAO().findByIdEager
					(Perfil.class, new Long(objeto.getFuncaoUsuarioEnum().getId()));
			listaPerfil.add(aux);
			this.objeto.setPerfis(listaPerfil);
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
	
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

	public Usuario getUsuario() {
		if (objeto == null) {
			objeto = new Usuario();
		}
		return objeto;
	}
	
	@Override
	public Usuario getObjeto() {
		if (objeto == null) {
			objeto = new Usuario();
		}
		return objeto;
	}

	public FuncaoUsuarioEnum[] getFuncoes() {
		if (funcoes == null || funcoes.length == 0) {
			FuncaoUsuarioEnum[] aux = FuncaoUsuarioEnum.values();
			funcoes = new FuncaoUsuarioEnum[aux.length - 1];
			for (int i = 1; i < aux.length; i++) {
				funcoes[i - 1] = aux[i];
			}
		}
		return funcoes;
	}
	
	public StatusUsuarioEnum[] getListaStatusUsuarioEnum() {
		if(listaStatusUsuarioEnum == null) {
			listaStatusUsuarioEnum = StatusUsuarioEnum.values();
		}
		return listaStatusUsuarioEnum;
	}
	
	public TipoTelefoneEnum[] getListaTipoTelefoneEnum() {
		if(listaTipoTelefoneEnum == null) {
			listaTipoTelefoneEnum = TipoTelefoneEnum.values();
		}
		return listaTipoTelefoneEnum;
	}
}
