package br.com.sandubas.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.sandubas.model.enums.FuncaoUsuarioEnum;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication, request);
		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Builds the target URL according to the logic defined in the main class
	 * Javadoc.
	 * @throws Exception 
	 */
	protected String determineTargetUrl(Authentication authentication, HttpServletRequest request) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsuarioSistema usuario = (UsuarioSistema) userDetails;
		String id = request.getParameter("id");
		String x = request.getParameter("g-recaptcha-response");
		if(x == null || x.isEmpty()) {
			throw new IOException("Resolva o Captcha");
		}
		if (id != null) {
			if (usuario.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.USUARIO_EXTERNO)) {
				return "/pages/manifestacao/manifestante/acompanharManifestacao.xhtml?manifestacaoId=" + id;
			} else {
				return "/pages/manifestacao/administrar.xhtml?id=" + id;
			}

		} else {
			if (usuario.getUsuario().getFuncaoUsuarioEnum().equals(FuncaoUsuarioEnum.USUARIO_EXTERNO)) {
				//TODO usuario externo 
				return "";
			} else {
				return "/pages/principal.xhtml";
			}
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
