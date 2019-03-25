package br.com.sandubas.model.interfaces;

import java.io.Serializable;

import br.com.sandubas.exception.NegocioException;

public interface ICRUDService<T extends Serializable>{
	void salvar(T objeto) throws NegocioException;
	void excluir(T objeto) throws NegocioException;
	public Boolean registroExiste(T objeto);
}
