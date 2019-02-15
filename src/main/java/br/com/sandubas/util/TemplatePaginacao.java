package br.com.sandubas.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.LazyDataModel;

import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Page;


public abstract class TemplatePaginacao<T> {
	protected Page<T> pages;
	protected String filtroSelecionado;
	protected String filtroValor;
	protected int totalDeRegistros;
	protected LazyDataModel<T> registros;
	protected List<FiltroPesquisa> filtrosPesquisa = new ArrayList<>();
	protected T objeto;
	public static final String CAMINHO_COMPONENTE = "formulario:listarRegistros:";
	public static final String DISPLAY_NONE = "display: none";
	public static final String DISPLAY_INLINE = "display: inline";
	
	/**
	 * Este metodo coordena a combo de filtros
	 * @param event
	 */
	public void selecionarFiltroPesquisa(AjaxBehaviorEvent event) {
		try {
			UIOutput select = (UIOutput) event.getSource();
			String value = select.getValue().toString();
			FiltroPesquisa filtroPesquisa = filtrosPesquisa.stream().filter(f -> f.getValue().equals(value)).findFirst().get();
			
			UIComponent[] componentes = indentificarComponentes();
			String style = "";
			for (int i = 0; i < componentes.length; i++) {
				if(componentes[i].getId().equals(filtroPesquisa.getValue())) {
					style = DISPLAY_INLINE;
				}
				else {
					style = DISPLAY_NONE;
				}
				componentes[i].getClass().getDeclaredMethod("setStyle", String.class).invoke(componentes[i], style);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * O componete espaço NÃO deve ser passado
	 * apenas podem ser passados componentes 
	 * HTML PURO
	 * @return
	 * @throws Exception
	 */
	public abstract UIComponent[] indentificarComponentes() throws Exception;
	
	public abstract void init();
	
	/**
	 * Se seguido o padra estabelecido, com o nome do formulario
	 * e da data table, se passa o id do domponente e depois gerado
	 * o caminho completo
	 * @param idComponente
	 * @return caminho completo do componente
	 */
	public String getCaminhoComponenteCompleto(String idComponente) {
		return CAMINHO_COMPONENTE + idComponente;
	}
	public Page<T> getPages() {
		return pages;
	}
	public T getObjeto() {
		return objeto;
	}
	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}
	public String getFiltroValor() {
		return filtroValor;
	}
	public int getTotalDeRegistros() {
		return totalDeRegistros;
	}
	public LazyDataModel<T> getRegistros() {
		return registros;
	}
	public List<FiltroPesquisa> getFiltrosPesquisa() {
		return filtrosPesquisa;
	}
	public void setPages(Page<T> pages) {
		this.pages = pages;
	}
	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}
	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}
	public void setFiltroValor(String filtroValor) {
		this.filtroValor = filtroValor;
	}
	public void setTotalDeRegistros(int totalDeRegistros) {
		this.totalDeRegistros = totalDeRegistros;
	}
	public void setRegistros(LazyDataModel<T> registros) {
		this.registros = registros;
	}
	public void setFiltrosPesquisa(List<FiltroPesquisa> filtrosPesquisa) {
		this.filtrosPesquisa = filtrosPesquisa;
	}
}
