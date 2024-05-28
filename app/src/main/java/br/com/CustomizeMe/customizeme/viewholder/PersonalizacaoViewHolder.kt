package br.com.CustomizeMe.customizeme.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.R
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener

class PersonalizacaoViewHolder( itemView: ItemMenuPersonalizacaoBinding) : RecyclerView.ViewHolder(itemView.root) {

    fun bind(listener : OnClickListener){
        itemView.findViewById<ImageView>(R.id.imageViewItemMenu).setOnClickListener {
            listener.onClick()
        }
    }
}