package br.com.sandubas.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import br.com.sandubas.controller.UsuarioPaginateSessionScoped;
import br.com.sandubas.dao.UsuarioDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Paginacao;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;
import br.com.sandubas.security.Seguranca;
import br.com.sandubas.util.StringUtil;
import br.com.sandubas.util.jsf.FacesUtil;

@SuppressWarnings("deprecation")
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 7630507668274613371L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private PerfilService perfilService;

	@Inject
	private Seguranca seguranca;
	
	@Inject
	private EmailService emailService;

	@Inject
	private TelefoneService telefoneService;

	@Inject
	private EnviadorEmailService enviadorEmailService;

	private PasswordEncoder encoder;

	@Inject
	private UsuarioPaginateSessionScoped paginateSessionScoped;

	public void atualizarDadosUsuarioEsqueceuSenha(Usuario usuario) throws NegocioException {
		try {
			this.encoder = new Md5PasswordEncoder();
			this.setMd5PasswordEncoder(usuario);
			usuario.setStatusUsuarioEnum(StatusUsuarioEnum.ATIVO);

			this.salvarContatosUsuario(usuario);
			this.usuarioDAO.update(usuario);
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("minhasInformacoesSucesso"),
					Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	public void atualizarDadosUsuarioMinhasInformacoes(Usuario usuario) throws NegocioException {
		try {
			boolean usuarioExterno = this.seguranca.getUsuario().getFuncaoUsuarioEnum()
					.equals(FuncaoUsuarioEnum.USUARIO_EXTERNO);
			this.encoder = new Md5PasswordEncoder();
			String hashedPass = this.encoder.encodePassword(usuario.getSenha(), null);
			if (hashedPass.equals(this.seguranca.getUsuario().getSenha())) {
				this.setMd5PasswordEncoder(usuario);
				usuario.setStatusUsuarioEnum(StatusUsuarioEnum.ATIVO);
				if (usuarioExterno) {
					this.salvarContatosUsuario(usuario);
				}
				this.usuarioDAO.update(usuario);
				RequestContext.getCurrentInstance().addCallbackParam("autenticar", Boolean.TRUE);
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("minhasInformacoesSucesso"),
						Boolean.TRUE);
			} else {
				throw new NegocioException(
						FacesUtil.propertiesLoader().getProperty("minhasInformacoesSenhaAtualNaoConfere"),
						Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	/**
	 * @author thiago
	 * @author manoel
	 * @param inicioDaPagina
	 * @param tamanhoDaPagina
	 * @param campo
	 * @param valor
	 * @return
	 */
	public List<Usuario> paginarUsuarios(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo, String valor) {
		List<Usuario> usuarios = null;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			switch (campo) {
			case "id":
				condicao += String.format(" AND %s = %s", campo, valor);
				break;
			case "nome":
			case "login":
			case "funcaoUsuarioEnum":
			case "statusUsuarioEnum":
			case "tipoUsuarioEnum":
				condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(usuario." + campo + ")", valor.toUpperCase());
				break;

			}
		}

		condicao += " ORDER BY usuario.id DESC";
		usuarios = usuarioDAO.paginarUsuarios(inicioDaPagina, tamanhoDaPagina, condicao);
		return usuarios;
	}

	public String filtrarUsiariosFuncaoAdmin(String condicao) {
		boolean admin = this.seguranca.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.ADMINISTRADOR);
		if (!admin) {
			condicao += " AND usuario.funcaoUsuarioEnum != 'ADMINISTRADOR'";
		}

		return condicao;

	}

	/**
	 * @author thiago
	 * @author manoel
	 * @param inicioDaPagina
	 * @param tamanhoDaPagina
	 * @param campo
	 * @param valor
	 * @return
	 */
	public int contarUsuariosCadastrados(String campo, String valor) {
		int total = 0;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			switch (campo) {
			case "id":
				condicao += String.format(" AND %s = %s", campo, valor);
				break;
			case "nome":
			case "email.email":
			case "login":
			case "funcaoUsuarioEnum":
			case "statusUsuarioEnum":
			case "tipoUsuarioEnum":
				condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(usuario." + campo + ")", valor.toUpperCase());
				break;
			}
		}

		total = usuarioDAO.count(Usuario.class, condicao).intValue();
		return total;
	}

	public void salvarUsuario(Usuario usuario) throws NegocioException {
		try {
//			boolean edicao = false;
			if (!this.usuarioExiste(usuario)) {
				if (usuario.getId() == null) {
					usuario.setDataAtivacao(new Date());
				} else {
//					edicao = true;
				} 
				if (usuario.getId() == null) {
					this.setMd5PasswordEncoder(usuario);
				}
				if (!StringUtil.valalidarEmail(usuario.getEmail().getEmail())) {
					throw new NegocioException(FacesUtil.propertiesLoader().getProperty("manifestacaoEmailInvalido"),
							Boolean.FALSE);
				}
				this.salvarContatosUsuario(usuario);
				usuarioDAO.update(usuario);
				// EmailNotificacao emailNotificacao = emailNotificacaoDAO
				// .buscarEmailNotificacaoPorTipoEmailNotificacaoAtivoPassandoCodigoEmailNotificacao(27l);
				// if (!edicao) {
				// enviadorEmailService.enviarEmailRecuperarSenha(usuario,
				// usuario.getConfirmacaoSenha(),
				// emailNotificacao);
				// }
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioExistente"), Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	private void salvarContatosUsuario(Usuario usuario) throws NegocioException {
		usuario.getEmail().setPrincipalManifestacao(Boolean.FALSE);
		if (usuario.getEmail().getId() != null) {
			this.emailService.getEmailDAO().update(usuario.getEmail());
		} else {
			this.emailService.getEmailDAO().insert(usuario.getEmail());
		}
		this.telefoneService.getPersistencia().update(usuario.getTelefone());
	}

	public void salvarUsuarioExterno(Usuario usuario) throws NegocioException {
		try {
//			if (!usuarioDAO.buscarUsuariosExternoPassandoEmail(usuario.getEmail().getEmail()).isEmpty()) {
//				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioEmailIndisponivel"),
//						Boolean.FALSE);
//			}
			if (!this.usuarioExiste(usuario)) {
				usuario.setDataAtivacao(new Date());
				usuario.setPerfis(null);
				usuario.setFuncaoUsuarioEnum(FuncaoUsuarioEnum.USUARIO_EXTERNO);
				usuario.setStatusUsuarioEnum(StatusUsuarioEnum.ATIVO);
				this.setMd5PasswordEncoder(usuario);
				this.salvarContatosUsuario(usuario);
				if (!StringUtil.valalidarEmail(usuario.getEmail().getEmail())) {
					throw new NegocioException(FacesUtil.propertiesLoader().getProperty("manifestacaoEmailInvalido"),
							Boolean.FALSE);
				}
				usuarioDAO.update(usuario);
				// EmailNotificacao emailNotificacao = emailNotificacaoDAO
				// .buscarEmailNotificacaoPorTipoEmailNotificacaoAtivoPassandoCodigoEmailNotificacao(26l);
				// enviadorEmailService.enviarEmailRecuperarSenha(usuario,
				// usuario.getConfirmacaoSenha(),
				// emailNotificacao);
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioExistente"), Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	private void setMd5PasswordEncoder(Usuario usuario) {
		this.encoder = new Md5PasswordEncoder();
		String hashedPass = this.encoder.encodePassword(usuario.getConfirmacaoSenha(), null);
		usuario.setSenha(hashedPass);
	}

	public void excluirUsuario(Usuario usuario) throws NegocioException {
		try {
			this.validarExclusao(usuario);
			this.usuarioDAO.delete(Usuario.class, usuario.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	public void validarExclusao(Usuario usuario) throws NegocioException {
		if (this.getPerfilService().getPerfilDAO().buscarPerfisUsuarioPassandoIdUsuario(usuario.getId()).size() > 0) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioInvalidaExclusaoPerfil"),
					Boolean.FALSE);
		}
	}

	public boolean usuarioExiste(Usuario usuario) {
		String tudo[] = StringUtil.validarTrim(usuario.getNome(), usuario.getLogin());
		usuario.setNome(tudo[0]);
		usuario.setLogin(tudo[1]);

		Usuario auxiliar = this.usuarioDAO.buscarUsuarioPorLogin(usuario.getLogin());
		boolean retorno = false;
		if (auxiliar != null && usuario.getLogin().toUpperCase().equals(auxiliar.getLogin().toUpperCase())
				&& (usuario.getId() != null && usuario.getId().equals(auxiliar.getId()))) {
			retorno = false;
		} else if (auxiliar == null) {
			retorno = false;
		} else {
			retorno = true;
		}
		return retorno;
	}

	public boolean usuarioExiste(String login) {
		try {
			Usuario auxiliar = this.usuarioDAO.buscarUsuarioPorLogin(login);
			if (auxiliar != null)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Usuario> buscarUsuariosDistintosOrdenadosPorNome() {
		List<Usuario> usuariosOrdenados = this.usuarioDAO.buscarUsuariosOrdenadosPorNome();
		TreeSet<Usuario> usuariosDistintoTreeSet = usuariosOrdenados.stream().collect(
				Collectors.toCollection(() -> new TreeSet<Usuario>((p1, p2) -> p1.getNome().compareTo(p2.getNome()))));
		List<Usuario> emailsDistintos = new ArrayList<Usuario>(usuariosDistintoTreeSet);
		return emailsDistintos;
	}

	public List<Usuario> retornarTodosUsuarios() {
		List<Usuario> lista = new ArrayList<>();
		lista = usuarioDAO.getList(Usuario.class);
		return lista;
	}

	public List<Usuario> retornarTodosUsuariosOrdenados() {
		List<Usuario> lista = new ArrayList<>();
		lista = usuarioDAO.retornaTodosUsuariosOrdenados();
		return lista;
	}

	// public void novaSenhaCheckInformacao(Manifestacao manifestacao, String email)
	// throws NegocioException {
	// if (manifestacao == null)
	// throw new
	// NegocioException(FacesUtil.propertiesLoader().getProperty("manifestacaoNenhuma"));
	// if (!manifestacao.getEmailPrincipal().equals(email))
	// throw new
	// NegocioException(FacesUtil.propertiesLoader().getProperty("minhasInformacoesEMailCorreto"));
	// }

	public void novaSenhaCheckInformacao(Usuario usuario, String nome, String email, String login)
			throws NegocioException {
		if (!this.usuarioExiste(login))
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioNenhum"));
		if (!usuario.getNome().equals(nome))
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("minhasInformacoesNomeCorreto"));
		if (!usuario.getEmail().getEmail().equals(email))
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("minhasInformacoesEMailCorreto"));
		if (!usuario.getLogin().equals(login))
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("usuarioNenhum"));
	}

	@SuppressWarnings("unused")
	private Usuario buscarUsuarioPorLogin(String login) throws NegocioException {
		try {
			return this.usuarioDAO.buscarUsuarioPorLogin(login);
		} catch (Exception e) {
			throw new NegocioException("ERRO");
		}
	}

	public Paginacao registroLazyDataModel(String filtroSelecionado, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, String limitOffset) {
		Paginacao paginacao = new Paginacao();
		String filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
		boolean update = true;
		try {
			if (filtroSelecionado != null && filtroSelecionado.equals("")) {
				filtroSelecionado = null;
			}
			List<Usuario> registros = null;
			if (((this.paginateSessionScoped.getFiltroSelecionado() != null && filtroSelecionado == null)
					|| (this.paginateSessionScoped.isLimitOffset() && filtroSelecionado == null))
					&& limitOffset == null) {
				int page = (this.paginateSessionScoped.getFirst() / this.paginateSessionScoped.getPageSize());
//				int firstSessionScoped = this.paginateSessionScoped.getFirst();
				int pageSizeSessionScoped = this.paginateSessionScoped.getPageSize();
				// registros = paginarUsuarios(firstSessionScoped, pageSizeSessionScoped,
				// this.paginateSessionScoped.getFiltroSelecionado(),
				// this.paginateSessionScoped.getFiltroValor(),
				// this.paginateSessionScoped.getUnidade());
				// paginacao.setTotalDeRegistros(
				// contarUsuariosCadastrados(this.paginateSessionScoped.getFiltroSelecionado(),
				// this.paginateSessionScoped.getFiltroValor(),
				// this.paginateSessionScoped.getUnidade()));
				if (!this.paginateSessionScoped.isUpdate()) {
					paginacao.setTotalDeRegistrosReal(paginacao.getTotalDeRegistros());
					paginacao.setTotalDeRegistros(pageSizeSessionScoped + 1);
					paginacao.setUpdate(true);
				} else {
					paginacao.setUpdate(false);
					if (paginacao.getTotalDeRegistros() < pageSize + 1) {
						paginacao.setTotalDeRegistrosReal(paginacao.getTotalDeRegistros());
						paginacao.setTotalDeRegistros(pageSizeSessionScoped + 1);
						paginacao.setUpdate(true);
						update = false;
					} else {
						update = true;
					}
				}
				FacesUtil.execute("PF('usuarios').getPaginator().setPage(" + page + ");");
			} else {
				if (filtroSelecionado == null || (filtroSelecionado != null && filtroSelecionado.equals("default"))
						&& (limitOffset != null && !limitOffset.equals("todos"))) {
					filtroSelecionado = this.paginateSessionScoped.getFiltroSelecionado() != null
							? this.paginateSessionScoped.getFiltroSelecionado()
							: filtroSelecionado;
					filtroValor = this.paginateSessionScoped.getFiltroValor() != null
							? this.paginateSessionScoped.getFiltroValor()
							: filtroValor;
					// unidade = this.paginateSessionScoped.getUnidade() != null ?
					// this.paginateSessionScoped.getUnidade()
					// : unidade;
				}
				// registros = paginarUsuarios(first, pageSize, filtroSelecionado, filtroValor,
				// unidade);
				// paginacao.setTotalDeRegistros(contarUsuariosCadastrados(filtroSelecionado,
				// filtroValor, unidade));
				this.paginateSessionScoped.initLimitOffSet(first, pageSize);
				this.paginateSessionScoped.setFirst(first);
				this.paginateSessionScoped.setPageSize(pageSize);
				this.paginateSessionScoped.setFiltroValor(filtroValor, filtroSelecionado);
				this.paginateSessionScoped.setFiltroSelecionado(filtroSelecionado, filtroSelecionado);
				// this.paginateSessionScoped.setUnidade(unidade, filtroSelecionado);
				this.paginateSessionScoped.setFiltroValor(filtroValor, filtroSelecionado);
				if (paginacao.getTotalDeRegistros() < pageSize + 1) {
					update = false;
				} else {
					update = true;
				}
				if ((limitOffset != null && ((limitOffset.equals("atualizar") || limitOffset.equals("redefinir")))
						&& paginacao.getTotalDeRegistros() < pageSize + 1)) {
					paginacao.setTotalDeRegistrosReal(paginacao.getTotalDeRegistros());
					paginacao.setTotalDeRegistros(pageSize + 1);
					paginacao.setUpdate(true);
				}
			}
			this.paginateSessionScoped.setUpdate(update);
			paginacao.setUsuarios(registros);
			if (limitOffset != null && limitOffset.equals("atualizar")) {
				FacesUtil.execute(
						"document.getElementById('fo-usuario:hiddenlimitOffset').value = 'vazio'; mostrarMensagemAtualizarRegistros();");
			} else if (limitOffset != null && limitOffset.equals("redefinir")) {
				FacesUtil.execute(
						"document.getElementById('fo-usuario:hiddenlimitOffset').value = 'vazio'; mostrarMensagemSucessoSenhaRedefinirManifestante();");
			} else {
				FacesUtil.execute("atualizarRegistros();");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paginacao;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public PerfilService getPerfilService() {
		return perfilService;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public FuncaoUsuarioEnum[] getFuncoes() {
		FuncaoUsuarioEnum[] funcoes;
		int i = 0;

		funcoes = new FuncaoUsuarioEnum[2];

		for (FuncaoUsuarioEnum funcao : FuncaoUsuarioEnum.values()) {
			if (!funcao.equals(FuncaoUsuarioEnum.ADMINISTRADOR) && !funcao.equals(FuncaoUsuarioEnum.USUARIO_EXTERNO)
					&& !funcao.equals(FuncaoUsuarioEnum.ROOT)) {
				funcoes[i] = funcao;
				i++;
			}
		}
		return funcoes;
	}

	public EnviadorEmailService getEnviadorEmailService() {
		return enviadorEmailService;
	}
}
