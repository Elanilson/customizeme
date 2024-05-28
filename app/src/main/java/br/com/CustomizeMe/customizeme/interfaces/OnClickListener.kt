package br.com.CustomizeMe.customizeme.interfaces

import android.view.View
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel

interface OnClickListener {
    fun onClick()
    fun onClick(cor : String)
    fun onClick(view : View,cor : String,camada : CamadaPersonalizavel)
    fun onClick( corOriginal : String, corNova : String, camada : CamadaPersonalizavel)

}