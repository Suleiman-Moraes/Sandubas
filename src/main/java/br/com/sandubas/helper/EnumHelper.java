package br.com.sandubas.helper;

import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.FuncionalidadeEnum;
import br.com.sandubas.model.enums.LogOperacaoEnum;
import br.com.sandubas.model.enums.SexoEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;

/**
 * @author Samuel Correia Guimarães
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

	public static FuncaoUsuarioEnum getFuncaoUsuarioEnum(String FuncaoUsuarioEnumId, Usuario usuario) {
		boolean adm = Boolean.FALSE;
		boolean ouvidor = Boolean.FALSE;
		boolean interlocutor = Boolean.FALSE;
		boolean operador = Boolean.FALSE;

		for (Perfil p : usuario.getPerfis()) {
			if (usuario.getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.OUVIDOR)) {
				if (p.getId().equals(new Long(1l))) { // perfil administrador
					adm = (Boolean.TRUE);
				} else if (p.getId().equals(new Long(2l))) { // perfil ouvidor
					ouvidor = (Boolean.TRUE);
				} else if (p.getId().equals(new Long(4))) { // perfil operador
					operador = (Boolean.TRUE);
				}
			} else if (usuario.getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.INTERLOCUTOR)) {
				if (p.getId().equals(new Long(3l))) { // perfil interlocutor
					interlocutor = (Boolean.TRUE);
				} else if (p.getId().equals(new Long(4l))) { // perfil operador
					operador = (Boolean.TRUE);
				}
			} else if (usuario.getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.OPERADOR)) {
				if (p.getId().equals(new Long(4l))) { // perfil operador
					operador = (Boolean.TRUE);
				}
			}
		}
		switch (FuncaoUsuarioEnumId) {
		case "1":
			if (adm) {
				usuario.setPerfilAdm(adm);
			} else if (ouvidor) {
				usuario.setPerfilOuvidor(ouvidor);
			} else if (operador) {
				usuario.setPerfilOperador(operador);
			}
			return FuncaoUsuarioEnum.OUVIDOR;
		case "2":
			if (interlocutor) {
				usuario.setPerfilInterlocutor(interlocutor);
			} else if (operador) {
				usuario.setPerfilOperador(operador);
			}
			return FuncaoUsuarioEnum.INTERLOCUTOR;
		case "3":
			if (operador) {
				usuario.setPerfilOperador(operador);
			}
			return FuncaoUsuarioEnum.OPERADOR;
		case "4":
			return FuncaoUsuarioEnum.MANIFESTANTE;
		case "5":
			return FuncaoUsuarioEnum.ADMINISTRADOR;
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
		case 2:
			return StatusUsuarioEnum.NOVA_SENHA;
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
			return FuncionalidadeEnum.CONSULTAR_PROJETOS_DO_SALIC;
		case 4:
			return FuncionalidadeEnum.CONSULTAR_PROPOSTAS_DO_SALIC;
		case 5:
			return FuncionalidadeEnum.GERENCIAR_MANIFESTACAO;
		case 6:
			return FuncionalidadeEnum.MANTER_AJUDA;
		case 7:
			return FuncionalidadeEnum.MANTER_AREA_DE_ENTRADA;
		case 8:
			return FuncionalidadeEnum.MANTER_AVISO;
		case 9:
			return FuncionalidadeEnum.MANTER_CLASSIFICACOES;
		case 10:
			return FuncionalidadeEnum.MANTER_EMAILS_DE_NOTIFICACAO;
		case 11:
			return FuncionalidadeEnum.MANTER_MENSAGEM_TEXTO_PADRONIZADO;
		case 12:
			return FuncionalidadeEnum.MANTER_FAIXA_ETARIA;
		case 13:
			return FuncionalidadeEnum.MANTER_FILTROS_PERSONALIZADOS;
		case 14:
			return FuncionalidadeEnum.MANTER_FILTROS_SPAM;
		case 15:
			return FuncionalidadeEnum.MANTER_GRUPO;
		case 16:
			return FuncionalidadeEnum.MANTER_LISTA_MANIFESTACAO;
		case 17:
			return FuncionalidadeEnum.MANTER_MEIO_DE_ENTRADA;
		case 18:
			return FuncionalidadeEnum.MANTER_MEIO_DE_RESPOSTA;
		case 19:
			return FuncionalidadeEnum.MANTER_PERFIL;
		case 20:
			return FuncionalidadeEnum.MANTER_PERFIL_X_FUNCIONALIDADES;
		case 21:
			return FuncionalidadeEnum.MANTER_PERFIL_X_GRUPO;
		case 22:
			return FuncionalidadeEnum.MANTER_PRIORIDADE;
		case 23:
			return FuncionalidadeEnum.MANTER_RESPOSTAS_MANIFESTACOES;
		case 24:
			return FuncionalidadeEnum.MANTER_SUBCLASSIFICACOES;
		case 25:
			return FuncionalidadeEnum.MANTER_TIPO_MANIFESTACAO;
		case 26:
			return FuncionalidadeEnum.MANTER_UNIDADE;
		case 27:
			return FuncionalidadeEnum.MANTER_USUARIO_X_PERFIL;
		case 28:
			return FuncionalidadeEnum.MANTER_USUARIOS;
		case 29:
			return FuncionalidadeEnum.PAINEL_DE_NOTIFICACAO_CONTROLE_PRAZOS;
		case 30:
			return FuncionalidadeEnum.REALIZAR_TRAMITE_UNIDADE;
		case 31:
			return FuncionalidadeEnum.REALIZAR_EXTRACAO_DE_DADOS;
		case 32:
			return FuncionalidadeEnum.REALIZAR_PESQUISA_NAS_MANIFESTACOES;
		case 33:
			return FuncionalidadeEnum.REALIZAR_PESQUISA_NOS_REGISTROS;
		case 34:
			return FuncionalidadeEnum.REGISTRAR_REGISTRO_EXTERNO;
		case 35:
			return FuncionalidadeEnum.REGISTRAR_MANIFESTACAO;
		case 36:
			return FuncionalidadeEnum.REGISTRAR_REGISTRO;
		case 37:
			return FuncionalidadeEnum.VISUALIZAR_ESTATISTICAS_DE_MANIFESTACAO;
		case 38:
			return FuncionalidadeEnum.VISUALIZAR_LOG_DE_OPERACOES;
		case 39:
			return FuncionalidadeEnum.MANTER_GRUPO_AJUDA;
		case 40:
			return FuncionalidadeEnum.VISUALIZAR_DADOS_SIGILOSOS;
		case 41:
			return FuncionalidadeEnum.MANTER_GRAU_INSTRUCAO;
		case 42:
			return FuncionalidadeEnum.GERAR_GRAFICOS;
		case 43:
			return FuncionalidadeEnum.MANTER_QUESTIONARIO;
		}
		throw new Exception("Funcionalidade não listada.");
	}
}
