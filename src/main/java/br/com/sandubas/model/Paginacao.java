package br.com.sandubas.model;

import java.util.Date;
import java.util.List;

/**
 * @author Manoel
 * @version v1.0.0 24/12/2016
 * @since v1.0.0
 */
public class Paginacao {

	private List<Object> manifestacoes;

	private List<Usuario> usuarios;

	private int totalDeRegistros;

	private Date filtroValorData;

	private int totalDeRegistrosReal;

	private boolean update;

	public List<Object> getManifestacoes() {
		return manifestacoes;
	}

	public void setManifestacoes(List<Object> manifestacoes) {
		this.manifestacoes = manifestacoes;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public int getTotalDeRegistros() {
		return totalDeRegistros;
	}

	public void setTotalDeRegistros(int totalDeRegistros) {
		this.totalDeRegistros = totalDeRegistros;
	}

	public Date getFiltroValorData() {
		return filtroValorData;
	}

	public void setFiltroValorData(Date filtroValorData) {
		this.filtroValorData = filtroValorData;
	}

	public int getTotalDeRegistrosReal() {
		return totalDeRegistrosReal;
	}

	public void setTotalDeRegistrosReal(int totalDeRegistrosReal) {
		this.totalDeRegistrosReal = totalDeRegistrosReal;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

}
