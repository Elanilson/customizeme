package br.com.CustomizeMe.customizeme.model.dto

import br.com.CustomizeMe.customizeme.model.roupa.MfundoGola

data class FundoGola(
    val id: Int,
    val idGola: Int,
    val imagemUrl: String,
    val titulo: String
)

fun FundoGola.toFundoGola() : MfundoGola{
    return MfundoGola(id, idGola, imagemUrl,titulo)
}