package br.com.CustomizeMe.customizeme.model.roupa

data class Manequim <T>(
    var vestuarioSuperior: List<FrenteVerso<T>>?,
    var vestuarioInferior:List<FrenteVerso<T>>?,
    var meias: List<FrenteVerso<T>>?,
    var calcados: List<FrenteVerso<T>>?,
)