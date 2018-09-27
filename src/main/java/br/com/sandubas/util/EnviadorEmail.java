package br.com.sandubas.util;

import java.io.Serializable;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.report.ReportLoader;

public class EnviadorEmail implements Serializable {

	private static final long serialVersionUID = 6898260035636385622L;
	private Properties properties;
	private Session session;

	public void enviarEmail(String destinatario, String assunto, String corpo,
			String... anexos) throws NegocioException {
		try {
//			MimeMessage message = new MimeMessage(getSession(preferencias));
//			message.setFrom(new InternetAddress(preferencias.getUsuarioEmail()));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
//			message.setSubject(assunto, "utf-8");

			BodyPart conteudo = new MimeBodyPart();
			conteudo.setContent(corpo, "text/html; charset=\"utf-8\"");
			conteudo.setHeader("Content-Type", "text/html; charset=\"utf-8\"");

			Multipart conteudoEAnexos = new MimeMultipart();
			conteudoEAnexos.addBodyPart(conteudo);

			for (String nomeAnexo : anexos) {
				BodyPart anexo = new MimeBodyPart();
				DataSource source = new FileDataSource(ReportLoader.class.getResource("").getPath() + nomeAnexo);
				anexo.setDataHandler(new DataHandler(source));
				anexo.setFileName(nomeAnexo);
				conteudoEAnexos.addBodyPart(anexo);
			}

//			message.setContent(conteudoEAnexos);
//			Transport.send(message);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
//			properties.put("mail.smtp.host", preferencias.getHostEmail());
//			properties.put("mail.smtp.socketFactory.port", preferencias.getPortaEmail());
//			if (preferencias.getSslEmail()) {
//				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			}
			properties.put("mail.smtp.auth", "true");
//			properties.put("mail.smtp.port", preferencias.getPortaEmail());
		}
		return properties;
	}

	public Session getSession(Object preferencias) {
//		session = Session.getDefaultInstance(getProperties(preferencias), new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(preferencias.getUsuarioEmail(), preferencias.getSenhaEmail());
//			}
//		});
		return null;
	}

}
