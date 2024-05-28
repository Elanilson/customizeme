package br.com.CustomizeMe.customizeme.fragments


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.CustomizeMe.customizeme.EditorActivity
import br.com.CustomizeMe.customizeme.R
import br.com.CustomizeMe.customizeme.adapter.CorAdapter
import br.com.CustomizeMe.customizeme.adapter.CoresDisponiveisAdapter
import br.com.CustomizeMe.customizeme.adapter.TituloCorAdapter
import br.com.CustomizeMe.customizeme.databinding.FragmentCoresBinding
import br.com.CustomizeMe.customizeme.interfaces.OnClickListener
import br.com.CustomizeMe.customizeme.interfaces.OnDadosEnviadosListener
import br.com.CustomizeMe.customizeme.model.CamadaPersonalizavel
import com.google.android.material.bottomsheet.BottomSheetBehavior


open class CoresFragment : Fragment() , OnDadosEnviadosListener  {
    private val binding by lazy { FragmentCoresBinding.inflate(layoutInflater) }

    private var valorRecebido : List<String>? = null
    private var itens : List<CamadaPersonalizavel>? = null
    private var item : CamadaPersonalizavel? = null
    private var adapter = TituloCorAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        valorRecebido = arguments?.getStringArrayList("cores") ?: emptyList()
        itens = arguments?.getParcelableArrayList("itens") ?: emptyList()
        item = arguments?.getParcelable("item") ?: null

        item?.let { Log.d("Itens","itens recebidos CoresFragment ${it.coresOriginais.size}") }



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       /* valorRecebido?.let {
            Toast.makeText(activity, "Total de cores: ${it} ", Toast.LENGTH_SHORT).show()
            adapter.attackCores(it)
        }*/
        itens?.let {
           for (item in it){
               adapter.attackCores(it)
              // adapterCoresDisponiveis.attackCamada(item)
               Log.d("Itens","itens recebidos CoresFragment ${item.titulo}")
               Log.d("Itens","itens recebidos Cores ${item.coresOriginais}")
           }
        }

