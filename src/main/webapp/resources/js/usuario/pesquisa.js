/**
 * @author thiago
 * @author manoel
 * @version v1.0.0 26/12/2016
 * @since v1.0.0
 */
var filtroSelecionado = '*[id="fo-usuario:usuarios:filtroSelecionado"]';

$(document).ready(function() {
	var nome = '*[id="fo-usuario:usuarios:nome"]';
	var email = '*[id="fo-usuario:usuarios:email"]';
	var login = '*[id="fo-usuario:usuarios:login"]';
	var funcaoUsuario = '*[id="fo-usuario:usuarios:funcaoUsuario"]';
	var unidade = '*[id="fo-usuario:usuarios:unidade"]';
	var tipoAcesso = '*[id="fo-usuario:usuarios:tipoAcesso"]';
	var statusUsuario = '*[id="fo-usuario:usuarios:statusUsuario"]';
	var globalFilter = '*[id="fo-usuario:usuarios:globalFilter"]';

	$(document).on('change', filtroSelecionado, function() {
		var v = $(filtroSelecionado).val();
		if (v == '') {
			$(globalFilter).val(v);
			PF('usuarios').filter();
		}
	});

	$(document).on('keypress', nome, function(e) {
		if(e.keyCode == 13){
			$(globalFilter).val($(nome).val());
			PF('usuarios').filter();
			atualizarRegistros();
		}
	});

	$(document).on('keypress', email, function(e) {
		if(e.keyCode == 13){
			$(globalFilter).val($(email).val());
			PF('usuarios').filter();
			atualizarRegistros();
		}
	});
	
	$(document).on('keypress', login, function(e) {
		if(e.keyCode == 13){
			$(globalFilter).val($(login).val());
			PF('usuarios').filter();
			atualizarRegistros();
		}
	});	

	$(document).on('change', funcaoUsuario, function() {
		$(globalFilter).val($(funcaoUsuario).val());
		PF('usuarios').filter();
		atualizarRegistros();
	});

	$(document).on('change', unidade, function() {
		$(globalFilter).val($(unidade).val());
		PF('usuarios').filter();
		atualizarRegistros();
	});
	
	$(document).on('change', tipoAcesso, function() {
		$(globalFilter).val($(tipoAcesso).val());
		PF('usuarios').filter();
		atualizarRegistros();
	});	
	
	$(document).on('change', statusUsuario, function() {
		$(globalFilter).val($(statusUsuario).val());
		PF('usuarios').filter();
		atualizarRegistros();
	});		

});
