/**
 * @author Suleiman
 */

$(document).ready(function() {
	//console.log('perfil/pesquisa.js');
	var filtroSelecionado = '*[id="fo-perfil:listarPerfis:filtroSelecionado"]';
	var nome = '*[id="fo-perfil:listarPerfis:nome"]';
	var id = '*[id="fo-perfil:listarPerfis:id"]';
    
	var globalFilter = '*[id="fo-perfil:listarPerfis:globalFilter"]';

	$(document).on('change', filtroSelecionado, function() {
		var v = $(filtroSelecionado).val();
		console.log('filtroSelecionado' + v);
		if (v == '') {
			$(globalFilter).val(v);
			PF('perfis').filter();
		}
	});

	$(document).on('change', nome, function() {
		$(globalFilter).val($(nome).val());
		PF('perfis').filter();
	});
	
	$(document).on('change', id, function() {
		$(globalFilter).val($(id).val());
		PF('perfis').filter();
	});	
	
	function somenteNumero(e) {
		var tecla = (window.event) ? event.keyCode : e.which;
		if ((tecla > 47 && tecla < 58))
			return true;
		else {
			if (tecla == 8 || tecla == 0)
				return true;
			else
				return false;
		}
	}
});
