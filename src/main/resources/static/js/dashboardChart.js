var ISPI = ISPI || {};
ISPI.GraficoDeTotaisPorAnos = (function () {
    function GraficoDeTotaisPorAnos() {
        this.ctx = $('#myChart')[0].getContext('2d');

    }
    GraficoDeTotaisPorAnos.prototype.iniciar = function () {
        $.ajax({
            url: '/matriculas/totalEstudantelPorAno',
            method: 'GET',
            success: renderizarGrafico.bind(this)
        });
    };

    function renderizarGrafico(resposta) {
        var anos = [];
        var totais = [];
        resposta.forEach(function (object) {
            anos.unshift(object.ano);
            totais.unshift(object.totalMatricula);
            console.log(resposta);
        });
        var myChart = new Chart(this.ctx, {
            type: 'line',
            data: {
                labels: anos,
                datasets: [{
                        label: 'Crescimento Anual',
                        data: totais,
                        backgroundColor: '#066792',
                        pointBorderColor: '#066792',
                        pointBackgroundColor: '#fff',
                        borderColor: '#046967',
                        borderWidth: 6,
                        pointRadius: 5,
                    }]
            },
            options: {
                interaction: {
                    mode: 'index',
                    intersect: false
                },
                animations: {
                    tension: {
                        duration: 1200,
                        easing: 'linear',
                        from: 1,
                        top: 0,
                        loop: true
                    }

                },
                hoverRadius: 15,
                hoverBackgroundColor: '#44BD02',
                responsive: true
            }

        });
    }
    return GraficoDeTotaisPorAnos;
}());
$(function () {
    var graficoDeTotaisPorAnos = new ISPI.GraficoDeTotaisPorAnos();
    graficoDeTotaisPorAnos.iniciar();
});

