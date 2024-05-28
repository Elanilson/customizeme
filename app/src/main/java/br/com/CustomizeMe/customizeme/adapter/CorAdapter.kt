package br.com.CustomizeMe.customizeme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutCorBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import br.com.CustomizeMe.customizeme.viewholder.CorViewHolder


class CorAdapter : RecyclerView.Adapter<CorViewHolder>() {
    var listaDeCores : List<String> = emptyList()
    private lateinit var listener : OnClickListener
    private lateinit var camada : CamadaPersonalizavel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorViewHolder {
        val inflat = LayoutInflater.from(parent.context)
        return CorViewHolder(LayoutCorBinding.inflate(inflat,parent,false))
    }

    override fun getItemCount(): Int {
        return listaDeCores.size
    }

    override fun onBindViewHolder(holder: CorViewHolder, position: Int) {
        holder.bind(listaDeCores[position],listener,camada)
    }

    fun attackCores(listaDeCores : List<String>){
        this.listaDeCores = listaDeCores.toSet().toList()
       // this.listaDeCores += listaDeCores
        notifyDataSetChanged()
    }

    fun attackCamada(camada : CamadaPersonalizavel){
        this.camada = camada
        notifyDataSetChanged()
    }

    fun attackListener(listener : OnClickListener){
        this.listener = listener
    }

}