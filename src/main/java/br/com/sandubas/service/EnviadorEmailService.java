package br.com.sandubas.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.com.sandubas.dao.EmailDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Usuario;
import br.com.sandubas.util.EnviadorEmail;

public class EnviadorEmailService implements Serializable {

	private static final long serialVersionUID = 6625921239453540654L;

	@Inject
	public EnviadorEmail enviadorEmail;

	@Inject
	private EmailDAO emailDAO;

	private static final Logger log = Logger.getLogger(EnviadorEmailService.class.getName());

	public void enviarEmailRecuperarSenhaManifestacao(String novaSenha) throws NegocioException {
		new Thread(new Runnable() {
			public void run() {
				try {
					String assunto = "";
					String corpo = "";
					enviadorEmail.enviarEmail("", assunto, corpo);
				} catch (Exception e) {
					log.severe(e.getMessage());
				}
			}
		}).start();
	}

	public void enviarEmailNotificacaoUsuarioOperador(Usuario usuario, Usuario usuarioLogado, String urlBase) throws NegocioException {
		try {
			String assunto = "";
			String corpo = "";
			enviadorEmail.enviarEmail(usuario.getEmail().getEmail(), assunto, corpo);
		} catch (Exception e) {
			throw new NegocioException("Erro ao enviar o email, tente novamente. Erro: " + e.getMessage());
		}
	}

	public EmailDAO getEmailDAO() {
		return emailDAO;
	}

}
