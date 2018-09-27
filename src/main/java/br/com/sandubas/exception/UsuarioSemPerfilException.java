package br.com.sandubas.exception;

public class UsuarioSemPerfilException extends RuntimeException {

	private static final long serialVersionUID = -5618658079680419266L;

	public UsuarioSemPerfilException(String message) {
		super(message);
	}

}