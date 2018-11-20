package br.com.sandubas.model.enums;

import lombok.Getter;

/**
 * @author marcos.ribeiro
 */
@Getter
public enum FuncaoUsuarioEnum {
	
	ROOT("1","root"),
	ADMINISTRADOR("2","Administrador"),
    OPERADOR_CAIXA("3","Operador de Caixa"),
    FUNCIONARIO("4","Funcionário"),
	USUARIO_EXTERNO("5","Cliente");
    
    private String id;
    private String descricao;

    private FuncaoUsuarioEnum(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}
