package br.com.CustomizeMe.customizeme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutCorBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutTituloOrigemCorBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import br.com.CustomizeMe.customizeme.viewholder.TituloCorViewHolder


class TituloCorAdapter : RecyclerView.Adapter<TituloCorViewHolder>() {
    var listaDeCores : List<CamadaPersonalizavel> = emptyList()
    private lateinit var listener : OnClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TituloCorViewHolder {
        val inflat = LayoutInflater.from(parent.context)
        return TituloCorViewHolder(LayoutTituloOrigemCorBinding.inflate(inflat,parent,false))
    }

    override fun getItemCount(): Int {
        return listaDeCores.size
    }

    override fun onBindViewHolder(holder: TituloCorViewHolder, position: Int) {
        holder.bind(listaDeCores[position],listener)
    }

    fun attackCores(listaDeCores : List<CamadaPersonalizavel>){
        this.listaDeCores = listaDeCores
      //  this.listaDeCores += listaDeCores
        notifyDataSetChanged()
    }
    fun attackListener(listener : OnClickListener){
        this.listener = listener
    }
}