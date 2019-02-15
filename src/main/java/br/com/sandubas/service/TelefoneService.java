package br.com.sandubas.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sandubas.dao.TelefoneDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.Telefone;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.util.jsf.FacesUtil;

@SuppressWarnings("all")
public class TelefoneService implements Serializable, ICRUDService<Telefone>{

	private static final long serialVersionUID = -8735358206076950412L;
	 
	@Inject
	private TelefoneDAO persistencia;

	@Override
	public Telefone salvar(Telefone objeto) throws NegocioException {
		try {
			if (!this.registroExiste(objeto)) {
				this.persistencia.update(objeto);
				return objeto;
			} else {
				throw new NegocioException(FacesUtil.propertiesLoader().getProperty("telefoneExistente"),
						Boolean.FALSE);
			}
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public void excluir(Telefone objeto) throws NegocioException {
		try {
//			this.validarExclusao(objeto.getId(), "areaEntrada", Protocolo.class.getName(), "areaEntradaInvalidaExclusaoManifestacao");
			this.persistencia.delete(Telefone.class, objeto.getId());
			throw new NegocioException(FacesUtil.propertiesLoader().getProperty("telefoneExclusao"), Boolean.TRUE);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.isTypeException());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), Boolean.FALSE);
		}
	}

	@Override
	public Boolean registroExiste(Telefone objeto) {
		//TODO implementar
		return Boolean.FALSE;
	}
	
	public TelefoneDAO getPersistencia() {
		return persistencia;
	}
}
