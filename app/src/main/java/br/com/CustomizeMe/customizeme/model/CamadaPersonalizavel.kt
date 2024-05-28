package br.com.CustomizeMe.customizeme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CamadaPersonalizavel(
    val id : Int,
    val titulo : String,
    val dados : String,
    var coresOriginais : List<String>,
    var coresSubstitutas : List<String>
) :Parcelable {
    override fun toString(): String {
        return "CamadaPersonalizavel(titulo='$titulo', dados='$dados', coresOriginais=$coresOriginais, coresSubstitutas=$coresSubstitutas)"
    }
}
