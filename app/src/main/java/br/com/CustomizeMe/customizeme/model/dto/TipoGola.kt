package br.com.CustomizeMe.customizeme.model.dto

import br.com.CustomizeMe.customizeme.model.roupa.MtipoGola

data class TipoGola(
    val gola: List<Gola>,
    val id: Int,
    val titulo: String
)
fun TipoGola.toTipoGola () :MtipoGola{
    return MtipoGola(gola.map { it.toGola() },id, titulo)
}