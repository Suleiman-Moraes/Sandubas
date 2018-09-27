package br.com.sandubas.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

import br.com.sandubas.model.enums.FuncaoUsuarioEnum;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
			throws IOException, ServletException {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsuarioSistema usuario = (UsuarioSistema) userDetails;

		if (!request.getRequestURI().equals("/sandubas/")) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth.isAuthenticated() && request.getRequestURI().equals("/sandubas/login.jsp")
					&& usuario.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.MANIFESTANTE)) {
				response.sendRedirect(
						request.getContextPath() + "/pages/principal.xhtml");

			} else if (auth.isAuthenticated() && request.getRequestURI().equals("/sandubas/login.jsp")
					&& !usuario.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.MANIFESTANTE)) {
				response.sendRedirect(request.getContextPath() + "/pages/principal.xhtml");
			} else {
				response.sendRedirect(request.getContextPath() + "/pages/erro/404.xhtml");
			}
		} else {
			if (usuario.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.MANIFESTANTE)) {
				response.sendRedirect(
						request.getContextPath() + "/pages/principal.xhtml");
			} else {
				response.sendRedirect(request.getContextPath() + "/pages/principal.xhtml");
			}
		}
	}
}