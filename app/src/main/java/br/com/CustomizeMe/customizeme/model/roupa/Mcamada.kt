package br.com.CustomizeMe.customizeme.model.roupa

data class Mcamada(
    val calcado: List<Any>,
    val calcao: List<Any>,
    val camisa: List<Mcamisa>,
    val id: Int,
    val manga: List<Any>,
    val meiao: List<Any>,
    val punho: List<Mpunho>,
    val titulo: String

) {
    override fun toString(): String {
        return "Camada(calcado=$calcado, calcao=$calcao, camisa=$camisa, id=$id, manga=$manga, meiao=$meiao, punho=$punho, titulo='$titulo')"
    }
}