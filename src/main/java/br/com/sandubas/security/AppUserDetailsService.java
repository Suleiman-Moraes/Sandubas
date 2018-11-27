package br.com.sandubas.security;

import static br.com.sandubas.model.enums.FuncionalidadeEnum.MANTER_CRUD_SIMPLES;

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
import br.com.sandubas.util.jsf.FacesUtil;

public class AppUserDetailsService implements UserDetailsService {

	private FuncaoUsuarioEnum profile;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioDAO usuarioDAO = CDIServiceLocator.getBean(UsuarioDAO.class);
		Usuario usuario = usuarioDAO.login(username);
		if(usuario.getStatusUsuarioEnum() == null || usuario.getStatusUsuarioEnum().equals(StatusUsuarioEnum.INATIVO)) {
			throw new UsuarioInativoException(FacesUtil.propertiesLoader().getProperty("usuarioInativo"));
		}
		UsuarioSistema user = null;
		profile = EnumHelper.getFuncaoUsuarioEnum(usuario.getFuncaoUsuarioEnum().getId(), usuario);
		if (profile == null) {
			throw new UsuarioSemPerfilException("Usuário sem perfil associado !");
		} else {
			user = new UsuarioSistema(usuario, getPerfis(usuario), getGrupos(usuario, usuarioDAO, profile),
					usuario.getPerfilCliente(), usuario.getPerfilFuncionario(), usuario.getPerfilAdministrador(),
					usuario.getPerfilOperador(), usuario.getPerfilROOT());
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

		if (profile == null) {
			//TODO Fazer alguma coisa
		} else if (profile.equals(FuncaoUsuarioEnum.ADMINISTRADOR)) {
			Stream.of(FuncionalidadeEnum.values()).map(Enum::name).collect(Collectors.toList())
					.forEach(funcionalidade -> authorities.add(new SimpleGrantedAuthority(funcionalidade)));
		} else {
			Arrays.asList(MANTER_CRUD_SIMPLES)
					.forEach(funcionalidade -> authorities.add(new SimpleGrantedAuthority(funcionalidade.toString())));

			List<String> permissoes = usuarioDAO.permissoesPassandoUsuarioAutenticacaoSpringSecurity(usuario);

			permissoes.forEach(
					p -> authorities.add(new SimpleGrantedAuthority(FuncionalidadeEnum.retornaSituacao(p).toString())));

		}

		return authorities;
	}

}
