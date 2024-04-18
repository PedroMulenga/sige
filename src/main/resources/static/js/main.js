/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*function GerarNumeroEstudante() {
 var texto = "20";
 var aleatorio = Math.floor(Math.random() * 1500);
 document.getElementById('numEstudante').value = (texto + aleatorio);
 
 }*/

$('#button').click(function (e) {
    var bi = $('#bi').val();
    console.log(bi);
    $.ajax({
        method: 'POST',
        url: '/matriculas/pegarInscricao/' + bi,
        contentType: 'application/json',
        data: JSON.stringify({bi: bi}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResumo,
        success: onSuccessVisualizarResumo
    });

    function onErrorVisualizarResumo() {
        swal({
            title: "Estudante não Inscrito ou Cancelado",
            type: "warning"
        });
        //window.alert('ALUNO NÃO ENCONTRADO OU CANCELADO');
        document.getElementById('email').value = ('');
        document.getElementById('nome').value = ('');
        document.getElementById('naturalidade').value = ('');
        document.getElementById('telefone').value = ('');
        document.getElementById('genero').value = ('');
        document.getElementById('dataNascimento').value = ('');
        document.getElementById('morada').value = ('');
        //$('.loading').hide();
        //console.log(arguments);
    }

    function onSuccessVisualizarResumo(response) {
        swal({
            title: "Dados Encontrados com Sucesso",
            type: "success"
        });
        document.getElementById('nome').value = (response.nome) + ' ' + (response.sobrenome);
        document.getElementById('naturalidade').value = (response.municipio.provincia.nome) + ', ' + (response.municipio.nome);
        document.getElementById('telefone').value = (response.telefone);
        document.getElementById('genero').value = (response.genero);
        document.getElementById('dataNascimento').value = (response.dataNascimento);
        document.getElementById('morada').value = (response.bairro);
        document.getElementById('email').value = (response.email);
        //console.log(response);
    }
});


$('#buttonPesquisar').click(function () {
    var numeroEstudante = $('#numeroEstudante').val();
    console.log(numeroEstudante);
    $.ajax({
        method: 'POST',
        url: '/pegarMatricula/' + numeroEstudante,
        contentType: 'application/json',
        data: JSON.stringify({numeroEstudante: numeroEstudante}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResultado,
        success: onSuccessVisualizarResultado
    });

    function onErrorVisualizarResultado() {
        swal({
            title: "Estudante não Encontrado ou Cancelado",
            type: "warning"
        });
        document.getElementById('numeroEstudante').value = ('');
        document.getElementById('bi').value = ('');
        document.getElementById('nome').value = ('');
        document.getElementById('periodo').value = ('');
        document.getElementById('curso').value = ('');
        document.getElementById('turma').value = ('');
        document.getElementById('anoCurricular').value = ('');
        document.getElementById('classe').value = ('');
    }

    function onSuccessVisualizarResultado(response) {
        swal({
            title: "Dados Encontrados com Sucesso",
            type: "success"
        });
        document.getElementById('nome').value = (response.aluno.nome) + ' ' + (response.aluno.sobrenome);
        document.getElementById('bi').value = (response.aluno.bi);
        document.getElementById('periodo').value = (response.turma.periodo);
        document.getElementById('anoCurricular').value = (response.anoLectivo.nome);
        document.getElementById('turma').value = (response.turma.nome);
        document.getElementById('curso').value = (response.turma.curso.nome);
        document.getElementById('classe').value = (response.turma.classe);
        //console.log(response);
    }
});

$(document).ready(function () {
    $('#buttonVerificar').click(function (e) {
        var numeroEstudante = $('#numeroEstudante').val();
        var servico = $('#servico').val();
        //var tabelaPagamentos = document.getElementById('#tabelaPagamentos');
        console.log(numeroEstudante);
        $.ajax({
            method: 'POST',
            url: '/transportes/pagamentoTransporte/' + numeroEstudante + '/' + servico,
            contentType: 'application/json',
            data: JSON.stringify({numeroEstudante: numeroEstudante, servico: servico}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Dados Não Encontrados!",
                type: "warning"
            });
            document.getElementById('numeroEstudante').value = ('');
            document.getElementById('tabelaPagamentos').value = ('');
            $.get('/transportes/carregarPaginaFragmento', $(this).serialize(), function (emolumento) {
                $("#tabelaPagamentos").load("/transportes/carregarPaginaFragmento");
            });
            document.getElementById('fragmentoFotoMatricula').value = ('');
            $.get('/transportes/carregarPaginaFragmentoFoto', $(this).serialize(), function () {
                $("#fragmentoFotoMatricula").load("/transportes/carregarPaginaFragmentoFoto");
            });

            //$('.loading').hide();
            //console.log(arguments);
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Dados Encontrados com Sucesso",
                type: "success"
            });
            $("#tabelaPagamentos").load("/transportes/carregarPaginaFragmento");
            $("#fragmentoFotoMatricula").load("/transportes/carregarPaginaFragmentoFoto");
            //$("#tabelaPagamentos").data(resposta);
            //console.log(resposta);
        }
    });
});

