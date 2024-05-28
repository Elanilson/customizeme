package br.com.CustomizeMe.customizeme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.viewholder.PersonalizacaoViewHolder

class PersonalizacaoAdapter : RecyclerView.Adapter<PersonalizacaoViewHolder>() {
    private lateinit var listener : OnClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalizacaoViewHolder {
        val inflat = LayoutInflater.from(parent.context)
        return PersonalizacaoViewHolder(ItemMenuPersonalizacaoBinding.inflate(inflat,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: PersonalizacaoViewHolder, position: Int) {
        holder.bind(listener)
    }

    fun attackListener(listener : OnClickListener){
        this.listener = listener
    }
}