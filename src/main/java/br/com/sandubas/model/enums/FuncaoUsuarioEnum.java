package br.com.sandubas.model.enums;

/**
 * @author marcos.ribeiro
 */
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

    public String getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
