package br.com.CustomizeMe.customizeme.model.roupa

data class MontagemManeqim(
    var id : Int?,
    var titulo : String?,
    var imagemUrl : String?,
    var cores : List<String>?,
    var coresSubstitutas : List<String>?,
) {
    override fun toString(): String {
        return "MontagemManeqim(id=$id, titulo=$titulo, imagemUrl=$imagemUrl, cores=$cores, coresSubstitutas=$coresSubstitutas)"
    }
}
