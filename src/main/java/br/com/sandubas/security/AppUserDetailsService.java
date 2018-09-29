package br.com.sandubas.security;

import static br.com.sandubas.model.enums.FuncionalidadeEnum.ATUALIZAR_MINHAS_INFORMACOES;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.GERENCIAR_MANIFESTACAO;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.GERENCIAR_MANIFESTACAO_MANIFESTANTE;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.MANTER_EMAILS_DE_NOTIFICACAO;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.MANTER_FILTROS_PERSONALIZADOS;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.MANTER_RESPOSTAS_MANIFESTACOES;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.MANTER_MENSAGEM_TEXTO_PADRONIZADO;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.REALIZAR_PESQUISA_NOS_REGISTROS;
import static br.com.sandubas.model.enums.FuncionalidadeEnum.REGISTRAR_MANIFESTACAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.sandubas.dao.UsuarioDAO;
import br.com.sandubas.exception.UsuarioInativoException;
import br.com.sandubas.exception.UsuarioSemPerfilException;
import br.com.sandubas.helper.EnumHelper;
import br.com.sandubas.model.Perfil;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.model.enums.FuncaoUsuarioEnum;
import br.com.sandubas.model.enums.FuncionalidadeEnum;
import br.com.sandubas.model.enums.StatusUsuarioEnum;
import br.com.sandubas.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	private FuncaoUsuarioEnum profile;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioDAO usuarioDAO = CDIServiceLocator.getBean(UsuarioDAO.class);
		Usuario usuario = usuarioDAO.login(username);
		UsuarioSistema user = null;
		profile = EnumHelper.getFuncaoUsuarioEnum(usuario.getFuncaoUsuarioEnum().getId(), usuario);
			if (profile == null) {
				throw new UsuarioSemPerfilException("Usuário sem perfil associado !");
			} else {
				user = new UsuarioSistema(usuario, getPerfis(usuario), getGrupos(usuario, usuarioDAO, profile),
						usuario.getPerfilAdm(), usuario.getPerfilOuvidor(), usuario.getPerfilInterlocutor(),
						usuario.getPerfilOperador());
				return user;
			}
	}

	private List<Perfil> getPerfis(Usuario usuario) {
		List<Perfil> perfis = new ArrayList<Perfil>(0);
		perfis.addAll(usuario.getPerfis());
		return perfis;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario, UsuarioDAO usuarioDAO,
			FuncaoUsuarioEnum profile) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>(0);

		if (usuario.getStatusUsuarioEnum().equals(StatusUsuarioEnum.NOVA_SENHA)) {
			Arrays.asList(ATUALIZAR_MINHAS_INFORMACOES)
					.forEach(funcionalidade -> authorities.add(new SimpleGrantedAuthority(funcionalidade.toString())));
		} else {
			if (profile == null) {
				authorities.add(new SimpleGrantedAuthority(REGISTRAR_MANIFESTACAO.toString()));
			} else if (profile.equals(FuncaoUsuarioEnum.ADMINISTRADOR)) {
				Stream.of(FuncionalidadeEnum.values()).map(Enum::name).collect(Collectors.toList())
						.forEach(funcionalidade -> authorities.add(new SimpleGrantedAuthority(funcionalidade)));
			} else if (profile.equals(FuncaoUsuarioEnum.OPERADOR) || profile.equals(FuncaoUsuarioEnum.INTERLOCUTOR)
					|| profile.equals(FuncaoUsuarioEnum.OUVIDOR)) {
				Arrays.asList(GERENCIAR_MANIFESTACAO, ATUALIZAR_MINHAS_INFORMACOES, REALIZAR_PESQUISA_NOS_REGISTROS)
						.forEach(funcionalidade -> authorities
								.add(new SimpleGrantedAuthority(funcionalidade.toString())));

				if (profile.equals(FuncaoUsuarioEnum.OUVIDOR)) {

					Arrays.asList(REGISTRAR_MANIFESTACAO, MANTER_FILTROS_PERSONALIZADOS, MANTER_EMAILS_DE_NOTIFICACAO,
							MANTER_MENSAGEM_TEXTO_PADRONIZADO, MANTER_RESPOSTAS_MANIFESTACOES)
							.forEach(funcionalidade -> authorities
									.add(new SimpleGrantedAuthority(funcionalidade.toString())));
				}

				List<String> permissoes = usuarioDAO.permissoesPassandoUsuarioAutenticacaoSpringSecurity(usuario);

				permissoes.forEach(p -> authorities
						.add(new SimpleGrantedAuthority(FuncionalidadeEnum.retornaSituacao(p).toString())));

			} else if (profile.equals(FuncaoUsuarioEnum.MANIFESTANTE)) {
				Arrays.asList(ATUALIZAR_MINHAS_INFORMACOES, GERENCIAR_MANIFESTACAO_MANIFESTANTE).forEach(
						funcionalidade -> authorities.add(new SimpleGrantedAuthority(funcionalidade.toString())));
			}
		}

		return authorities;
	}

}
