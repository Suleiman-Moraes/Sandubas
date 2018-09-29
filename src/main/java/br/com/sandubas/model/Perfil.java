/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sandubas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Manoel Albino Neto
 */
@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Column(nullable = false, length = 30)
	private String nome;

	@NotNull
	@NotBlank
	@Column(nullable = false, length = 250)
	private String descricao;

	@ManyToMany
	@JoinTable(name = "perfil_funcionalidade", joinColumns = { @JoinColumn(name = "id_perfil") }, inverseJoinColumns = {
			@JoinColumn(name = "id_funcionalidade") })
	private List<Funcionalidade> funcionalidades;

	public Perfil() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Funcionalidade> getFuncionalidades() {
		if (funcionalidades == null) {
			funcionalidades = new ArrayList<Funcionalidade>(0);
		}
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	@Override
	public String toString() {
		return String.format("%s > %s", getEntidade(), getNome());
	}

	private String getEntidade() {
		return "Perfil";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
