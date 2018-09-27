package br.com.sandubas.exception;

import java.util.List;

public class NegocioException extends Exception {

    private static final long serialVersionUID = -5618658079680419266L;
    private boolean typeException;
    private List<PersonException> personExceptions;
    private String wizardPessoaJuridca;

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, boolean typeException) {
        super(message);
        this.typeException = typeException;
    }

    public NegocioException(List<PersonException> personExceptions) {
        this.personExceptions = personExceptions;
    }

    public NegocioException(String message, boolean typeException, List<PersonException> personExceptions) {
        super(message);
        this.typeException = typeException;
        this.personExceptions = personExceptions;
    }

    public NegocioException(String message, boolean typeException, String wizardPessoaJuridca) {
        super(message);
        this.typeException = typeException;
        this.wizardPessoaJuridca = wizardPessoaJuridca;
    }

    public boolean isTypeException() {
        return typeException;
    }

    public void setTypeException(boolean typeException) {
        this.typeException = typeException;
    }

    public List<PersonException> getPersonExceptions() {
        return personExceptions;
    }

    public void setPersonExceptions(List<PersonException> personExceptions) {
        this.personExceptions = personExceptions;
    }

    public String getWizardPessoaJuridca() {
        return wizardPessoaJuridca;
    }

    public void setWizardPessoaJuridca(String wizardPessoaJuridca) {
        this.wizardPessoaJuridca = wizardPessoaJuridca;
    }

}