package br.com.sandubas.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.sandubas.dao.ClassificacaoMercadoriaDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.model.interfaces.IPaginacaoService;
import br.com.sandubas.util.jsf.FacesUtil;

@SuppressWarnings("all")
public class ClassificacaoMercadoriaService implements Serializable, ICRUDService<ClassificacaoMercadoria>, IPaginacaoService<ClassificacaoMercadoria>{

	private static final long serialVersionUID = -8735358206076950412L;
	 
	@Inject
	private ClassificacaoMercadoriaDAO persistencia;

	@Override
	public List<ClassificacaoMercadoria> paginarRegistro(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo,
			String valor) {
		List<ClassificacaoMercadoria> listaRegistro = null;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			condicao += devolverCondicaoParaPaginacao(campo, valor);
		}
		condicao += " ORDER BY classificacaomercadoria.id DESC";
		
		listaRegistro = persistencia.paginar(inicioDaPagina, tamanhoDaPagina, condicao);
		
		return listaRegistro;
	}

	@Override
	public Integer contarRegistrosCadastrados(String campo, String valor) {
		Integer total = 0;
		campo = campo == null ? "" : campo;
		valor = valor == null ? "" : valor;
		String condicao = "1 = 1";
		if (!campo.isEmpty() && !valor.isEmpty()) {
			condicao += devolverCondicaoParaPaginacao(campo, valor);
		}
		total = persistencia.count(ClassificacaoMercadoria.class, condicao).intValue();
		return total;
	}
	
	@Override
	public String devolverCondicaoParaPaginacao(String campo, String valor) {
		String condicao = "";
		switch (campo) {
		case "nome":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(classificacaomercadoria." + campo + ")", valor.toUpperCase());
			break;
		case "id":
			condicao += String.format(" AND %s = %s", campo, valor);
			break;
		case "descricao":
			condicao += String.format(" AND %s LIKE '%%%s%%'", "UPPER(classificacaomercadoria." + campo + ")", valor.toUpperCase());
			break;
		}
		return condicao;
	}
	
	@Override
	public void salvar(ClassificacaoMercadoria objeto) throws NegocioException {
		try {
			if (!this.registroExiste(objeto)) {
				this.persistencia.update(objeto);
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
//			this.validarExclusao(objeto.getId(), "areaEntrada", Protocolo.class.getName(), "areaEntradaInvalidaExclusaoManifestacao");
			this.persistencia.delete(ClassificacaoMercadoria.class, objeto.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("classificacaoMercadoriaExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public Boolean registroExiste(ClassificacaoMercadoria objeto) {
		//TODO implementar
		return Boolean.FALSE;
	}
	
	public ClassificacaoMercadoriaDAO getPersistencia() {
		return persistencia;
	}
}
