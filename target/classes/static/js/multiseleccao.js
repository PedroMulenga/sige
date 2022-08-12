var ISPI = ISPI || {};
ISPI.Multiselecao = (function () {
    function Multiselecao() {
        this.statusBtn = $('.js-status');
        this.selecaoCheck = $('.js-selecao');

    }
    Multiselecao.prototype.iniciar = function () {
        this.statusBtn.on('click', onAlterarStatusClicado.bind(this));

    }
    function onAlterarStatusClicado(event) {
        var botaoClicado = $(event.currentTarget);
        var status= botaoClicado.data('status');
        //console.log(botaoClicado.data('status'));
        var checkBoxSelecionado = this.selecaoCheck.filter(':checked');
        var codigos = $.map(checkBoxSelecionado, function (c) {
            return $(c).data('codigo');
        });
        if (codigos.length > 0) {
            $.ajax({
                url: '/status',
                method: 'PUT',
                data: {
                    codigos: codigos,
                    status: status
                },
                success: function () {
                    window.location.reload();
                    
                }

            });
        }
    }
    return Multiselecao;
}());

$(function () {
    var multiselecao = new ISPI.Multiselecao();
    multiselecao.iniciar();
});



var ISPI = ISPI || {};

ISPI.ComboProvincia = (function () {

    function ComboProvincia() {
        this.combo = $('#provincia');
        this.emitter = $({});
        this.on = this.emitter.on.bind(this.emitter);
    }

    ComboProvincia.prototype.iniciar = function () {
        this.combo.on('change', onProvinciaAlterada.bind(this));
    };

    function onProvinciaAlterada() {
        //console.log('Provincia alterado', this.combo.val());
        this.emitter.trigger('alterado', this.combo.val());
    }
    return ComboProvincia;

}());

ISPI.ComboMunicipio = (function () {

    function ComboMunicipio(comboProvincia) {
        this.comboProvincia = comboProvincia;
        this.combo = $('#municipio');
        this.imgloading = $('.js-img-loading');
        this.inputHiddencodigoMunicipioSelecionado = $('#inputHiddencodigoMunicipioSelecionado');
    }

    ComboMunicipio.prototype.iniciar = function () {
        reset.call(this);
        this.comboProvincia.on('alterado', onProvinciaAlterada.bind(this));
        var codigoProvincia = this.comboProvincia.combo.val();
        inicializarMunicipio.call(this, codigoProvincia);
    }

    function onProvinciaAlterada(evento, codigoProvincia) {
        //console.log('codigo da provincia ', codigoProvincia);
        this.inputHiddencodigoMunicipioSelecionado.val();
        inicializarMunicipio.call(this, codigoProvincia);

    }
    function inicializarMunicipio(codigoProvincia) {
        if (codigoProvincia) {
            var resposta = $.ajax({
                url: this.combo.data('url'),
                method: 'GET',
                contentType: 'application/json',
                data: {'provincia': codigoProvincia},
                beforeSend: iniciarRequisicao.bind(this),
                complete: finalizarRequisicao.bind(this)
            });
            resposta.done(onBuscarMunicipioFinalizado.bind(this));
        } else {
            reset.call(this);
        }

    }

    function onBuscarMunicipioFinalizado(municipios) {
        var options = [];
        municipios.forEach(function (municipio) {
            options.push('<option value="' + municipio.codigo + '">' + municipio.nome + '</option>');
        });
        this.combo.html(options.join(''));
        this.combo.removeAttr('disabled');


        var codigoMunicipioSelecionado = this.inputHiddencodigoMunicipioSelecionado.val();
        if (codigoMunicipioSelecionado) {
            this.combo.val(codigoMunicipioSelecionado);
        }

    }


    function reset() {
        this.combo.html('<option value="">Selecione o Municipio</option>');
        this.combo.val('');
        this.combo.attr('disabled', 'disabled');
    }

    function iniciarRequisicao() {
        reset.call(this);
        this.imgloading.show();
    }

    function finalizarRequisicao() {
        this.imgloading.hide();
    }

    return ComboMunicipio;
}());

$(function () {

    var comboProvincia = new ISPI.ComboProvincia();
    comboProvincia.iniciar();
    var comboMunicipio = new ISPI.ComboMunicipio(comboProvincia);
    comboMunicipio.iniciar();

});




