package br.com.sandubas.model.enums;


/**
 * @author marcos.ribeiro
 * @author Samuel Correia Guimarães
 */
public enum FuncionalidadeEnum {

    ATUALIZAR_MINHAS_INFORMACOES(1, "Atualizar Minhas Informações"),
    CONFIGURAR_PREFERENCIAS_DE_SISTEMA(2, "Configurar Preferencias de Sistema"),
    CONSULTAR_PROJETOS_DO_SALIC(3, "Consultar Projetos do SALIC"),
    CONSULTAR_PROPOSTAS_DO_SALIC(4, "Consultar Propostas do SALIC"),
    GERENCIAR_MANIFESTACAO(5, "Gerenciar Manifestação"),
    MANTER_AJUDA(6, "Manter Ajuda"),
    MANTER_AREA_DE_ENTRADA(7, "Manter Area de Entrada"),
    MANTER_AVISO(8, "Manter Aviso"),
    MANTER_CLASSIFICACOES(9, "Manter Classificações"),
    MANTER_EMAILS_DE_NOTIFICACAO(10, "Manter Emails de Notificação"),
    MANTER_MENSAGEM_TEXTO_PADRONIZADO(11, "Manter Mensagem Texto Padronizado"),
    MANTER_FAIXA_ETARIA(12, "Manter Faixa Etária"),
    MANTER_FILTROS_PERSONALIZADOS(13, "Manter Filtros Personalizados"),
    MANTER_FILTROS_SPAM(14, "Manter Filtros Spam"),
    MANTER_GRUPO(15, "Manter Grupo"),
    MANTER_LISTA_MANIFESTACAO(16, "Manter Lista Manifestação"),
    MANTER_MEIO_DE_ENTRADA(17, "Manter Meio de Entrada"),
    MANTER_MEIO_DE_RESPOSTA(18, "Manter Meio de Resposta"),
    MANTER_PERFIL(19, "Manter Perfil"),
    MANTER_PERFIL_X_FUNCIONALIDADES(20, "Manter Perfil x Funcionalidades"),
    MANTER_PERFIL_X_GRUPO(21, "Manter Perfil x Grupo"),
    MANTER_PRIORIDADE(22, "Manter Prioridade"),
    MANTER_RESPOSTAS_MANIFESTACOES(23, "Manter Respostas Manifestações"),
    MANTER_SUBCLASSIFICACOES(24, "Manter SubClassificações"),
    MANTER_TIPO_MANIFESTACAO(25, "Manter Tipo Manifestação"),
    MANTER_UNIDADE(26, "Manter Unidade"),
    MANTER_USUARIO_X_PERFIL(27, "Manter Usuário x Perfil"),
    MANTER_USUARIOS(28, "Manter Usuários"),
    PAINEL_DE_NOTIFICACAO_CONTROLE_PRAZOS(29, "Painel de Notificação/Controle Prazos"),
    REALIZAR_TRAMITE_UNIDADE(30, "Realizar Trâmite Unidade"),
    REALIZAR_EXTRACAO_DE_DADOS(31, "Realizar Extração de Dados"),
    REALIZAR_PESQUISA_NAS_MANIFESTACOES(32, "Realizar Pesquisa nas Manifestações"),
    REALIZAR_PESQUISA_NOS_REGISTROS(33, "Realizar Pesquisa nos Registros"),
    REGISTRAR_REGISTRO_EXTERNO(34, "Registrar Registro Externo"),
    REGISTRAR_MANIFESTACAO(35, "Registrar Manifestação"),
    REGISTRAR_REGISTRO(36, "Registrar Registro"),
    VISUALIZAR_ESTATISTICAS_DE_MANIFESTACAO(37, "Visualizar Estatísticas de Manifestação"),
    VISUALIZAR_LOG_DE_OPERACOES(38, "Visualizar Log de Operações"),
    MANTER_GRUPO_AJUDA(39, "Manter Grupo Ajuda"),
    VISUALIZAR_DADOS_SIGILOSOS(40, "Visualizar Dados Sigilosos"),
    MANTER_GRAU_INSTRUCAO(41, "Manter Grau de Instrução"),
    GERAR_GRAFICOS(42, "Gerar Gráficos"),
    MANTER_QUESTIONARIO(43, "Manter Questionário"),
    MANTER_AREA_ATUACAO(44, "Manter Area de Atuação"),
	MANTER_TIPO_EMAIL_AUTOMATIZADO(45, "Manter Tipo Email Automatizado"),
	MANTER_PRESTADORA_DE_SERVICO(46, "Manter Prestadora de Serviço"),
	GERENCIAR_MANIFESTACAO_MANIFESTANTE(47, "Gerenciar Manifestação Manifestante");
    
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
