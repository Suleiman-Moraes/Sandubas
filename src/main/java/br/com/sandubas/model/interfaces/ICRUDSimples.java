package br.com.sandubas.model.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.PrimeFaces;

import br.com.sandubas.util.jsf.FacesUtil;

public interface ICRUDSimples <T extends Serializable>{
	
	/**
	 * Deve ser passado as opções em relação a modal:
	 * modal, resizable, contentHeight, contentWidth, draggable e etc,
	 * alem de devolver tb o link no no url_pt
	 * @param objeto
	 * @return [(Map)opcoes, (String)url]
	 */
	Object[] abrirModal(T objeto);
	void deletarObjeto();
	void salvarObjeto();
	void limpar();
	
	@SuppressWarnings("unchecked")
	default void abrirModalCrud(T objeto) {
		Object[] retorno = abrirModal(objeto);
		Map<String, Object> opcoes = (Map<String, Object>) retorno[0];
		Map<String, List<String>> params = new HashMap<>();
		
		if (objeto != null) {
			List<String> objetoId = new ArrayList<>();
			objetoId.add(((IEntidadeRelacional)objeto).getAtributoIndentificador().toString());
			params.put("objetoId", objetoId);
		}
		PrimeFaces.current().dialog().openDynamic(
				FacesUtil.propertiesLoaderURL().getProperty(retorno[1]+""), opcoes, params);
	}
}
