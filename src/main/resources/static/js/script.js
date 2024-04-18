let medTrimestral_Um = $('#medtrimestral_um').val();
;
let medTrimestral_dois = $('#medtrimestral_dois').val();
;
let medTrimestral_tres = $('#medtrimestral_tres').val();
;
let demFinal = 0;
function calcularNotaTrim_Um() {
    let mac_Um = $('#mac_um').val();
    let nPP_um = $('#npp_um').val();
    let nPT_um = $('#npt_um').val();
    medTrimestral_Um = (Number(mac_Um) + Number(nPP_um) + Number(nPT_um)) / 3;
    document.getElementById('medtrimestral_um').value = medTrimestral_Um.toFixed();
    calcularedFinal();
}
function calcularNotaTrim_Dois() {
    let mac_Dois = $('#mac_dois').val();
    let nPP_Dois = $('#npp_dois').val();
    let nPT_Dois = $('#npt_dois').val();
    medTrimestral_dois = (Number(mac_Dois) + Number(nPP_Dois) + Number(nPT_Dois)) / 3;
    document.getElementById('medtrimestral_dois').value = medTrimestral_dois.toFixed();
    calcularedFinal();
}
function calcularNotaTrim_Tres() {
    let mac_Tres = $('#mac_tres').val();
    let nPP_Tres = $('#npp_tres').val();
    let nPT_Tres = $('#npt_tres').val();
    medTrimestral_tres = (Number(mac_Tres) + Number(nPP_Tres) + Number(nPT_Tres)) / 3;
    document.getElementById('medtrimestral_tres').value = medTrimestral_tres.toFixed();
    calcularedFinal();
}
const calcularedFinal = function () {
    this.medFinal = (Number(medTrimestral_Um) + Number(medTrimestral_dois) + Number(medTrimestral_tres)) / 3;
    document.getElementById('medfinal').value = medFinal.toFixed();
    let valMedFinal = $('#medfinal').val();
    if (valMedFinal >= 10) {
        document.getElementById('situacao').value = "Apto";
    } else {
        document.getElementById('situacao').value = "Não Apto";
    }
}






function buscarDadosAluno(response) {
    this.matricua = $('#matricula');
    document.getElementById('nomeAluno').value = matricua.codigo;
    //alert(response);
}