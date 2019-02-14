package br.com.sandubas.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;

import br.com.sandubas.dao.ClassificacaoMercadoriaDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.interfaces.ICRUDService;
import br.com.sandubas.model.interfaces.IPaginacaoService;
import br.com.sandubas.util.jsf.FacesUtil;

@SuppressWarnings("all")
public class ClassificacaoMercadoriaService implements Serializable, ICRUDService<ClassificacaoMercadoria>{

	private static final long serialVersionUID = -8735358206076950412L;
	 
	@Inject
	private ClassificacaoMercadoriaDAO persistencia;

	public Page<ClassificacaoMercadoria> paginar(){
		
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
