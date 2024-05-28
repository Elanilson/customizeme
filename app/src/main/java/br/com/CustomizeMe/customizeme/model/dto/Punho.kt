package br.com.CustomizeMe.customizeme.model.dto

import br.com.CustomizeMe.customizeme.model.roupa.Mpunho

data class Punho(
    val id: Int,
    val idCamada: Int,
    val idCamisa: Int,
    val imagemUrl: String,
    val titulo: String
)

fun Punho.toPunho() : Mpunho {
    return Mpunho(titulo,id,idCamada, idCamisa, imagemUrl)
}