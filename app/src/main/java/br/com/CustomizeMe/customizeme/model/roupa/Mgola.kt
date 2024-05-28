package br.com.CustomizeMe.customizeme.model.roupa

data class Mgola(
    val fundoGola: List<MfundoGola>,
    val id: Int,
    val idCamada: Int,
    val idTipoGola: Int,
    var imagemUrl: String,
    var titulo: String = "Gola"


) {
    override fun toString(): String {
        return "Gola(fundoGola=$fundoGola, id=$id, idCamada=$idCamada, idTipoGola=$idTipoGola, imagemUrl='$imagemUrl')"
    }
}