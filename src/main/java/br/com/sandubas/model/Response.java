package br.com.sandubas.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	private List<String> erros;
	
	public List<String> getErros() {
		if(this.erros == null) {
			this.erros = new LinkedList<>();
		}
		return erros;
	}
}
