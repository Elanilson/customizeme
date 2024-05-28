package br.com.CustomizeMe.customizeme.model.dto

import br.com.CustomizeMe.customizeme.model.roupa.Mgola

data class Gola(
    val fundoGola: List<FundoGola>,
    val id: Int,
    val idCamada: Int,
    val idTipoGola: Int,
    val imagemUrl: String,
    val titulo: String
)

fun Gola.toGola() : Mgola{
    return  Mgola(fundoGola.map {it.toFundoGola() },id,idCamada, idTipoGola, imagemUrl)
}