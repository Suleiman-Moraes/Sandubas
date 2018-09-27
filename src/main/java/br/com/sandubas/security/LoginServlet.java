package br.com.sandubas.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sandubas.helper.ValidacaoHelper;

@WebServlet(description = "Servlet responsável por efetuar o login do usuário", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGGED_INDEX_PAGE = "/pages/manifestacao/listarmanifestacoes.xhtml";
	@SuppressWarnings("unused")
	private static final String MANIFESTATION_DETAIL_PAGE = "/pages/manifestacao/administrar.xhtml?num=%s&id=%s";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendRedirect(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		List<String> errors = new ArrayList<String>();
		try {
			if (request.getUserPrincipal() == null) {
				String username = request.getParameter("j_username");
				String password = request.getParameter("j_password");

				if (ValidacaoHelper.isEmpty(username)) {
					errors.add("Usuário é obrigatório");
				}

				if (ValidacaoHelper.isEmpty(password)) {
					errors.add("Senha é obrigatório");
				}

				if (ValidacaoHelper.isNotEmpty(errors)) {
					throw new Exception("Campos obrigatórios não preenchidos");
				}

			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
			dispatcher.forward(request, response);			
			
			//sendRedirect(request, response);
		} catch (Exception e) {
			String errorMessage = (String) request.getAttribute("errorMessage");
			if (ValidacaoHelper.isNotEmpty(errorMessage)) {
				errors.add(errorMessage);
			}

			request.setAttribute("errors", errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pageToSend = request.getContextPath();

		if (request.getUserPrincipal() != null) {
			LoginTypeEnum loginType = (LoginTypeEnum) request.getAttribute("loginType");

			if (loginType == null) {
				pageToSend = pageToSend.concat(LOGGED_INDEX_PAGE);
			} else {
				switch (loginType) {
				case USER:
					pageToSend = pageToSend.concat(LOGGED_INDEX_PAGE);
					break;
				case MANIFESTATION: {
					// TbManifestacao manifestation = (TbManifestacao)
					// request.getAttribute("manifestation");
					// pageToSend =
					// pageToSend.concat(String.format(MANIFESTATION_DETAIL_PAGE,
					// manifestation.getNrManifestacao(),
					// manifestation.getIdManifestacao()));
					break;
				}
				case NEW_PASSWORD:
					pageToSend = pageToSend.concat("/novasenha.xhtml");
					break;
				}
			}
		} else {
			pageToSend = pageToSend.concat("/login.jsp");
		}

		response.sendRedirect(pageToSend);
	}

}
