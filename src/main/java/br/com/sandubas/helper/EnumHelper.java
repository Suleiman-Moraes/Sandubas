package br.com.sandubas.helper;

import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.FuncionalidadeEnum;
import br.com.sandubas.model.enums.LogOperacaoEnum;
import br.com.sandubas.model.enums.SexoEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;

/**
 * @author Samuel Correia Guimarães
 * @author Suleiman Alves de Moraes
 */
public class EnumHelper {

	public static SexoEnum getSexoEnum(String id) {
		switch (id) {
		case "1":
			return SexoEnum.MASCULINO;
		case "2":
			return SexoEnum.FEMININO;
		case "3":
			return SexoEnum.NAO_INFORMADO;
		default:
			return null;
		}
	}

	public static LogOperacaoEnum getLogOperacaoEnum(String id) {
		switch (id) {
		case "1":
			return LogOperacaoEnum.INSERIR;
		case "2":
			return LogOperacaoEnum.ALTERAR;
		case "3":
			return LogOperacaoEnum.EXCLUIR;
		case "4":
			return LogOperacaoEnum.CONSULTAR;
		default:
			return null;
		}
	}

	public static StatusUsuarioEnum getStatusUsuarioEnum(Integer id) {
		switch (id) {
		case 0:
			return StatusUsuarioEnum.INATIVO;
		case 1:
			return StatusUsuarioEnum.ATIVO;
		default:
			return null;
		}
	}

	public static FuncionalidadeEnum getFuncionalidadeEnum(Integer id) throws Exception {
		switch (id) {
		case 1:
			return FuncionalidadeEnum.ATUALIZAR_MINHAS_INFORMACOES;
		case 2:
			return FuncionalidadeEnum.CONFIGURAR_PREFERENCIAS_DE_SISTEMA;
		case 3:
			return FuncionalidadeEnum.MANTER_CRUD_SIMPLES;
		}
		throw new Exception("Funcionalidade não listada.");
	}

	public static FuncaoUsuarioEnum getFuncaoUsuarioEnum(String id, Usuario usuario) {
		usuario.setPerfilROOT(Boolean.FALSE);
		usuario.setPerfilOperador(Boolean.FALSE);
		usuario.setPerfilAdministrador(Boolean.FALSE);
		usuario.setPerfilCliente(Boolean.FALSE);
		usuario.setPerfilFuncionario(Boolean.FALSE);
		switch (id) {
		case "1":
			usuario.setPerfilROOT(Boolean.TRUE);
			return FuncaoUsuarioEnum.ROOT;
		case "2":
			usuario.setPerfilAdministrador(Boolean.TRUE);
			return FuncaoUsuarioEnum.ADMINISTRADOR;
		case "3":
			usuario.setPerfilOperador(Boolean.TRUE);
			return FuncaoUsuarioEnum.OPERADOR_CAIXA;
		case "4":
			usuario.setPerfilFuncionario(Boolean.TRUE);
			return FuncaoUsuarioEnum.FUNCIONARIO;
		case "5":
			return FuncaoUsuarioEnum.USUARIO_EXTERNO;
		default:
			return null;
		}
	}
}
