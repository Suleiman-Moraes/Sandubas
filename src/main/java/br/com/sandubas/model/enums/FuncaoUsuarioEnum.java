package br.com.sandubas.model.enums;

/**
 * @author marcos.ribeiro
 */
public enum FuncaoUsuarioEnum {
	
    USUARIO_EXTERNO("1","Cliente"),
    OPERADOR_CAIXA("2","Operador de Caixa"),
    FUNCIONARIO("3","Funcionário"),
    ADMINISTRADOR("4","Administrador"),
	ROOT("5","root");
    
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
