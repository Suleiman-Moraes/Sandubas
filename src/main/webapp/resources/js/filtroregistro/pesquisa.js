/**
 * @author Suleiman
 */

$(document).ready(
	function() {
		// console.log('filtroregistro/pesquisa.js');
		var filtroSelecionado = '*[id="formulario:listarRegistros:filtroSelecionado"]';
		var nome = '*[id="formulario:listarRegistros:nome"]';
		var descricao = '*[id="formulario:listarRegistros:descricao"]';
		var id = '*[id="formulario:listarRegistros:id"]';
		var precoPago = '*[id="formulario:listarRegistros:precoPago"]';
		var porcentagemVenda = '*[id="formulario:listarRegistros:porcentagemVenda"]';
		var quantidade = '*[id="formulario:listarRegistros:quantidade"]';
		var valorAgrupamento = '*[id="formulario:listarRegistros:valorAgrupamento"]';
		var marca = '*[id="formulario:listarRegistros:marca"]';
		var valorMedida = '*[id="formulario:listarRegistros:valorMedida"]';
		var descricao = '*[id="formulario:listarRegistros:descricao"]';
		
		var btnBuscar = '*[id="formulario:listarRegistros:btnBuscar"]';
	
		var globalFilter = '*[id="formulario:listarRegistros:globalFilter"]';
	
		$(document).on('change', filtroSelecionado, function() {
			var v = $(filtroSelecionado).val();
			console.log('filtroSelecionado' + v); 
			if (v == '') {
				$(globalFilter).val(v);
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', nome, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(nome).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', precoPago, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(precoPago).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', porcentagemVenda, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(porcentagemVenda).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', quantidade, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(quantidade).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', valorAgrupamento, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(valorAgrupamento).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', marca, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(marca).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', valorMedida, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(valorMedida).val());
				PF('registroWV').filter();
			}
		});
		
		$(document).on('keypress', descricao, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(descricao).val());
				PF('registroWV').filter();
			}
		});
	
		$(document).on('keypress', id, function(e) {
			if(e.keyCode == 13){
				$(globalFilter).val($(id).val());
				PF('registroWV').filter();
			}
		});
		
	});