$(document).ready(function () {
    $('#buttonBuscarDados').click(function (e) {
        var numeroEstudante = $('#numeroEstudante').val();
        //var tabelaPagamentos = document.getElementById('#tabelaPagamentos');
        console.log(numeroEstudante);
        $.ajax({
            method: 'POST',
            url: '/transportes/verificacaoRapida/' + numeroEstudante,
            contentType: 'application/json',
            data: JSON.stringify({numeroEstudante: numeroEstudante}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Não Pago!",
                type: "warning"
            });
            document.getElementById('numeroEstudante').value = ('');
            document.getElementById('mesReferente').value = ('');
            document.getElementById('fragmentoFotoMatricula').value = ('');
            $.get('/transportes/carregarPaginaFragmentoFotoMatricula', $(this).serialize(), function (matricula) {
                $("#fragmentoFotoMatricula").load("/transportes/carregarPaginaFragmentoFotoMatricula");
            });
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Pago com Sucesso",
                type: "success"
            });
            document.getElementById('mesReferente').value = (resposta.mesReferente) + '--' + (resposta.situacao);
            $("#fragmentoFotoMatricula").load("/transportes/carregarPaginaFragmentoFotoMatricula");
        }
    });
});

//BUSCA DO USUÁRIO FUNCIONÁRIO PELO BI
$(document).ready(function () {
    $('#buscarUsuario').click(function (e) {
        var bi = $('#bi').val();
        console.log(bi);
        $.ajax({
            method: 'POST',
            url: '/usuarios/usuarioFuncionario/' + bi,
            contentType: 'application/json',
            data: JSON.stringify({bi: bi}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Dados do Usuário Não Encontrados!",
                type: "error"
            });
            document.getElementById('bi').value = ('');
            document.getElementById('nomeUsuario').value = ('');
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Dados do Usuário Encontrados com Sucesso",
                type: "success"
            });
            document.getElementById('nomeUsuario').value = (resposta.nome);
            document.getElementById('email').value = (resposta.email);
            //console.log(resposta);
        }
    });
});



//JAVA SCRIPT PARA DESABILITAR OS ALERTAS AUTOMÁTICAMENTE APOIS 5 MINUTOS
$('document').ready(function () {
    $('#file').change(function () {
        showImageThumbnail(this);
    });
    setTimeout(function () {
        $(".alertas").fadeOut("slow", function () {
            $(this).alert('close');
        });
    }, 5000);
});
//JAVA SCRIPT PARA APRESENTAR A IMAGEM APÓIS SER INSERIDA NO CADASTRO DE USUÁRIOS E MATRÍCULAS
function showImageThumbnail(fileInput) {
    file = fileInput.files[0];
    reader = new FileReader();
    reader.onload = function (e) {
        $('#thumbal').attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}


$(document).ready(function () {
    var money = $('.js-maskMoney').mask("###,##0.00", {reverse: true});
    var phone = $('.phone').mask('000-000-000');
    var data = $('.data').mask('dd/mm/yyyy');
    var bi = $('.bi').mask('000000000SS000');
    var texte = $('.mixed').mask('AAA 000-S0S');
    var anoAcademico = $('.anoAcademico').mask('0000');
});


$('#consultarNotas').click(function () {
    var numeroEstudante = $('#numeroEstudante').val();
    console.log(numeroEstudante);
    $.ajax({
        method: 'POST',
        url: '/notas/consultar_nota/' + numeroEstudante,
        contentType: 'application/json',
        data: JSON.stringify({numeroEstudante: numeroEstudante}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResultado,
        success: onSuccessVisualizarResultado
    });

    function onErrorVisualizarResultado() {
        swal({
            title: "Estudante não Encontrado ou Cancelado",
            type: "warning"
        });
        document.getElementById('nome').value = ('');
        document.getElementById('turma').value = ('');
        document.getElementById('curso').value = ('');
        document.getElementById('classe').value = ('');
        document.getElementById('periodo').value = ('');
        document.getElementById('anoCurricular').value = ('');
        document.getElementById('situacao').value = ('');
        $("#fragmentoLitaNotas").load("/notas/carregar_notas");
    }

    function onSuccessVisualizarResultado(resultado) {
        swal({
            title: "Dados Encontrados com Sucesso",
            type: "success"
        });
        document.getElementById('nome').value = (resultado.aluno.nome) + ' ' + (resultado.aluno.sobrenome);
        document.getElementById('turma').value = (resultado.turma.nome);
        document.getElementById('curso').value = (resultado.turma.curso.nome);
        document.getElementById('classe').value = (resultado.turma.classe);
        document.getElementById('periodo').value = (resultado.turma.periodo);
        document.getElementById('anoCurricular').value = (resultado.anoLectivo.nome);
        document.getElementById('situacao').value = (resultado.situacao);
        $("#fragmentoLitaNotas").load("/notas/carregar_notas");


        //console.log(response);
    }
});
function onSelectTipoServicoEmolumento() {
   var codigoServicoEmolumento = document.getElementById('servico').value;
    $.ajax({
        method: 'POST',
        url: '/tipoServico/' + codigoServicoEmolumento,
        contentType: 'application/json',
        data: JSON.stringify({codigoServicoEmolumento: codigoServicoEmolumento}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResultado,
        success: onSuccessVisualizarResultado
    });
    function onErrorVisualizarResultado() {
        document.getElementById('valorApagar').value = (0);
    }

    function onSuccessVisualizarResultado(resultado) {
        document.getElementById('valorApagar').value = (resultado.custoServico);

    }
}
