package br.com.CustomizeMe.customizeme.model.roupa

data class Mcamisa(
    val id:         Int,
    val idCamada:    Int,
    val idSexo:     Int,
    val idTipo:     Int,
    val codigo:     Int,
    val titulo:     String,
    val imagemUrl:   String,
    val punho:      Mpunho?,
    val tipo_gola:  List<MtipoGola>

){
}

