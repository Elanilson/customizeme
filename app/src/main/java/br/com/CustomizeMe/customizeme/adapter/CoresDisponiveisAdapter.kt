package br.com.CustomizeMe.customizeme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutCorBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import br.com.CustomizeMe.customizeme.viewholder.CoresDisponiveisViewHolder


class CoresDisponiveisAdapter : RecyclerView.Adapter<CoresDisponiveisViewHolder>() {
    var listaDeCores : List<String> = emptyList()
    private lateinit var listener : OnClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoresDisponiveisViewHolder {
        val inflat = LayoutInflater.from(parent.context)
        return CoresDisponiveisViewHolder(LayoutCorBinding.inflate(inflat,parent,false))
    }

    override fun getItemCount(): Int {
        return listaDeCores.size
    }

    override fun onBindViewHolder(holder: CoresDisponiveisViewHolder, position: Int) {
        holder.bind(listaDeCores[position],listener)
    }

    fun attackCores(listaDeCores : List<String>){
        this.listaDeCores = listaDeCores
       // this.listaDeCores += listaDeCores
        notifyDataSetChanged()
    }



    fun attackListener(listener : OnClickListener){
        this.listener = listener
    }



}