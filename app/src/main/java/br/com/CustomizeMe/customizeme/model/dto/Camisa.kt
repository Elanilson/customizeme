package br.com.CustomizeMe.customizeme.model.dto

import br.com.CustomizeMe.customizeme.model.roupa.Mcamisa

data class Camisa(
    val id: Int,
    val idCamada: Int,
    val idSexo: Int,
    val idTipo: Int,
    val imagemUrl: String,
    val manga: List<Any>,
    val punho: Punho?,
    val tipo_gola: List<TipoGola>,
    val titulo: String
)

fun Camisa.toCamisa() : Mcamisa{
    return  Mcamisa(id,idCamada,idSexo,idTipo,0,titulo,imagemUrl, punho?.toPunho(),tipo_gola.map { it.toTipoGola() })
}