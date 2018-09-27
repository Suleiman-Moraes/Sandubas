package br.com.sandubas.model.interfaces;

import java.util.List;


public interface IPaginarDAO<T> {
	public List<T> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String... condicao);
	public List<T> paginar(Integer inicioDaPagina, Integer tamanhoDaPagina, String join,
			Object obj, boolean parametro, String... condicao);
}
