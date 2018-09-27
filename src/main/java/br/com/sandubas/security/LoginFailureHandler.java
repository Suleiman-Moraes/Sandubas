package br.com.sandubas.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import br.com.sandubas.exception.UsuarioInativoException;
import br.com.sandubas.exception.UsuarioSemPerfilException;
import br.com.sandubas.helper.ValidacaoHelper;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		List<String> errors = new ArrayList<String>();
		if (exception.getCause() instanceof UsuarioInativoException) {
			errors.add("Usuário inativo");
			request.setAttribute("errors", errors);
		} else {
			if ((exception.getClass().isAssignableFrom(BadCredentialsException.class)
					|| exception.getClass().isAssignableFrom(AuthenticationServiceException.class)
							&& ((NullPointerException) exception.getCause()) == null)) {
				String username = request.getParameter("j_username");
				String password = request.getParameter("j_password");

				if (ValidacaoHelper.isEmpty(username)) {
					errors.add("Usuário é obrigatório");
				} else if (ValidacaoHelper.isEmpty(password)) {
					errors.add("Senha é obrigatório");
				} else {
					errors.add("Login ou senha inválidos");
				}

				if (ValidacaoHelper.isNotEmpty(errors)) {
					request.setAttribute("errors", errors);
				}
			} else if (exception.getCause() instanceof UsuarioSemPerfilException) {
				request.setAttribute("errors", errors);
				errors.add("Usuário sem perfil associado");

			} else {
				errors.add("Login ou senha inválidos");
				request.setAttribute("errors", errors);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

}
