package br.com.CustomizeMe.customizeme.interfaces

import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel

interface OnDadosEnviadosListener {
    fun onDadosEnviados(dados: String)
    fun onDadosEnviados(itens : List<CamadaPersonalizavel> )
    fun onDadosEnviados(itens : CamadaPersonalizavel )
}