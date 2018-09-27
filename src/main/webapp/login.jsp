<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<title>Sistema de Ouvidoria</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<meta http-equiv="refresh" content="3600;url=/ouvidoria" />
	<link rel="stylesheet" href="${context}/resources/css/modal.css" />
	<link rel="stylesheet" href="${context}/resources/css/estilo.css" />
	<link rel="stylesheet" href="${context}/resources/css/estiloGoogle.css" />
	<link rel="stylesheet" href="${context}/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${context}/resources/css/sistema.css" />
	<link rel="shortcut icon" type="image/x-icon" href="${context}/resources/images/favicon.ico" />	
	<script src="${context}/resources/js/jquery.js" type="text/javascript"></script>
	<script src="${context}/resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$.get("/ouvidoria/aviso", function(data) {
				console.log('data: ');
				if(data.titulo) {
					$("#avisoTitulo").append(data.titulo);
					$("#avisoConteudo").append(data.descricao);
					if($('#alert-error').hasClass("display-none")){
						$(".modal").show();
					}
				}
			}, "json" );
			var p = param('id');
			if($.isNumeric(p)){
                var url = "${context}/j_spring_security_check?id="+p;
                $('#loginform').attr('action', url);
			}
		})
		function param(name) {
		    return (location.search.split(name + '=')[1] || '').split('&')[0];
		}		
	</script>
</head>

