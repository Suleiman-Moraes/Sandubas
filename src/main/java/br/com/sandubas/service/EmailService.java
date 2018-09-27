package br.com.sandubas.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.sandubas.dao.EmailDAO;
import br.com.sandubas.model.Email;

public class EmailService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EmailDAO emailDAO;

	@Inject
	private EnviadorEmailService enviadorEmailService;

	public List<Email> buscarEmailDistintosDosManifestantes() {
		List<Email> emails = null;
		emails = this.emailDAO.buscarEmailsDosManifestantes();
		TreeSet<Email> emailsDistintoTreeSet = emails.stream().collect(
				Collectors.toCollection(() -> new TreeSet<Email>((p1, p2) -> p1.getEmail().compareTo(p2.getEmail()))));
		List<Email> emailsDistintos = new ArrayList<Email>(emailsDistintoTreeSet);
		return emailsDistintos;
	}

	public EnviadorEmailService getEnviadorEmailService() {
		return enviadorEmailService;
	}

	public EmailDAO getEmailDAO() {
		return emailDAO;
	}

}
