package br.com.sandubas.model.interfaces;

import java.util.List;

public interface IPaginacaoService<T> {
	List<T> paginarRegistro(Integer inicioDaPagina, Integer tamanhoDaPagina, String campo, String valor);
	
	Integer contarRegistrosCadastrados(String campo, String valor);
	
	String devolverCondicaoParaPaginacao(String campo, String valor);
}
