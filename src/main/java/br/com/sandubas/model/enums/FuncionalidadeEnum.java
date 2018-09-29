package br.com.sandubas.model.enums;


/**
 * @author marcos.ribeiro
 * @author Samuel Correia Guimarães
 * @author Suleiman Alves de Moraes
 */
public enum FuncionalidadeEnum {

    ATUALIZAR_MINHAS_INFORMACOES(1, "Atualizar Minhas Informações"),
    CONFIGURAR_PREFERENCIAS_DE_SISTEMA(2, "Configurar Preferencias de Sistema"),
    MANTER_CRUD_SIMPLES(3, "Manter CRUD Simples");
    
    private Integer id;
    private String descricao;
    
    private FuncionalidadeEnum(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;

    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
    public static FuncionalidadeEnum retornaSituacao(String situacao) {
    	FuncionalidadeEnum retorno = null;
        if (situacao != null) {
           for (FuncionalidadeEnum obj : FuncionalidadeEnum.values()) {
              if (situacao != null && situacao.equals(obj.getDescricao().toString())) {
                 retorno = obj;
                 break;
              } 
           }
        }
        return retorno;
     }    
    
    
}
