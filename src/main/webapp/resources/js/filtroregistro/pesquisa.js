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
