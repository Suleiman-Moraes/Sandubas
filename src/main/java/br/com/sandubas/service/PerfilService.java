package br.com.sandubas.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.sandubas.dao.FuncionalidadeDAO;
import br.com.sandubas.dao.PerfilDAO;
import br.com.sandubas.dao.UsuarioDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.util.StringUtil;
import br.com.sandubas.util.jsf.FacesUtil;

public class PerfilService implements Serializable {

	private static final long serialVersionUID = 7630507668274613371L;

	@Inject
	private PerfilDAO perfilDAO;

	@Inject
	private FuncionalidadeDAO funcionalidadeDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	public void salvarPerfil(Perfil perfil) throws NegocioException {
		try {
			if (!this.perfilExiste(perfil)) {
				perfilDAO.update(perfil);
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("perfilExistente"), Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	public void excluirPerfil(Perfil perfil) throws NegocioException {
		try {
			this.validarExclusao(perfil);
			this.perfilDAO.delete(Perfil.class, perfil.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("perfilExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	public void validarExclusao(Perfil perfil) throws NegocioException {
		if (this.usuarioDAO.buscarUsuarioPassandoPerfil(perfil).size() > 0) {
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("perfilInvalidaExclusaoUsuario"),
					Boolean.FALSE);
		}
	}

	public boolean perfilExiste(Perfil perfil) {
		String tudo[] = StringUtil.validarTrim(perfil.getNome(), perfil.getDescricao());
		perfil.setNome(tudo[0]);
		perfil.setDescricao(tudo[1]);

		Perfil auxiliar = this.perfilDAO.buscarPerfilPorNome(perfil.getNome());
		boolean retorno = false;
		if (auxiliar != null && perfil.getNome().toUpperCase().equals(auxiliar.getNome().toUpperCase())
				&& (perfil.getId() != null && perfil.getId().equals(auxiliar.getId()))) {
			retorno = false;
		} else if (auxiliar == null) {
			retorno = false;
		} else {
			retorno = true;
		}
		return retorno;
	}

	public PerfilDAO getPerfilDAO() {
		return perfilDAO;
	}

	public FuncionalidadeDAO getFuncionalidadeDAO() {
		return funcionalidadeDAO;
	}

	// ComboBox
	public List<Perfil> paginarPerfil(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo, String valor) {
		// TODO - refatorar.
		List<Perfil> perfil = null;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			switch (campo) {
			case "nome":
			case "descricao":
				condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(perfil." + campo + ")", valor.toUpperCase());
				break;
			case "id":
				condicao += String.format(" AND %s = %s", campo, valor);
				break;
			}
		}
		condicao += " ORDER BY perfil.id DESC";

		perfil = perfilDAO.paginarPerfil(inicioDaPagina, tamanhoDaPagina, condicao);

		return perfil;
	}

	public int contarPerfilCadastradas(String campo, String valor) {
		// TODO - refatorar
		int total = 0;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			switch (campo) {
			case "nome":
			case "descricao":
				condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(perfil." + campo + ")", valor.toUpperCase());
				break;
			case "id":
				condicao += String.format(" AND %s = %s", campo, valor);
				break;
			}
		}
		total = perfilDAO.count(Perfil.class, condicao).intValue();
		return total;
	}

	public List<Perfil> obterPerfilPassandoFuncaoUsuario(FuncaoUsuarioEnum funcaoUsuarioEnum) {
		StringBuilder select = new StringBuilder("SELECT p FROM Perfil p WHERE p.id IN ");

		switch (funcaoUsuarioEnum) {
		case ROOT:
			select.append("(1, 2)");
			break;
		case ADMINISTRADOR:
			select.append("(2)");
			break;
		case OPERADOR_CAIXA:
			select.append("(3, 4)");
			break;
		case FUNCIONARIO:
			select.append("(4)");
			break;
		default:
			break;
		}
		try {
			return perfilDAO.select(select.toString(), Perfil.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
