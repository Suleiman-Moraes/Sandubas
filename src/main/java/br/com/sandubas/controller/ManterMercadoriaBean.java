package br.com.sandubas.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.ClassificacaoMercadoria;
import br.com.sandubas.model.FiltroPesquisa;
import br.com.sandubas.model.Mercadoria;
import br.com.sandubas.model.TipoProduto;
import br.com.sandubas.model.interfaces.ICRUDSimples;
import br.com.sandubas.service.MercadoriaService;
import br.com.sandubas.util.TemplatePaginacao;
import br.com.sandubas.util.jsf.FacesUtil;
import lombok.Setter;

@Named
@ViewScoped
public class ManterMercadoriaBean extends TemplatePaginacao<Mercadoria>
		implements Serializable, ICRUDSimples<Mercadoria> {

	private static final long serialVersionUID = 1L;

	@Inject
	private MercadoriaService service;

	@Setter
	private List<TipoProduto> listaTipoProduto;

	@Setter
	private List<ClassificacaoMercadoria> listaClassificacaoMercadoria;

	public ManterMercadoriaBean() {
		registros = new LazyDataModel<Mercadoria>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Mercadoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				List<Mercadoria> registros = null;
				filtroValor = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
				registros = service.paginarRegistro(first, pageSize, filtroSelecionado, filtroValor);
				totalDeRegistros = service.contarRegistrosCadastrados(filtroSelecionado, filtroValor);
				setRowCount(totalDeRegistros);
				filtroValor = "";
				return registros;
			}
		};
	}

	public void mostrarMensagemSucesso(boolean edicao) {
		if (edicao) {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("mercadoriaSucessoEditado"));
		} else {
			FacesUtil.addSuccessMessage(FacesUtil.propertiesLoader().getProperty("mercadoriaSucessoNovo"));
		}
	}

	@Override
	public UIComponent[] indentificarComponentes() throws Exception {
		UIComponent[] componentes = new UIComponent[10];

		HtmlInputText id = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("id"));
		HtmlInputText precoPago = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("precoPago"));
		HtmlInputText porcentagemVenda = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("porcentagemVenda"));
		HtmlInputText quantidade = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("quantidade"));
		HtmlInputText valorAgrupamento = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("valorAgrupamento"));
		HtmlInputText marca = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("marca"));
		HtmlInputText valorMedida = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("valorMedida"));
		HtmlInputText descricao = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("descricao"));
		HtmlInputText classificacaoMercadoria = FacesUtil
				.htmlInputText(getCaminhoComponenteCompleto("classificacaoMercadoria_nome"));
		HtmlInputText tipoProduto = FacesUtil.htmlInputText(getCaminhoComponenteCompleto("tipoProduto_nome"));

		componentes[0] = id;
		componentes[1] = precoPago;
		componentes[2] = porcentagemVenda;
		componentes[3] = quantidade;
		componentes[4] = valorAgrupamento;
		componentes[5] = marca;
		componentes[6] = valorMedida;
		componentes[7] = descricao;
		componentes[8] = classificacaoMercadoria;
		componentes[9] = tipoProduto;

		return componentes;
	}

	@PostConstruct
	@Override
	public void init() {
		filtrosPesquisa.add(new FiltroPesquisa("Todos", ""));
		filtrosPesquisa.add(new FiltroPesquisa("Código", "id"));
		filtrosPesquisa.add(new FiltroPesquisa("Preço Pago", "precoPago"));
		filtrosPesquisa.add(new FiltroPesquisa("Porcentagem de Venda", "porcentagemVenda"));
		filtrosPesquisa.add(new FiltroPesquisa("Marca", "marca"));
		filtrosPesquisa.add(new FiltroPesquisa("Quantidade", "quantidade"));
		filtrosPesquisa.add(new FiltroPesquisa("Valor de Medida", "valorMedida"));
		filtrosPesquisa.add(new FiltroPesquisa("Valor de Agrupamento", "valorAgrupamento"));
		filtrosPesquisa.add(new FiltroPesquisa("Descrição", "descricao"));
		filtrosPesquisa.add(new FiltroPesquisa("Tipo do Produto", "tipoProduto_nome"));
		filtrosPesquisa.add(new FiltroPesquisa("Classificação de Mercadoria", "classificacaoMercadoria_nome"));
	}

	@Override
	public Object[] abrirModal(Mercadoria objeto) {
		Object[] retorno = new Object[2];
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);// redimensionar a modal
		opcoes.put("contentHeight", 500);// altura
		opcoes.put("contentWidth", 600);// largura
		opcoes.put("draggable", true);// movimentar a modal

		retorno[0] = opcoes;// configuração da modal
		retorno[1] = "linkModalCadastroMercadoria";// Link da modal no url_pt

		return retorno;
	}

	@Override
	public void deletarObjeto() {
		try {
			this.service.excluir(getObjeto());
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public void salvarObjeto() {
		try {
			service.salvar(objeto);
			RequestContext.getCurrentInstance().closeDialog(objeto);
		} catch (NegocioException e) {
			FacesUtil.addDynamicMessage(e.getMessage(), e.isTypeException());
		}
	}

	@Override
	public void limpar() {
		this.objeto = new Mercadoria();
	}

	// Getters and Setters
	@Override
	public Mercadoria getObjeto() {
		if (objeto == null) {
			objeto = new Mercadoria();
		}
		return objeto;
	}

	public List<ClassificacaoMercadoria> getListaClassificacaoMercadoria() {
		if (listaClassificacaoMercadoria == null) {
			listaClassificacaoMercadoria = service.getClassificacaoMercadoriaService().getPersistencia()
					.getOrderList(ClassificacaoMercadoria.class, "nome");
		}
		return listaClassificacaoMercadoria;
	}

	public List<TipoProduto> getListaTipoProduto() {
		if (listaTipoProduto == null) {
			listaTipoProduto = service.getTipoProdutoService().getPersistencia().getOrderList(TipoProduto.class,
					"nome");
		}
		return listaTipoProduto;
	}
}
