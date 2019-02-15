package br.com.sandubas.service;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.http.HttpMethod;

import com.google.gson.reflect.TypeToken;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.Page;
import br.com.sandubas.model.Response;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.util.ClientHelp;
import br.com.sandubas.util.StringUtil;
import br.com.sandubas.util.jsf.FacesUtil;

@SuppressWarnings("all")
public class ClassificacaoMercadoriaService implements Serializable, ICRUDService<ClassificacaoMercadoria> {

	private static final long serialVersionUID = -8735358206076950412L;

	private static final String URL_PRINCIPAL = FacesUtil.propertiesLoaderURL().getProperty("linkWebService")
			+ "/classificacaomercadoria";

	public Page<ClassificacaoMercadoria> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo,
			String valor) throws NegocioException {
		try {
			Page<ClassificacaoMercadoria> pages = null;
			campo = campo == null ? "" : campo;
			valor = StringUtil.isNullOrEmpty(valor.trim()) ? StringUtil.UNINFORMED : valor;
			
			String nome = campo.equals("nome") ? valor : StringUtil.UNINFORMED;
			String descricao = campo.equals("descricao") ? valor : StringUtil.UNINFORMED;
			String id = campo.equals("id") ? valor : "0";
			final String url = String.format("%s/%s/%s/%s/%s/%s", URL_PRINCIPAL, inicioDaPagina, tamanhoDaPagina, id,
					nome, descricao);
			Type type = new TypeToken<Response<Page<ClassificacaoMercadoria>>>() {
			}.getType();
			Response<Page<ClassificacaoMercadoria>> response = (Response<Page<ClassificacaoMercadoria>>) ClientHelp
					.realizarRequisicao(url, HttpMethod.GET, type);
			if (response != null) {
				verificarObjetoResponse(response);
				if (response.getData() != null) {
					pages = response.getData();
				} 
			}
			return pages;
		} catch (NegocioException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ClassificacaoMercadoria salvar(ClassificacaoMercadoria objeto) throws NegocioException {
		try {
			if (!this.registroExiste(objeto)) {
				HttpMethod method = objeto.getId() != null ? HttpMethod.PUT : HttpMethod.POST;
				Response<ClassificacaoMercadoria> response = (Response<ClassificacaoMercadoria>) ClientHelp
						.realizarRequisicao(URL_PRINCIPAL, method, getTypePadrao(), objeto);
				if (response != null) {
					verificarObjetoResponse(response);
					if (response.getData() != null) {
						objeto = response.getData();
					} 
				}
				return objeto;
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("classificacaoMercadoriaExistente"),
						Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public void excluir(ClassificacaoMercadoria objeto) throws NegocioException {
		try {
			Boolean exclusao = excluir(String.format("%s/%s", URL_PRINCIPAL, objeto.getId()));
			if(exclusao) {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("classificacaoMercadoriaExclusao"),
						Boolean.TRUE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}
	
	public ClassificacaoMercadoria findById(String id) throws NegocioException {
		try {
			ClassificacaoMercadoria objeto = null;
			Response<ClassificacaoMercadoria> response = (Response<ClassificacaoMercadoria>) ClientHelp
					.realizarRequisicao(String.format("%s/%s", URL_PRINCIPAL, id), HttpMethod.GET, getTypePadrao());
			if (response != null) {
				verificarObjetoResponse(response);
				if (response.getData() != null) {
					objeto = response.getData();
				} 
			}
			return objeto;
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		}
	}
	
	public List<ClassificacaoMercadoria> findAll() throws NegocioException {
		try {
			List<ClassificacaoMercadoria> objeto = null;
			Type type = new TypeToken<Response<List<ClassificacaoMercadoria>>>() {
			}.getType();
			Response<List<ClassificacaoMercadoria>> response = (Response<List<ClassificacaoMercadoria>>) ClientHelp
					.realizarRequisicao(URL_PRINCIPAL, HttpMethod.GET, type);
			if (response != null) {
				verificarObjetoResponse(response);
				if (response.getData() != null) {
					objeto = response.getData();
				} 
			}
			return objeto;
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public Boolean registroExiste(ClassificacaoMercadoria objeto) {
		// TODO implementar
		return Boolean.FALSE;
	}

	private Type getTypePadrao() {
		return new TypeToken<Response<ClassificacaoMercadoria>>() {
		}.getType();
	}
}
