package br.com.CustomizeMe.customizeme.model.dto

data class Roupa(
    val id: Int,
    val calcado: List<Any>,
    val calcao: List<Any>,
    val camisa: List<Camisa>,
    val idCategoria: Int,
    val meiao: List<Any>,
    val titulo: String
)