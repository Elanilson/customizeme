package br.com.CustomizeMe.customizeme.viewholder

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.databinding.LayoutCorBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel

class CorViewHolder(val binding: LayoutCorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cor : String,listener : OnClickListener, camada : CamadaPersonalizavel){
        binding.cardviewCor.setOnClickListener {

            listener.onClick(binding.cardviewCor,cor,camada)
        }
        binding.cardviewCor.setCardBackgroundColor(Color.parseColor(cor))
    }



}