<body>
	<main>
		<div class="container">
			<div class="row">
				<div class="col col-md-4 visible-md-block visible-lg-block padding-top-3">
					<div class="">
						<img src="${context}/resources/images/logo-ouvidoria-baloes.png"/>
					</div>
				</div>
				<div class="col col-md-8">
					<div class="visible-md-block visible-lg-block" style="margin-top: 50px;"></div>
					<div class="visible-xs-block visible-sm-block" style="margin-top: 10px;"></div>
					<div id="loginbox"
						class="mainbox">
						<div class="panel seta" style="border-color: #B68309;">
							<div class="panel-heading" style="background-color: #FFB504;border-color: #B68309;">
								<div class="panel-title text-bold">Autenticação</div>
								<div
									style="float: right; position: relative; top: -10px">
									<a href="${context}/pages/principal.xhtml" class="text-bold">Esqueceu a senha</a>
								</div>
							</div>
							<div style="padding-top: 30px;background: #02364c;" class="panel-body">
								<div id="alert-error" class="alert alert-danger-1 ${empty errors? 'display-none':''}" role="alert">
									<c:forEach var="error" items="${errors}">
										<strong>
											<c:out value="${error}" />
										</strong>
									</c:forEach>
									<c:choose>
										<c:when test="${param.logout == 'expired'}">
											<li>Sessão Expirada</li>
										</c:when>
									</c:choose>
								</div>
								<form id="loginform" class="form-horizontal" role="form" method="post" action="${context}/j_spring_security_check">
									<div style="margin-bottom: 25px" class="input-group${empty errors? '':' has-error'}">
										<span class="input-group-addon">
											<i class="glyphicon glyphicon-user"></i>
										</span>
										<input
											id="login-username"
											type="text" class="form-control" id="j_username" name="j_username"
											placeholder="Usuário" size="24">
									</div>
									<div style="margin-bottom: 25px" class="input-group${empty errors? '':' has-error'}">
										<span class="input-group-addon">
											<i class="glyphicon glyphicon-lock"></i>
										</span>
										<input
											id="login-password" id="j_password" name="j_password"
											type="password" class="form-control"
											placeholder="Senha" size="24">
									</div>
									
									<div style="margin-top: 10px" class="form-group">
										<!-- Button -->
										<div class="col-sm-12 controls">
											<button type="submit" id="btn-login" href="#" class="btn btn-default text-bold pull-right">
												<i class="glyphicon glyphicon-log-in margin-right-1"></i> ACESSAR
											</button>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-12 control">
											<div
												class="text-color-white text-size-1">
												Não é cadastrado?
												<a href="${context}/pages/externo/cadastrarManifestante.xhtml" class="link-laranja"> Cadastre-se </a>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div><!-- Panel end -->
					</div> <!-- loginbox -->
				</div> <!-- col -->
			</div><!-- row -->
			<div class="row margin-top-2 margin-bottom-3">
				<div class="col col-md-8 col-md-offset-4">
					<div id="item:1" class="bg-orange padding-1">
						<p class="h3 margin-0 position-relative">
							<a class="link-black" href="${context}/pages/externo/cadastrarManifestacaoExterna.xhtml">
								<img src="${context}/resources/images/addManifestacao.png" class="position-absolute top-0"/>
								<strong class="margin-left-3">Cadastrar Manifestação</strong>
							</a>
						</p>
					</div><!-- item:1 -->
					<div id="item:2" class="bg-orange padding-1 margin-top-2">
						<p class="h3 margin-0 position-relative">
							<a class="link-black" href="${context}/pages/externo/acompanharManifestacao.xhtml">
								<img src="${context}/resources/images/icon-search.png" class="position-absolute top-0"/>
								<strong class="margin-left-3">Acompanhar Manifestação</strong>
							</a>
						</p>
					</div><!-- item:2 -->
					<div id="item:3" class="padding-1 margin-top-2">
						<p class="h4 margin-0 position-relative text-color-black text-align-justify">
							<strong>
								À Ouvidoria compete receber, examinar e encaminhar as manifestações.
							</strong>
						</p>
					</div><!-- item:3 -->
				</div><!-- col -->
			</div><!-- row -->
		</div><!-- container -->
	</main>
	<footer class="bg-azul-2 margin-0">
		<div class="container">
			<div class="row margin-top-2 text-color-white">
				<div class="col col-md-5">
					<h4 class="text-color-white margin-left-0">Sobre a Ouvidoria</h4>
					<p class="h5">
						A implantação da Ouvidoria na AGR se deu por meio da Lei 13.569 de 27 de dezembro de 1999. 
						Buscou-se, com sua criação, garantir aos usuários dos serviços públicos no Estado de Goiás um canal
						de comunicação onde possam registrar suas manifestações (reclamações, denúncias, elogios, sugestões etc),
						e providenciar soluções para os conflitos entre os usuários e concessionárias ou permissionários.
					</p class="h5">
					<p>Participe! A qualidade de nossos serviços depende de sua participação.</p>
				</div><!-- col -->
				<div class="col col-md-4 col-md-offset-3">
					<h4 class="text-color-white margin-left-0">
						<a class="h3 link-white" href="http://www.agr.go.gov.br/pagina/ver/6901/ouvidor" target="_blank">Mais Informações</a>
					</h4>
					<ul class="list-unstyled">
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="http://www.cge.go.gov.br/ouvidoria/">Ouvidoria Geral do Estado de Goiás</a>
						</li>
						<br />
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="http://www.transparencia.go.gov.br/">Goiás Transparente</a>
						</li>
						<br />
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="http://vaptvupt.goias.gov.br/vvv/index">Vapt Vupt Digital</a>
						</li>
						<br />
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="http://www.saneago.com.br/lai/ouvidoria/">Ouvidoria da Saneago</a>
						</li>
						<br />
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="http://www.celg.com.br/paginas/clientes/centralAtendimento.aspx">Central de Atendimento CELG</a>
						</li>
						<br />
						<!-- 
						<li>
							<img src="${context}/resources/images/setaHome.png" />
							<a class="link-laranja" href="${context}/pages/externo/listarAnexos.xhtml">Relatórios da Ouvidoria</a>
						</li>
						 -->
					</ul>
				</div><!-- col -->
			</div><!-- row -->
			<div class="row padding-3">
				<div class="col col-md-12">
					<p class="h5 text-center text-color-white">
						Coordenação de Informática - Agência Goiana de Regulação. Todos os Direitos Reservados.
						<a class="link-laranja" href="http://www.agr.go.gov.br/" target="_blank">AGR</a>
					</p>
				</div>
			</div><!-- row -->
		</div>
	</footer>
	
	<div class="modal" id="avisoModal">
		<div class="modal-content">
			<div class="modal-header">
		        <button type="button" class="close" onclick="$('.modal').hide();"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="avisoTitulo"></h4>
			</div>
			<div class="modal-body"><div id="avisoConteudo"></div></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" onclick="$('.modal').hide();">Fechar</button>
			</div>
		</div>
	</div>
	<!-- 
	<script src="http://barra.brasil.gov.br/barra.js" type="text/javascript"></script>
	 -->
</body>
</html>
