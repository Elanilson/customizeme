package br.com.CustomizeMe.customizeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.CustomizeMe.customizeme.adapter.CorAdapter
import br.com.CustomizeMe.customizeme.adapter.PersonalizacaoAdapter
import br.com.CustomizeMe.customizeme.databinding.ActivityCoresBinding
import br.com.CustomizeMe.customizeme.databinding.ActivityMainBinding

class CoresActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCoresBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecyclerview()
    }
    private fun configRecyclerview(){
        val manager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        binding.recyclerViewCores.adapter = CorAdapter()
        binding.recyclerViewCores.layoutManager = manager
        binding.recyclerViewCores.setHasFixedSize(true)

    }
}