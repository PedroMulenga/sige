var EASYMULL = EASYMULL || {};
EASYMULL.GraficoDeTotaisPorAnos = (function () {
    function GraficoDeTotaisPorAnos() {
        this.ctx = $('#myAreaChart')[0].getContext('2d');

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
            type: 'doughnut',
            data: {
                labels: anos,
                datasets: [{
                        label: 'Crescimento Anual',
                        data: totais,
                        backgroundColor: [
                            '#AF0641',
                            '#ffa705',
                            '#9132bd',
                            '#066FA7',
                            '#B90101'
                        ],
                        borderAlign: 'center',
                        borderColor: '#FFF',
                        borderWidth: 6,
           
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
                        from: 1,
                        top: 0,
                    }

                },
                hoverOffset: 4,
                hoverBackgroundColor: '#44BD02',
                responsive: true
            }

        });
    }
    return GraficoDeTotaisPorAnos;
}());
$(function () {
    var graficoDeTotaisPorAnos = new EASYMULL.GraficoDeTotaisPorAnos();
    graficoDeTotaisPorAnos.iniciar();
});

