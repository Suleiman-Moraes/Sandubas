package br.com.sandubas.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UsuarioPaginateSessionScoped implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int first = 0;
	private int pageSize = 5;
	private String filtroSelecionado;
	private String filtroValor;
	private boolean limitOffset = false;
	private boolean update;
	private String todos;

	public void initLimitOffSet(int first, int pageSize) {
		if (this.first != first || this.pageSize != pageSize) {
			setLimitOffset(true);
		} else {
			setLimitOffset(false);
		}
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		if (first != this.first) {
			this.first = first;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}

	public void setFiltroSelecionado(String filtroSelecionado, String filtro) {
		if (filtro != null) {
			this.filtroSelecionado = filtroSelecionado;
		}
	}

	public String getFiltroValor() {
		return filtroValor;
	}

	public void setFiltroValor(String filtroValor, String filtro) {
		if (filtro != null) {
			this.filtroValor = filtroValor;
		}
	}

	public boolean isLimitOffset() {
		return limitOffset;
	}

	public void setLimitOffset(boolean limitOffset) {
		this.limitOffset = limitOffset;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getTodos() {
		return todos;
	}

	public void setTodos(String todos) {
		this.todos = todos;
	}
}