package br.com.sandubas.model.interfaces;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.springframework.http.HttpMethod;

import com.google.gson.reflect.TypeToken;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.Response;
import br.com.sandubas.util.ClientHelp;

@SuppressWarnings("unchecked")
public interface ICRUDService<T extends Serializable> {
	T salvar(T objeto) throws NegocioException;

	void excluir(T objeto) throws NegocioException;
	
	default Boolean excluir(String url) throws NegocioException {
		return realizarRequisicaoDevolvendoBoolean(url, HttpMethod.DELETE);
	}
	
	default Boolean existsByField(String url) throws NegocioException {
		return realizarRequisicaoDevolvendoBoolean(url, HttpMethod.GET);
	}
	
	default Boolean realizarRequisicaoDevolvendoBoolean(String url, HttpMethod method) throws NegocioException {
		try {
			Type type = new TypeToken<Response<Boolean>>() {
			}.getType();
			Response<Boolean> response = (Response<Boolean>) ClientHelp.realizarRequisicao(url, method, type);
			if (response != null) {
				verificarObjetoResponse(response);
				if (response.getData() != null) {
					return response.getData();
				}
			}
			return Boolean.FALSE;
		} catch (NegocioException e) {
			throw e;
		}
	}

	default void verificarObjetoResponse(Response<?> response) throws NegocioException {
		if (response.getErros().size() > 0) {
			String erro = "";
			for (String err : response.getErros()) {
				erro += err + "\n";
			}
			throw new NegocioException(erro.trim(), Boolean.FALSE);
		}
	}
}
