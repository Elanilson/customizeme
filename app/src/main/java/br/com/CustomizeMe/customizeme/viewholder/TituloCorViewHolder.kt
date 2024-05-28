package br.com.CustomizeMe.customizeme.viewholder

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.CustomizeMe.customizeme.adapter.CorAdapter
import br.com.CustomizeMe.customizeme.databinding.ItemMenuPersonalizacaoBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutCorBinding
import br.com.CustomizeMe.customizeme.databinding.LayoutTituloOrigemCorBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel

class TituloCorViewHolder(val binding: LayoutTituloOrigemCorBinding) : RecyclerView.ViewHolder(binding.root) {

    var adapter = CorAdapter()

    fun bind(camada : CamadaPersonalizavel, listener : OnClickListener){
    /*    binding.cardviewCor.setOnClickListener {
            listener.onClick(cor)
        }
        binding.cardviewCor.setCardBackgroundColor(Color.parseColor(cor))*/
        adapter.attackCamada(camada)
        adapter.attackListener(listener)
        binding.textViewTituloOrigemCor.setText(camada.titulo)
        adapter.attackCores(camada.coresOriginais)

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
      //  val manager = LinearLayoutManager(binding.root.context)
       // manager.orientation = RecyclerView.VERTICAL
        binding.recyclerviewCores.adapter = adapter
        binding.recyclerviewCores.layoutManager = manager
        binding.recyclerviewCores.setHasFixedSize(true)
    }
}