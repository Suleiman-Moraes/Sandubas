package br.com.sandubas.exception;

public class UsuarioInativoException extends RuntimeException {

	private static final long serialVersionUID = -5618658079680419266L;

	public UsuarioInativoException(String message) {
		super(message);
	}

}