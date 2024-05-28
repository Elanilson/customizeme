package br.com.CustomizeMe.customizeme.model.roupa

data class MtipoGola(
    val gola: List<Mgola>,
    val id: Int,
    val titulo: String

) {
    override fun toString(): String {
        return "TipoGola(gola=$gola, id=$id, titulo='$titulo')"
    }
}