      /*  item?.let {

                adapter.attackCores(it.coresOriginais)

        }*/
        //Toast.makeText(activity, "Total de cores: ${valorRecebido} ", Toast.LENGTH_SHORT).show()
        configRecyclerview()
        return binding.root
    }

    private fun configRecyclerview(){
       val manager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
      //  val manager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
        binding.recyclerViewCores.adapter = adapter
        binding.recyclerViewCores.layoutManager = manager
        binding.recyclerViewCores.setHasFixedSize(true)

        adapter.attackListener(object  : OnClickListener{
            override fun onClick() {
                Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show()
               // (activity as OnDadosEnviadosListener).onDadosEnviados("Dados que você quer enviar")
               /* itens?.let {
                   // it[0].coresSubstitutas = listOf("#FFFF00","#F10FDA")

                    substituirValor(it[0].coresSubstitutas.toMutableList(),"#F16321","#FFFF00")
                   substituirValor(it[0].coresSubstitutas.toMutableList(),"#D9D9D9","#F10FDA")
                  //  (activity as OnDadosEnviadosListener).onDadosEnviados(it)
                    (activity as OnDadosEnviadosListener).onDadosEnviados(it)
                }*/

                item?.let {
                    it.coresSubstitutas = substituirValor(it.coresSubstitutas.toMutableList(),"#D9D9D9","#FFFF00")
                    it.coresSubstitutas = substituirValor(it.coresSubstitutas.toMutableList(),"#D9D9D9","#F10FDA")

                    Log.d("Itens","original x ${it.coresOriginais}")
                    Log.d("Itens","coresSubstitutas x ${it.coresSubstitutas}")
                    //  (activity as OnDadosEnviadosListener).onDadosEnviados(it)
                    (activity as OnDadosEnviadosListener).onDadosEnviados(it)
                }

            }

            override fun onClick(cor: String) {
              /*  if(itens != null){
                    Toast.makeText(activity, "Click ", Toast.LENGTH_SHORT).show()
                    for (item in itens!!){
                        item.coresSubstitutas = substituirValor(item.coresSubstitutas.toMutableList(),cor,"#FFFF00")
                        //it.coresSubstitutas = substituirValor(it.coresSubstitutas.toMutableList(),"#D9D9D9","#F10FDA")

                        Log.d("Itens","original x ${item.coresOriginais}")
                        Log.d("Itens","coresSubstitutas x ${item.coresSubstitutas}")
                        //  (activity as OnDadosEnviadosListener).onDadosEnviados(it)
                        (activity as OnDadosEnviadosListener).onDadosEnviados(item)
                    }
                }*/





            }


            override fun onClick(view: View,cor: String, camada: CamadaPersonalizavel) {
              /*  Toast.makeText(activity, "Click alterar ", Toast.LENGTH_SHORT).show()
                camada.coresSubstitutas =  alterarPrimeiraOcorrenciaCor(camada.coresOriginais.toMutableList(),cor,"#F10FDA")
                (activity as OnDadosEnviadosListener).onDadosEnviados(camada)*/
                (activity as EditorActivity).bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)

                selesecaoDeCor(view,cor,camada)

            }

            override fun onClick(
                corOriginal: String,
                corNova: String,
                camada: CamadaPersonalizavel
            ) {

            }
        })

    }


    override fun onDadosEnviados(dados: String) {

    }

    override fun onDadosEnviados(itens: List<CamadaPersonalizavel>) {
        TODO("Not yet implemented")
    }

    override fun onDadosEnviados(itens: CamadaPersonalizavel) {
        TODO("Not yet implemented")
    }

   /* fun substituirValor(lista: MutableList<String>, valorAntigo: String, valorNovo: String) {
        val indice = lista.indexOf(valorAntigo)
        if (indice != -1) {
            lista[indice] = valorNovo
        }
    }*/

    fun substituirValor(lista: List<String>, valorAntigo: String, valorNovo: String): List<String> {
        val novaLista = lista.toMutableList() // Converte para MutableList
        val indice = novaLista.indexOf(valorAntigo)
        if (indice != -1) {
            novaLista[indice] = valorNovo
        }
        return novaLista
    }

    fun alterarPrimeiraOcorrenciaCor(listaCores: MutableList<String>, corAtual: String, novaCor: String): MutableList<String> {
        val indicePrimeiraOcorrencia = listaCores.indexOf(corAtual)
        if (indicePrimeiraOcorrencia != -1) {
            listaCores[indicePrimeiraOcorrencia] = novaCor
        }
        return listaCores
    }

    fun substituirTodasOcorrenciasCor(listaCores: MutableList<String>, corAtual: String, novaCor: String): MutableList<String> {
        for (i in listaCores.indices) {
            if (listaCores[i] == corAtual) {
                listaCores[i] = novaCor
            }
        }
        return listaCores
    }


    fun selesecaoDeCor(view: View,corOriginal : String, camada: CamadaPersonalizavel){
        activity?.let {
            val dialog = Dialog(it)
            dialog.setContentView(R.layout.layout_dialog_cores)

            val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerviewCores)
            val adapterCoresDisponiveis = CoresDisponiveisAdapter()
            val listaDeCores: List<String> = listOf(
                "#FFFFFF", // Branco
                "#F0F8FF", // Azul-claro
                "#F5F5F5", // Cinza-claro
                "#FFD700", // Amarelo
                "#98FB98", // Verde-pálido
                "#DDA0DD", // Roxo-claro
                "#87CEFA", // Azul-pálido
                "#FFE4B5", // Mocassim
                "#FFA07A", // Salmão-claro
                "#FF6347"  // Tomate
            )

            adapterCoresDisponiveis.attackCores(listaDeCores)
            adapterCoresDisponiveis.attackListener(capturarCor(view,corOriginal, camada))

            val manager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
            recyclerView.adapter = adapterCoresDisponiveis
            recyclerView.layoutManager = manager
            recyclerView.setHasFixedSize(true)
            dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
            dialog.create()
            dialog.show()
        }
    }

    fun capturarCor(view: View,corOriginal : String, camada: CamadaPersonalizavel) : OnClickListener{
        return  object : OnClickListener{
            override fun onClick() {

            }

            override fun onClick(cor: String) {
                (view as CardView).setCardBackgroundColor(Color.parseColor(cor))
               // view.setBackgroundColor(Color.parseColor(cor))
                camada.coresSubstitutas =  substituirTodasOcorrenciasCor(camada.coresOriginais.toMutableList(),corOriginal,cor)
                (activity as OnDadosEnviadosListener).onDadosEnviados(camada)

            }

            override fun onClick(view: View,cor: String, camada: CamadaPersonalizavel) {

            }

            override fun onClick(
                corOriginal: String,
                corNova: String,
                camada: CamadaPersonalizavel
            ) {

            }
        }
    }

}

